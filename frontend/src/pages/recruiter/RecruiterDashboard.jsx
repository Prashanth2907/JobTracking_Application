import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import jobService from '../../services/jobService';
import interviewService from '../../services/interviewService';
import LoadingSpinner from '../../components/LoadingSpinner';
import StatusBadge from '../../components/StatusBadge';
import {
  Briefcase,
  Users,
  Calendar,
  CheckCircle,
  PlusCircle,
  ArrowRight,
  Clock,
  TrendingUp,
  Building,
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

export const RecruiterDashboard = () => {
  const { user } = useAuth();
  const [loading, setLoading] = useState(true);
  const [jobs, setJobs] = useState([]);
  const [interviews, setInterviews] = useState([]);

  useEffect(() => {
    const loadDashboard = async () => {
      setLoading(true);
      try {
        const [jobsData, intsData] = await Promise.all([
          jobService.getMyJobs({ size: 10 }),
          interviewService.getMyInterviews(),
        ]);
        setJobs(jobsData.content || []);
        setInterviews(intsData || []);
      } catch {
        // Handle gracefully
      } finally {
        setLoading(false);
      }
    };
    loadDashboard();
  }, []);

  if (loading) {
    return (
      <div className="py-24">
        <LoadingSpinner message="Loading recruiter dashboard..." />
      </div>
    );
  }

  const activeJobs = jobs.filter((j) => j.status === 'PUBLISHED');
  const totalApplicants = jobs.reduce((sum, j) => sum + (j.applicantCount || 0), 0);
  const upcomingInterviews = interviews.filter(
    (i) => i.status === 'SCHEDULED' && new Date(i.scheduledAt) >= new Date()
  );

  const chartData = jobs.slice(0, 6).map((j) => ({
    name: j.title.length > 15 ? j.title.substring(0, 15) + '...' : j.title,
    applicants: j.applicantCount || 0,
  }));

  return (
    <div className="space-y-6">
      {/* Header Banner */}
      <div className="bg-gradient-to-r from-slate-900 via-indigo-950 to-indigo-900 rounded-2xl p-6 sm:p-8 text-white shadow-xl flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
        <div>
          <span className="text-xs font-semibold uppercase tracking-wider text-indigo-300">
            Recruiter Workspace
          </span>
          <h1 className="text-2xl sm:text-3xl font-bold mt-1">
            Welcome, {user?.firstName}!
          </h1>
          <p className="text-xs sm:text-sm text-indigo-200 mt-1 max-w-xl">
            Manage your open job postings, screen applicant resumes, and advance candidates through your hiring pipeline.
          </p>
        </div>

        <Link
          to="/recruiter/jobs/create"
          className="px-5 py-2.5 bg-indigo-500 hover:bg-indigo-600 text-white font-semibold text-xs rounded-xl shadow transition shrink-0 flex items-center gap-1.5"
        >
          <PlusCircle className="w-4 h-4" />
          <span>Post New Job</span>
        </Link>
      </div>

      {/* Metrics Row */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Active Jobs</span>
            <Briefcase className="w-4 h-4 text-emerald-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{activeJobs.length}</div>
          <p className="text-[11px] text-slate-400 mt-1">{jobs.length} total vacancies</p>
        </div>

        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Total Applicants</span>
            <Users className="w-4 h-4 text-indigo-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{totalApplicants}</div>
          <p className="text-[11px] text-slate-400 mt-1">Candidates in pipeline</p>
        </div>

        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Interviews</span>
            <Calendar className="w-4 h-4 text-purple-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{upcomingInterviews.length}</div>
          <p className="text-[11px] text-slate-400 mt-1">Upcoming scheduled</p>
        </div>

        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Company Profile</span>
            <Building className="w-4 h-4 text-blue-500" />
          </div>
          <div className="text-base font-bold text-slate-900 truncate">
            {user?.companyName || 'Verified'}
          </div>
          <Link to="/recruiter/profile" className="text-[11px] text-indigo-600 hover:underline mt-1 block">
            Edit branding →
          </Link>
        </div>
      </div>

      {/* Analytics & Quick Interviews Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Applicant Volume per Job */}
        <div className="lg:col-span-2 bg-white p-6 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider">
              Applicant Volume by Job Posting
            </h3>
            <span className="text-xs text-slate-400">Top Postings</span>
          </div>

          {chartData.length === 0 ? (
            <div className="h-56 flex items-center justify-center text-slate-400 text-xs">
              No job postings created yet.
            </div>
          ) : (
            <div className="h-56 w-full">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={chartData} margin={{ top: 10, right: 10, left: -20, bottom: 0 }}>
                  <XAxis dataKey="name" tick={{ fontSize: 11 }} />
                  <YAxis allowDecimals={false} tick={{ fontSize: 11 }} />
                  <Tooltip
                    formatter={(val) => [`${val} Applicants`, 'Total']}
                    contentStyle={{ backgroundColor: '#1e293b', borderRadius: '8px', color: '#fff', fontSize: '12px' }}
                  />
                  <Bar dataKey="applicants" fill="#6366f1" radius={[6, 6, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </div>
          )}
        </div>

        {/* Scheduled Interviews Box */}
        <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm flex flex-col justify-between">
          <div>
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider">
                Upcoming Sessions
              </h3>
              <Link to="/recruiter/interviews" className="text-xs text-indigo-600 hover:underline">
                View all ({interviews.length})
              </Link>
            </div>

            {upcomingInterviews.length === 0 ? (
              <div className="py-8 text-center text-slate-400">
                <Calendar className="w-8 h-8 mx-auto mb-2 text-slate-300" />
                <p className="text-xs">No upcoming interviews scheduled.</p>
              </div>
            ) : (
              <div className="space-y-3">
                {upcomingInterviews.slice(0, 3).map((item) => (
                  <div key={item.id} className="p-3 bg-purple-50/50 border border-purple-100 rounded-xl space-y-1">
                    <div className="flex items-center justify-between">
                      <p className="text-xs font-bold text-purple-950">{item.candidateName}</p>
                      <span className="text-[10px] font-semibold px-2 py-0.5 bg-purple-100 text-purple-800 rounded">
                        {item.interviewType}
                      </span>
                    </div>
                    <p className="text-[11px] text-purple-800">{item.jobTitle}</p>
                    <div className="flex items-center gap-1.5 text-[10px] text-purple-600 pt-1">
                      <Clock className="w-3 h-3" />
                      <span>{formatDateTime(item.scheduledAt)}</span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>

          <div className="pt-4 border-t border-slate-100 mt-4">
            <Link
              to="/recruiter/jobs"
              className="text-xs text-slate-500 hover:text-indigo-600 flex items-center justify-between"
            >
              <span>Manage all job postings</span>
              <ArrowRight className="w-3.5 h-3.5" />
            </Link>
          </div>
        </div>
      </div>

      {/* Posted Jobs Table */}
      <div className="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
        <div className="p-5 border-b border-slate-100 flex items-center justify-between">
          <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider">
            Your Active Job Postings
          </h3>
          <Link to="/recruiter/jobs" className="text-xs font-semibold text-indigo-600 hover:underline">
            View All ({jobs.length})
          </Link>
        </div>

        {jobs.length === 0 ? (
          <div className="p-8 text-center">
            <Briefcase className="w-8 h-8 mx-auto mb-2 text-slate-300" />
            <p className="text-xs text-slate-500 mb-4">You have not created any job vacancies yet.</p>
            <Link
              to="/recruiter/jobs/create"
              className="px-4 py-2 bg-indigo-600 text-white rounded-lg text-xs font-semibold hover:bg-indigo-700 transition"
            >
              Post First Job
            </Link>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs">
              <thead className="bg-slate-50 text-slate-500 uppercase tracking-wider font-semibold border-b border-slate-100">
                <tr>
                  <th className="px-6 py-3">Job Title</th>
                  <th className="px-6 py-3">Status</th>
                  <th className="px-6 py-3">Applicants</th>
                  <th className="px-6 py-3">Posted Date</th>
                  <th className="px-6 py-3 text-right">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100 text-slate-700">
                {jobs.slice(0, 5).map((job) => (
                  <tr key={job.id} className="hover:bg-slate-50/80 transition">
                    <td className="px-6 py-3.5 font-semibold text-slate-900">
                      <Link to={`/recruiter/jobs/${job.id}/applicants`} className="hover:text-indigo-600">
                        {job.title}
                      </Link>
                    </td>
                    <td className="px-6 py-3.5">
                      <StatusBadge status={job.status} />
                    </td>
                    <td className="px-6 py-3.5">
                      <span className="font-semibold text-indigo-600">
                        {job.applicantCount || 0} candidates
                      </span>
                    </td>
                    <td className="px-6 py-3.5 text-slate-500">{formatDate(job.createdAt)}</td>
                    <td className="px-6 py-3.5 text-right space-x-2">
                      <Link
                        to={`/recruiter/jobs/${job.id}/applicants`}
                        className="px-2.5 py-1 bg-indigo-50 text-indigo-700 font-semibold rounded-lg hover:bg-indigo-100 transition"
                      >
                        Applicants
                      </Link>
                      <Link
                        to={`/recruiter/jobs/${job.id}/edit`}
                        className="px-2.5 py-1 text-slate-600 hover:text-slate-900 rounded-lg hover:bg-slate-100 transition"
                      >
                        Edit
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

export default RecruiterDashboard;
