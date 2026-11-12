import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import applicationService from '../../services/applicationService';
import interviewService from '../../services/interviewService';
import resumeService from '../../services/resumeService';
import StatusBadge from '../../components/StatusBadge';
import LoadingSpinner from '../../components/LoadingSpinner';
import EmptyState from '../../components/EmptyState';
import {
  FileText,
  Clock,
  CheckCircle2,
  Calendar,
  ArrowRight,
  TrendingUp,
  AlertCircle,
  Video,
  Upload,
} from 'lucide-react';
import { formatDate, formatDateTime } from '../../utils/formatters';
import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  Cell,
} from 'recharts';

export const CandidateDashboard = () => {
  const { user } = useAuth();
  const [loading, setLoading] = useState(true);
  const [applications, setApplications] = useState([]);
  const [interviews, setInterviews] = useState([]);
  const [resume, setResume] = useState(null);

  useEffect(() => {
    const loadDashboardData = async () => {
      setLoading(true);
      try {
        const [appsData, intsData, resumeData] = await Promise.all([
          applicationService.getMyApplications({ size: 10 }),
          interviewService.getMyInterviews(),
          resumeService.getMyResume().catch(() => null),
        ]);
        setApplications(appsData.content || []);
        setInterviews(intsData || []);
        setResume(resumeData);
      } catch {
        // Handle gracefully
      } finally {
        setLoading(false);
      }
    };

    loadDashboardData();
  }, []);

  if (loading) {
    return (
      <div className="py-24">
        <LoadingSpinner message="Loading candidate dashboard..." />
      </div>
    );
  }

  // Calculate status counts
  const totalApps = applications.length;
  const underReview = applications.filter((a) => a.status === 'UNDER_REVIEW').length;
  const shortlisted = applications.filter((a) => a.status === 'SHORTLISTED').length;
  const interviewScheduled = applications.filter((a) => a.status === 'INTERVIEW_SCHEDULED').length;
  const offers = applications.filter((a) => a.status === 'SELECTED').length;

  const chartData = [
    { name: 'Applied', count: applications.filter((a) => a.status === 'APPLIED').length, color: '#3b82f6' },
    { name: 'Review', count: underReview, color: '#f59e0b' },
    { name: 'Shortlist', count: shortlisted, color: '#6366f1' },
    { name: 'Interview', count: interviewScheduled, color: '#a855f7' },
    { name: 'Selected', count: offers, color: '#10b981' },
  ];

  const upcomingInterviews = interviews.filter(
    (i) => i.status === 'SCHEDULED' && new Date(i.scheduledAt) >= new Date()
  );

  return (
    <div className="space-y-6">
      {/* Welcome Banner */}
      <div className="bg-gradient-to-r from-indigo-700 via-indigo-600 to-violet-600 rounded-2xl p-6 sm:p-8 text-white shadow-lg flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
        <div>
          <span className="text-xs font-semibold uppercase tracking-wider text-indigo-200">
            Candidate Portal
          </span>
          <h1 className="text-2xl sm:text-3xl font-bold mt-1">
            Welcome back, {user?.firstName}!
          </h1>
          <p className="text-xs sm:text-sm text-indigo-100 mt-1 max-w-xl">
            Track your submitted applications, view scheduled interviews, and discover new openings matching your skill set.
          </p>
        </div>

        <Link
          to="/jobs"
          className="px-5 py-2.5 bg-white text-indigo-700 font-semibold text-xs rounded-xl shadow hover:bg-indigo-50 transition shrink-0 flex items-center gap-1.5"
        >
          <span>Browse Jobs</span>
          <ArrowRight className="w-4 h-4" />
        </Link>
      </div>

      {/* Resume missing alert banner */}
      {!resume && (
        <div className="p-4 bg-amber-50 border border-amber-200 rounded-2xl flex items-center justify-between gap-4">
          <div className="flex items-center gap-3">
            <AlertCircle className="w-5 h-5 text-amber-600 shrink-0" />
            <div>
              <p className="text-xs font-bold text-amber-900">Your profile is missing a resume!</p>
              <p className="text-[11px] text-amber-700">
                Upload your resume so recruiters can review your skills when you apply.
              </p>
            </div>
          </div>
          <Link
            to="/candidate/profile"
            className="px-3 py-1.5 bg-amber-600 hover:bg-amber-700 text-white font-semibold text-xs rounded-lg transition shrink-0"
          >
            Upload Resume
          </Link>
        </div>
      )}

      {/* Summary Metrics Cards */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Applications</span>
            <FileText className="w-4 h-4 text-blue-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{totalApps}</div>
          <p className="text-[11px] text-slate-400 mt-1">Total submitted</p>
        </div>

        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Under Review</span>
            <Clock className="w-4 h-4 text-amber-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{underReview}</div>
          <p className="text-[11px] text-slate-400 mt-1">Being evaluated</p>
        </div>

        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Shortlisted</span>
            <CheckCircle2 className="w-4 h-4 text-indigo-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{shortlisted}</div>
          <p className="text-[11px] text-slate-400 mt-1">Advanced to rounds</p>
        </div>

        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Interviews</span>
            <Calendar className="w-4 h-4 text-purple-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{interviewScheduled}</div>
          <p className="text-[11px] text-slate-400 mt-1">Scheduled sessions</p>
        </div>
      </div>

      {/* Analytics & Upcoming Interviews Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Recharts Pipeline Chart */}
        <div className="lg:col-span-2 bg-white p-6 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider">
              Application Pipeline Progress
            </h3>
            <span className="text-xs text-slate-400">Live Status Tracking</span>
          </div>

          <div className="h-56 w-full">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={chartData} margin={{ top: 10, right: 10, left: -20, bottom: 0 }}>
                <XAxis dataKey="name" tick={{ fontSize: 11 }} />
                <YAxis allowDecimals={false} tick={{ fontSize: 11 }} />
                <Tooltip
                  formatter={(val) => [`${val} Applications`, 'Count']}
                  contentStyle={{ backgroundColor: '#1e293b', borderRadius: '8px', color: '#fff', fontSize: '12px' }}
                />
                <Bar dataKey="count" radius={[6, 6, 0, 0]}>
                  {chartData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={entry.color} />
                  ))}
                </Bar>
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Upcoming Interviews Card */}
        <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm flex flex-col justify-between">
          <div>
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider">
                Upcoming Interviews
              </h3>
              <Link to="/candidate/interviews" className="text-xs text-indigo-600 hover:underline">
                View all
              </Link>
            </div>

            {upcomingInterviews.length === 0 ? (
              <div className="py-8 text-center text-slate-400">
                <Calendar className="w-8 h-8 mx-auto mb-2 text-slate-300" />
                <p className="text-xs">No upcoming interviews scheduled yet.</p>
              </div>
            ) : (
              <div className="space-y-3">
                {upcomingInterviews.slice(0, 2).map((item) => (
                  <div key={item.id} className="p-3 bg-purple-50/50 border border-purple-100 rounded-xl space-y-1">
                    <p className="text-xs font-semibold text-purple-950">{item.companyName}</p>
                    <p className="text-[11px] text-purple-800">{item.jobTitle}</p>
                    <div className="flex items-center gap-1.5 text-[10px] text-purple-600 pt-1">
                      <Clock className="w-3 h-3" />
                      <span>{formatDateTime(item.scheduledAt)}</span>
                    </div>
                    {item.meetingLink && (
                      <a
                        href={item.meetingLink}
                        target="_blank"
                        rel="noreferrer"
                        className="inline-flex items-center gap-1 text-[11px] font-semibold text-indigo-600 hover:underline pt-1"
                      >
                        <Video className="w-3 h-3" />
                        Join Meeting Link
                      </a>
                    )}
                  </div>
                ))}
              </div>
            )}
          </div>

          <div className="pt-4 border-t border-slate-100 mt-4">
            <Link
              to="/candidate/profile"
              className="text-xs text-slate-500 hover:text-indigo-600 flex items-center justify-between"
            >
              <span>Manage Profile & Skills</span>
              <ArrowRight className="w-3.5 h-3.5" />
            </Link>
          </div>
        </div>
      </div>

      {/* Recent Applications Table */}
      <div className="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
        <div className="p-5 border-b border-slate-100 flex items-center justify-between">
          <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider">
            Recent Job Applications
          </h3>
          <Link
            to="/candidate/applications"
            className="text-xs font-semibold text-indigo-600 hover:underline"
          >
            View all applications ({totalApps})
          </Link>
        </div>

        {applications.length === 0 ? (
          <div className="p-8">
            <EmptyState
              title="You haven't applied to any jobs yet"
              description="Discover thousands of exciting opportunities and submit your application with one click."
              actionLabel="Search Open Vacancies"
              onAction={() => (window.location.href = '/jobs')}
            />
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs">
              <thead className="bg-slate-50 text-slate-500 uppercase tracking-wider font-semibold border-b border-slate-100">
                <tr>
                  <th className="px-6 py-3">Job Title & Company</th>
                  <th className="px-6 py-3">Applied On</th>
                  <th className="px-6 py-3">Current Status</th>
                  <th className="px-6 py-3 text-right">Action</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100 text-slate-700">
                {applications.slice(0, 5).map((app) => (
                  <tr key={app.id} className="hover:bg-slate-50/80 transition">
                    <td className="px-6 py-3.5">
                      <div className="font-semibold text-slate-900">{app.jobTitle}</div>
                      <div className="text-[11px] text-slate-400">{app.companyName}</div>
                    </td>
                    <td className="px-6 py-3.5 text-slate-500">{formatDate(app.appliedAt)}</td>
                    <td className="px-6 py-3.5">
                      <StatusBadge status={app.status} />
                    </td>
                    <td className="px-6 py-3.5 text-right">
                      <Link
                        to={`/jobs/${app.jobId}`}
                        className="text-indigo-600 hover:text-indigo-800 font-semibold"
                      >
                        View Job
                      </Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default CandidateDashboard;
