import React, { useState, useEffect } from 'react';
import interviewService from '../../services/interviewService';
import LoadingSpinner from '../../components/LoadingSpinner';
import EmptyState from '../../components/EmptyState';
import {
  Calendar,
  Clock,
  Video,
  User,
  Building,
  CheckCircle,
  XCircle,
  ExternalLink,
} from 'lucide-react';
import { formatDateTime } from '../../utils/formatters';

export const RecruiterInterviewsPage = () => {
  const [interviews, setInterviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('UPCOMING');
  const [actionLoading, setActionLoading] = useState(null);

  const fetchInterviews = async () => {
    setLoading(true);
    try {
      const data = await interviewService.getMyInterviews();
      setInterviews(data || []);
    } catch {
      setInterviews([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchInterviews();
  }, []);

  const handleUpdateStatus = async (id, status) => {
    setActionLoading(id);
    try {
      await interviewService.updateStatus(id, status);
      await fetchInterviews();
    } catch {
      alert('Failed to update interview status.');
    } finally {
      setActionLoading(null);
    }
  };

  const now = new Date();
  const upcoming = interviews.filter(
    (i) => i.status === 'SCHEDULED' && new Date(i.scheduledAt) >= now
  );
  const past = interviews.filter(
    (i) => i.status !== 'SCHEDULED' || new Date(i.scheduledAt) < now
  );

  const displayed = activeTab === 'UPCOMING' ? upcoming : past;

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Recruiter Interview Schedule</h1>
        <p className="text-xs text-slate-500 mt-1">
          Coordinate, manage, and track interview meetings with shortlisted candidates.
        </p>
      </div>

      <div className="flex border-b border-slate-200">
        <button
          onClick={() => setActiveTab('UPCOMING')}
          className={`py-3 px-5 text-xs font-semibold border-b-2 transition flex items-center gap-2 ${
            activeTab === 'UPCOMING'
              ? 'border-indigo-600 text-indigo-600'
              : 'border-transparent text-slate-500 hover:text-slate-800'
          }`}
        >
          <span>Upcoming Sessions</span>
          <span className="px-2 py-0.5 rounded-full text-[10px] bg-indigo-100 text-indigo-800">
            {upcoming.length}
          </span>
        </button>

        <button
          onClick={() => setActiveTab('PAST')}
          className={`py-3 px-5 text-xs font-semibold border-b-2 transition flex items-center gap-2 ${
            activeTab === 'PAST'
              ? 'border-indigo-600 text-indigo-600'
              : 'border-transparent text-slate-500 hover:text-slate-800'
          }`}
        >
          <span>Past / Completed</span>
          <span className="px-2 py-0.5 rounded-full text-[10px] bg-slate-100 text-slate-600">
            {past.length}
          </span>
        </button>
      </div>

      {loading ? (
        <div className="py-24">
          <LoadingSpinner message="Fetching interview schedule..." />
        </div>
      ) : displayed.length === 0 ? (
        <EmptyState
          icon={Calendar}
          title={activeTab === 'UPCOMING' ? 'No upcoming interviews' : 'No past interview logs'}
          description="Schedule interviews with candidates directly from the job applicants management page."
        />
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {displayed.map((item) => (
            <div
              key={item.id}
              className="bg-white rounded-2xl border border-slate-200 p-5 shadow-sm space-y-4 hover:border-indigo-200 transition"
            >
              <div className="flex items-start justify-between gap-3">
                <div className="flex items-start gap-3">
                  <div className="w-10 h-10 rounded-full bg-indigo-100 text-indigo-700 font-bold text-xs flex items-center justify-center shrink-0">
                    {item.candidateName ? item.candidateName[0].toUpperCase() : 'C'}
                  </div>
                  <div>
                    <h3 className="font-bold text-slate-900 text-sm">{item.candidateName}</h3>
                    <p className="text-[11px] text-slate-500">{item.candidateEmail}</p>
                    <p className="text-xs font-semibold text-indigo-600 mt-1">{item.jobTitle}</p>
                  </div>
                </div>

                <span
                  className={`px-2 py-0.5 rounded text-[10px] font-semibold ${
                    item.status === 'SCHEDULED'
                      ? 'bg-emerald-50 text-emerald-700 border border-emerald-200'
                      : item.status === 'COMPLETED'
                      ? 'bg-slate-100 text-slate-700'
                      : 'bg-rose-50 text-rose-700'
                  }`}
                >
                  {item.status}
                </span>
              </div>

              <div className="p-3 bg-slate-50 rounded-xl space-y-2 text-xs text-slate-700">
                <div className="flex items-center gap-2">
                  <Calendar className="w-4 h-4 text-indigo-500 shrink-0" />
                  <span className="font-semibold">{formatDateTime(item.scheduledAt)}</span>
                  <span className="text-[10px] px-2 py-0.5 rounded bg-purple-100 text-purple-700 font-semibold ml-auto">
                    {item.interviewType}
                  </span>
                </div>
                {item.endAt && (
                  <div className="text-[11px] text-slate-400 pl-6">
                    Ends at {formatDateTime(item.endAt)}
                  </div>
                )}
                {item.notes && (
                  <div className="pt-1 text-[11px] text-slate-600 pl-6 border-t border-slate-200/60 mt-1">
                    <strong>Agenda:</strong> {item.notes}
                  </div>
                )}
              </div>

              <div className="pt-2 border-t border-slate-100 flex items-center justify-between gap-2">
                {item.meetingLink ? (
                  <a
                    href={item.meetingLink}
                    target="_blank"
                    rel="noreferrer"
                    className="inline-flex items-center gap-1.5 px-3 py-1.5 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg text-xs font-semibold shadow-sm transition"
                  >
                    <Video className="w-3.5 h-3.5" />
                    <span>Join Call</span>
                    <ExternalLink className="w-3 h-3" />
                  </a>
                ) : (
                  <span className="text-[11px] text-slate-400 italic">No link provided</span>
                )}

                {item.status === 'SCHEDULED' && (
                  <div className="flex items-center gap-2">
                    <button
                      onClick={() => handleUpdateStatus(item.id, 'COMPLETED')}
                      disabled={actionLoading === item.id}
                      className="inline-flex items-center gap-1 px-2.5 py-1.5 bg-emerald-50 text-emerald-700 hover:bg-emerald-100 rounded-lg text-xs font-semibold transition"
                    >
                      <CheckCircle className="w-3.5 h-3.5" />
                      Mark Completed
                    </button>
                    <button
                      onClick={() => handleUpdateStatus(item.id, 'CANCELLED')}
                      disabled={actionLoading === item.id}
                      className="inline-flex items-center gap-1 px-2.5 py-1.5 bg-rose-50 text-rose-700 hover:bg-rose-100 rounded-lg text-xs font-semibold transition"
                    >
                      <XCircle className="w-3.5 h-3.5" />
                      Cancel
                    </button>
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default RecruiterInterviewsPage;
