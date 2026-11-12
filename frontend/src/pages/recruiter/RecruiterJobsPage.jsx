import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import jobService from '../../services/jobService';
import StatusBadge from '../../components/StatusBadge';
import LoadingSpinner from '../../components/LoadingSpinner';
import EmptyState from '../../components/EmptyState';
import {
  Briefcase,
  PlusCircle,
  Users,
  Search,
  MoreVertical,
  Edit2,
  Trash2,
  CheckCircle,
  XCircle,
  Eye,
} from 'lucide-react';
import { formatDate } from '../../utils/formatters';

export const RecruiterJobsPage = () => {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [actionLoading, setActionLoading] = useState(null);

  const fetchJobs = async () => {
    setLoading(true);
    try {
      const data = await jobService.getMyJobs({ size: 100 });
      setJobs(data.content || []);
    } catch {
      setJobs([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchJobs();
  }, []);

  const handleToggleStatus = async (job) => {
    setActionLoading(job.id);
    try {
      if (job.status === 'PUBLISHED') {
        await jobService.closeJob(job.id);
      } else {
        await jobService.publishJob(job.id);
      }
      await fetchJobs();
    } catch {
      alert('Failed to change job status.');
    } finally {
      setActionLoading(null);
    }
  };

  const handleDeleteJob = async (jobId) => {
    if (!window.confirm('Are you sure you want to delete this job posting? This action cannot be undone.')) {
      return;
    }
    setActionLoading(jobId);
    try {
      await jobService.deleteJob(jobId);
      await fetchJobs();
    } catch {
      alert('Failed to delete job.');
    } finally {
      setActionLoading(null);
    }
  };

  const filtered = jobs.filter((j) =>
    j.title.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Manage Job Vacancies</h1>
          <p className="text-xs text-slate-500 mt-1">
            Oversee, update, and manage your published and archived employment listings.
          </p>
        </div>

        <Link
          to="/recruiter/jobs/create"
          className="inline-flex items-center gap-2 px-4 py-2.5 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold text-xs rounded-xl shadow-sm transition"
        >
          <PlusCircle className="w-4 h-4" />
          <span>Post New Vacancy</span>
        </Link>
      </div>

      {/* Search Bar */}
      <div className="bg-white p-4 rounded-2xl border border-slate-200 shadow-sm flex items-center gap-3">
        <Search className="w-4 h-4 text-slate-400" />
        <input
          type="text"
          placeholder="Filter jobs by title..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full text-xs outline-none bg-transparent"
        />
      </div>

      {/* Jobs Table */}
      {loading ? (
        <div className="py-24">
          <LoadingSpinner message="Fetching job listings..." />
        </div>
      ) : filtered.length === 0 ? (
        <EmptyState
          icon={Briefcase}
          title={search ? 'No jobs match your search' : 'No job vacancies posted yet'}
          description="Create your first job listing to start receiving candidate applications."
          actionLabel="Create Job Posting"
          onAction={() => (window.location.href = '/recruiter/jobs/create')}
        />
      ) : (
        <div className="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs">
              <thead className="bg-slate-50 text-slate-500 uppercase tracking-wider font-semibold border-b border-slate-100">
                <tr>
                  <th className="px-6 py-3.5">Job Title</th>
                  <th className="px-6 py-3.5">Status</th>
                  <th className="px-6 py-3.5">Applicants</th>
                  <th className="px-6 py-3.5">Type & Location</th>
                  <th className="px-6 py-3.5">Posted</th>
                  <th className="px-6 py-3.5 text-right">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100 text-slate-700">
                {filtered.map((job) => (
                  <tr key={job.id} className="hover:bg-slate-50/80 transition">
                    <td className="px-6 py-4 font-semibold text-slate-900">
                      <Link
                        to={`/recruiter/jobs/${job.id}/applicants`}
                        className="hover:text-indigo-600 block leading-snug"
                      >
                        {job.title}
                      </Link>
                      <span className="text-[11px] text-slate-400 font-normal">
                        ID: #{job.id}
                      </span>
                    </td>
                    <td className="px-6 py-4">
                      <StatusBadge status={job.status} />
                    </td>
                    <td className="px-6 py-4">
                      <Link
                        to={`/recruiter/jobs/${job.id}/applicants`}
                        className="inline-flex items-center gap-1.5 px-2.5 py-1 bg-indigo-50 text-indigo-700 font-semibold rounded-lg hover:bg-indigo-100 transition"
                      >
                        <Users className="w-3.5 h-3.5" />
                        <span>{job.applicantCount || 0} Candidates</span>
                      </Link>
                    </td>
                    <td className="px-6 py-4 text-slate-600">
                      <div>{job.employmentType?.replace(/_/g, ' ')}</div>
                      <div className="text-[11px] text-slate-400">{job.location || 'Remote'}</div>
                    </td>
                    <td className="px-6 py-4 text-slate-500">{formatDate(job.createdAt)}</td>
                    <td className="px-6 py-4 text-right">
                      <div className="flex items-center justify-end gap-1.5">
                        <Link
                          to={`/jobs/${job.id}`}
                          title="Preview public job view"
                          className="p-1.5 text-slate-400 hover:text-slate-700 rounded-lg hover:bg-slate-100"
                        >
                          <Eye className="w-4 h-4" />
                        </Link>
                        <Link
                          to={`/recruiter/jobs/${job.id}/edit`}
                          title="Edit job"
                          className="p-1.5 text-slate-400 hover:text-indigo-600 rounded-lg hover:bg-slate-100"
                        >
                          <Edit2 className="w-4 h-4" />
                        </Link>
                        <button
                          onClick={() => handleToggleStatus(job)}
                          disabled={actionLoading === job.id}
                          title={job.status === 'PUBLISHED' ? 'Close job' : 'Publish job'}
                          className={`p-1.5 rounded-lg hover:bg-slate-100 ${
                            job.status === 'PUBLISHED'
                              ? 'text-amber-500 hover:text-amber-700'
                              : 'text-emerald-500 hover:text-emerald-700'
                          }`}
                        >
                          {job.status === 'PUBLISHED' ? (
                            <XCircle className="w-4 h-4" />
                          ) : (
                            <CheckCircle className="w-4 h-4" />
                          )}
                        </button>
                        <button
                          onClick={() => handleDeleteJob(job.id)}
                          disabled={actionLoading === job.id}
                          title="Delete job"
                          className="p-1.5 text-slate-400 hover:text-rose-600 rounded-lg hover:bg-rose-50"
                        >
                          <Trash2 className="w-4 h-4" />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
};

export default RecruiterJobsPage;
