import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import applicationService from '../../services/applicationService';
import StatusBadge from '../../components/StatusBadge';
import LoadingSpinner from '../../components/LoadingSpinner';
import EmptyState from '../../components/EmptyState';
import {
  Briefcase,
  Building,
  Calendar,
  Clock,
  Video,
  FileText,
  ChevronDown,
  ChevronUp,
} from 'lucide-react';
import { formatDate, formatDateTime } from '../../utils/formatters';

export const CandidateApplications = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeFilter, setActiveFilter] = useState('ALL');
  const [expandedId, setExpandedId] = useState(null);

  useEffect(() => {
    const fetchApps = async () => {
      setLoading(true);
      try {
        const data = await applicationService.getMyApplications({ size: 100 });
        setApplications(data.content || []);
      } catch {
        setApplications([]);
      } finally {
        setLoading(false);
      }
    };
    fetchApps();
  }, []);

  const filterTabs = [
    { key: 'ALL', label: 'All Applications' },
    { key: 'APPLIED', label: 'Applied' },
    { key: 'UNDER_REVIEW', label: 'Under Review' },
    { key: 'SHORTLISTED', label: 'Shortlisted' },
    { key: 'INTERVIEW_SCHEDULED', label: 'Interview' },
    { key: 'SELECTED', label: 'Selected' },
    { key: 'REJECTED', label: 'Rejected' },
  ];

  const filteredApps =
    activeFilter === 'ALL'
      ? applications
      : applications.filter((a) => a.status === activeFilter);

  return (
    <div className="space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">My Job Applications</h1>
        <p className="text-xs text-slate-500 mt-1">
          Monitor your recruitment journey and review feedback on all submitted job applications.
        </p>
      </div>

      {/* Filter Tabs */}
      <div className="flex items-center gap-1.5 overflow-x-auto pb-2 border-b border-slate-200 text-xs">
        {filterTabs.map((tab) => {
          const count =
            tab.key === 'ALL'
              ? applications.length
              : applications.filter((a) => a.status === tab.key).length;

          return (
            <button
              key={tab.key}
              onClick={() => setActiveFilter(tab.key)}
              className={`px-3 py-2 rounded-xl font-medium whitespace-nowrap transition flex items-center gap-1.5 ${
                activeFilter === tab.key
                  ? 'bg-indigo-600 text-white shadow-sm font-semibold'
                  : 'bg-white text-slate-600 hover:bg-slate-100 border border-slate-200'
              }`}
            >
              <span>{tab.label}</span>
              <span
                className={`px-1.5 py-0.2 rounded-full text-[10px] ${
                  activeFilter === tab.key ? 'bg-indigo-700 text-white' : 'bg-slate-100 text-slate-500'
                }`}
              >
                {count}
              </span>
            </button>
          );
        })}
      </div>

      {/* Applications List */}
      {loading ? (
        <div className="py-24">
          <LoadingSpinner message="Fetching your applications..." />
        </div>
      ) : filteredApps.length === 0 ? (
        <EmptyState
          title="No applications match this filter"
          description="You haven't submitted applications with this status yet."
          actionLabel="Explore Available Jobs"
          onAction={() => (window.location.href = '/jobs')}
        />
      ) : (
        <div className="space-y-4">
          {filteredApps.map((app) => {
            const isExpanded = expandedId === app.id;

            return (
              <div
                key={app.id}
                className="bg-white rounded-2xl border border-slate-200 p-5 shadow-sm hover:border-indigo-200 transition space-y-4"
              >
                <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
                  <div className="flex items-start gap-3.5">
                    <div className="w-12 h-12 rounded-xl bg-slate-100 text-slate-700 flex items-center justify-center font-bold text-base border border-slate-200 shrink-0">
                      {app.companyName ? app.companyName[0].toUpperCase() : 'C'}
                    </div>
                    <div>
                      <Link
                        to={`/jobs/${app.jobId}`}
                        className="font-bold text-base text-slate-900 hover:text-indigo-600 transition"
                      >
                        {app.jobTitle}
                      </Link>
                      <div className="flex items-center gap-2 text-xs text-slate-500 mt-0.5">
                        <span className="font-semibold text-slate-700">{app.companyName}</span>
                        <span>•</span>
                        <span>Applied on {formatDate(app.appliedAt)}</span>
                      </div>
                    </div>
                  </div>

                  <div className="flex items-center gap-3 w-full sm:w-auto justify-between sm:justify-end">
                    <StatusBadge status={app.status} />
                    <button
                      onClick={() => setExpandedId(isExpanded ? null : app.id)}
                      className="p-1.5 text-slate-400 hover:text-slate-600 rounded-lg hover:bg-slate-100"
                    >
                      {isExpanded ? <ChevronUp className="w-4 h-4" /> : <ChevronDown className="w-4 h-4" />}
                    </button>
                  </div>
                </div>

                {/* Collapsible Details */}
                {isExpanded && (
                  <div className="pt-4 border-t border-slate-100 text-xs space-y-3 bg-slate-50/50 p-4 rounded-xl">
                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                      <div>
                        <span className="text-slate-400 block font-medium">Attached Resume:</span>
                        <div className="flex items-center gap-1.5 text-slate-700 font-semibold mt-0.5">
                          <FileText className="w-3.5 h-3.5 text-indigo-500" />
                          <span>{app.resumeFileName || 'Standard Profile Resume'}</span>
                        </div>
                      </div>

                      <div>
                        <span className="text-slate-400 block font-medium">Recruiter Updates:</span>
                        <span className="text-slate-700 font-semibold mt-0.5 block">
                          Last updated on {formatDateTime(app.updatedAt)}
                        </span>
                      </div>
                    </div>

                    {app.coverLetter && (
                      <div className="pt-2 border-t border-slate-200/60">
                        <span className="text-slate-400 block font-medium mb-1">Your Cover Letter:</span>
                        <p className="text-slate-600 bg-white p-3 rounded-lg border border-slate-200 leading-relaxed whitespace-pre-wrap">
                          {app.coverLetter}
                        </p>
                      </div>
                    )}

                    <div className="pt-2 flex justify-end">
                      <Link
                        to={`/jobs/${app.jobId}`}
                        className="px-3 py-1.5 bg-white border border-slate-200 text-indigo-600 font-semibold rounded-lg hover:bg-slate-50 transition"
                      >
                        View Job Posting
                      </Link>
                    </div>
                  </div>
                )}
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default CandidateApplications;
