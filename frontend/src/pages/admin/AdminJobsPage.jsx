import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import adminService from '../../services/adminService';
import StatusBadge from '../../components/StatusBadge';
import LoadingSpinner from '../../components/LoadingSpinner';
import EmptyState from '../../components/EmptyState';
import { Briefcase, Search, Trash2, Eye, Building, MapPin } from 'lucide-react';
import { formatDate } from '../../utils/formatters';

export const AdminJobsPage = () => {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [actionLoading, setActionLoading] = useState(null);

  const fetchJobs = async () => {
    setLoading(true);
    try {
      const data = await adminService.getAllJobs({ size: 100 });
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

  const handleDeleteJob = async (jobId) => {
    if (!window.confirm('Are you sure you want to moderate and delete this job posting from the entire platform?')) {
      return;
    }
    setActionLoading(jobId);
    try {
      await adminService.deleteJob(jobId);
      await fetchJobs();
    } catch {
      alert('Failed to delete job posting.');
    } finally {
      setActionLoading(null);
    }
  };

  const filteredJobs = jobs.filter((j) =>
    `${j.title} ${j.companyName} ${j.location}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  return (
    <div className="space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Job Moderation Console</h1>
        <p className="text-xs text-slate-500 mt-1">
          Review, audit, and remove inappropriate job listings posted across the JobTrack platform.
        </p>
      </div>

      {/* Search Bar */}
      <div className="bg-white p-4 rounded-2xl border border-slate-200 shadow-sm flex items-center gap-3">
        <Search className="w-4 h-4 text-slate-400" />
        <input
          type="text"
          placeholder="Search jobs by title, company, or location..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full text-xs outline-none bg-transparent"
        />
      </div>

      {/* Jobs Table */}
      {loading ? (
        <div className="py-24">
          <LoadingSpinner message="Auditing platform job postings..." />
        </div>
      ) : filteredJobs.length === 0 ? (
        <EmptyState
          icon={Briefcase}
          title="No jobs found"
          description="There are currently no job postings matching your query."
        />
      ) : (
        <div className="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs">
              <thead className="bg-slate-50 text-slate-500 uppercase tracking-wider font-semibold border-b border-slate-100">
                <tr>
                  <th className="px-6 py-3.5">Job Title</th>
                  <th className="px-6 py-3.5">Company & Location</th>
                  <th className="px-6 py-3.5">Status</th>
                  <th className="px-6 py-3.5">Applicants</th>
                  <th className="px-6 py-3.5">Posted Date</th>
                  <th className="px-6 py-3.5 text-right">Moderation Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100 text-slate-700">
                {filteredJobs.map((job) => (
                  <tr key={job.id} className="hover:bg-slate-50/80 transition">
                    <td className="px-6 py-4 font-semibold text-slate-900">
                      <div className="text-sm font-bold text-slate-900">{job.title}</div>
                      <span className="text-[11px] text-slate-400 font-normal">Job ID #{job.id}</span>
                    </td>

                    <td className="px-6 py-4 space-y-0.5">
                      <div className="flex items-center gap-1.5 font-medium text-slate-800">
                        <Building className="w-3.5 h-3.5 text-slate-400" />
                        <span>{job.companyName}</span>
                      </div>
                      <div className="flex items-center gap-1.5 text-slate-400 text-[11px]">
                        <MapPin className="w-3.5 h-3.5" />
                        <span>{job.location}</span>
                      </div>
                    </td>

                    <td className="px-6 py-4">
                      <StatusBadge status={job.status} />
                    </td>

                    <td className="px-6 py-4 font-semibold text-indigo-600">
                      {job.applicantCount || 0}
                    </td>

                    <td className="px-6 py-4 text-slate-500">{formatDate(job.createdAt)}</td>

                    <td className="px-6 py-4 text-right">
                      <div className="flex items-center justify-end gap-2">
                        <Link
                          to={`/jobs/${job.id}`}
                          title="View public posting"
                          className="p-1.5 text-slate-400 hover:text-slate-700 rounded-lg hover:bg-slate-100"
                        >
                          <Eye className="w-4 h-4" />
                        </Link>
                        <button
                          onClick={() => handleDeleteJob(job.id)}
                          disabled={actionLoading === job.id}
                          title="Delete / Moderate Job"
                          className="p-1.5 text-slate-400 hover:text-rose-600 rounded-lg hover:bg-rose-50 transition"
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

export default AdminJobsPage;
