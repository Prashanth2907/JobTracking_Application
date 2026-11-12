import React, { useState, useEffect } from 'react';
import interviewService from '../../services/interviewService';
import LoadingSpinner from '../../components/LoadingSpinner';
import EmptyState from '../../components/EmptyState';
import {
  Calendar,
  Clock,
  Video,
  MapPin,
  Building,
  FileText,
  CheckCircle,
  ExternalLink,
} from 'lucide-react';
import { formatDateTime, formatDate } from '../../utils/formatters';

export const CandidateInterviews = () => {
  const [interviews, setInterviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('UPCOMING');

  useEffect(() => {
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
    fetchInterviews();
  }, []);

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
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Interview Schedule</h1>
        <p className="text-xs text-slate-500 mt-1">
          Stay prepared for upcoming video calls, technical rounds, and recruiter discussions.
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
          <LoadingSpinner message="Loading your interview schedule..." />
        </div>
      ) : displayed.length === 0 ? (
        <EmptyState
          icon={Calendar}
          title={activeTab === 'UPCOMING' ? 'No upcoming interviews' : 'No past interview records'}
          description="When recruiters schedule interviews for your applications, they will appear here with call details."
        />
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {displayed.map((item) => (
            <div
              key={item.id}
              className="bg-white rounded-2xl border border-slate-200 p-5 shadow-sm space-y-4 hover:border-indigo-200 transition"
            >
              <div className="flex items-start justify-between gap-3">
                <div>
                  <span className="px-2 py-0.5 rounded text-[10px] font-semibold bg-purple-50 text-purple-700 border border-purple-200">
                    {item.interviewType?.replace(/_/g, ' ')}
                  </span>
                  <h3 className="font-bold text-slate-900 text-sm mt-1.5">{item.jobTitle}</h3>
                  <div className="flex items-center gap-1.5 text-xs text-slate-500 mt-0.5">
                    <Building className="w-3.5 h-3.5" />
                    <span>{item.companyName}</span>
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
                </div>
                {item.endAt && (
                  <div className="flex items-center gap-2 text-slate-500 text-[11px] pl-6">
                    <span>Until {formatDateTime(item.endAt)}</span>
                  </div>
                )}
                {item.location && (
                  <div className="flex items-center gap-2">
                    <MapPin className="w-4 h-4 text-slate-400 shrink-0" />
                    <span>{item.location}</span>
                  </div>
                )}
              </div>

              {item.notes && (
                <div className="text-xs text-slate-600 bg-amber-50/60 border border-amber-100 p-3 rounded-xl">
                  <span className="font-semibold text-amber-900 block mb-0.5">Instructions:</span>
                  <p className="leading-relaxed">{item.notes}</p>
                </div>
              )}

              {item.meetingLink && (
                <div className="pt-2 border-t border-slate-100">
                  <a
                    href={item.meetingLink}
                    target="_blank"
                    rel="noreferrer"
                    className="w-full inline-flex items-center justify-center gap-2 py-2 px-3 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold text-xs rounded-xl shadow-sm transition"
                  >
                    <Video className="w-4 h-4" />
                    Join Video Call
                    <ExternalLink className="w-3.5 h-3.5" />
                  </a>
                </div>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default CandidateInterviews;
