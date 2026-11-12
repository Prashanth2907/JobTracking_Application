import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import jobService from '../../services/jobService';
import applicationService from '../../services/applicationService';
import resumeService from '../../services/resumeService';
import StatusBadge from '../../components/StatusBadge';
import LoadingSpinner from '../../components/LoadingSpinner';
import EmptyState from '../../components/EmptyState';
import CandidateDetailsModal from '../../components/CandidateDetailsModal';
import ScheduleInterviewModal from '../../components/ScheduleInterviewModal';
import {
  Users,
  Briefcase,
  ArrowLeft,
  Calendar,
  Download,
  Eye,
  CheckCircle,
  Clock,
  Mail,
  Phone,
} from 'lucide-react';
import { formatDate } from '../../utils/formatters';

export const RecruiterJobApplicantsPage = () => {
  const { jobId } = useParams();

  const [job, setJob] = useState(null);
  const [applicants, setApplicants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('ALL');

  // Modals state
  const [selectedCandidateApp, setSelectedCandidateApp] = useState(null);
  const [detailsModalOpen, setDetailsModalOpen] = useState(false);
  const [scheduleModalOpen, setScheduleModalOpen] = useState(false);

  const fetchApplicantsData = async () => {
    setLoading(true);
    try {
      const [jobData, appsData] = await Promise.all([
        jobService.getJobById(jobId),
        applicationService.getJobApplications(jobId, { size: 100 }),
      ]);
      setJob(jobData);
      setApplicants(appsData.content || []);
    } catch {
      // Handle gracefully
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchApplicantsData();
  }, [jobId]);

  const handleStatusChange = async (appId, newStatus) => {
    try {
      await applicationService.updateStatus(appId, newStatus);
      setApplicants((prev) =>
        prev.map((a) => (a.id === appId ? { ...a, status: newStatus } : a))
      );
    } catch (err) {
      alert('Failed to update applicant status: ' + (err.response?.data?.message || err.message));
    }
  };

  const handleDownloadResume = async (resumeId, candidateName) => {
    if (!resumeId) {
      alert('No resume attached.');
      return;
    }
    try {
      await resumeService.downloadResume(resumeId, `${candidateName}_resume.pdf`);
    } catch {
      alert('Failed to download resume.');
    }
  };

  const filterTabs = [
    { key: 'ALL', label: 'All Candidates' },
    { key: 'APPLIED', label: 'New Applied' },
    { key: 'UNDER_REVIEW', label: 'Under Review' },
    { key: 'SHORTLISTED', label: 'Shortlisted' },
    { key: 'INTERVIEW_SCHEDULED', label: 'Interviews' },
    { key: 'SELECTED', label: 'Selected / Offer' },
    { key: 'REJECTED', label: 'Rejected' },
  ];

  const filteredApplicants =
    activeTab === 'ALL'
      ? applicants
      : applicants.filter((a) => a.status === activeTab);

  if (loading) {
    return (
      <div className="py-24">
        <LoadingSpinner message="Loading job applicants..." />
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
        <div>
          <Link
            to="/recruiter/jobs"
            className="inline-flex items-center gap-1.5 text-xs font-medium text-slate-500 hover:text-slate-900 mb-2"
          >
            <ArrowLeft className="w-4 h-4" />
            Back to all jobs
          </Link>
          <div className="flex items-center gap-3">
            <h1 className="text-2xl font-bold text-slate-900 tracking-tight">
              Applicants for {job?.title}
            </h1>
            <StatusBadge status={job?.status} />
          </div>
          <p className="text-xs text-slate-500 mt-1">
            Review candidate resumes, update application pipeline stages, and schedule interviews.
          </p>
        </div>
      </div>

      {/* Filter Tabs */}
      <div className="flex items-center gap-1.5 overflow-x-auto pb-2 border-b border-slate-200 text-xs">
        {filterTabs.map((tab) => {
          const count =
            tab.key === 'ALL'
              ? applicants.length
              : applicants.filter((a) => a.status === tab.key).length;

          return (
            <button
              key={tab.key}
              onClick={() => setActiveTab(tab.key)}
              className={`px-3 py-2 rounded-xl font-medium whitespace-nowrap transition flex items-center gap-1.5 ${
                activeTab === tab.key
                  ? 'bg-indigo-600 text-white shadow-sm font-semibold'
                  : 'bg-white text-slate-600 hover:bg-slate-100 border border-slate-200'
              }`}
            >
              <span>{tab.label}</span>
              <span
                className={`px-1.5 py-0.2 rounded-full text-[10px] ${
                  activeTab === tab.key ? 'bg-indigo-700 text-white' : 'bg-slate-100 text-slate-500'
                }`}
              >
                {count}
              </span>
            </button>
          );
        })}
      </div>

      {/* Applicant Cards / Table */}
      {filteredApplicants.length === 0 ? (
        <EmptyState
          icon={Users}
          title="No candidates found in this stage"
          description="Candidates who apply or are moved to this stage will appear here."
        />
      ) : (
        <div className="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs">
              <thead className="bg-slate-50 text-slate-500 uppercase tracking-wider font-semibold border-b border-slate-100">
                <tr>
                  <th className="px-6 py-3.5">Candidate Name</th>
                  <th className="px-6 py-3.5">Contact Details</th>
                  <th className="px-6 py-3.5">Applied Date</th>
                  <th className="px-6 py-3.5">Pipeline Stage</th>
                  <th className="px-6 py-3.5 text-right">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100 text-slate-700">
                {filteredApplicants.map((app) => (
                  <tr key={app.id} className="hover:bg-slate-50/80 transition">
                    <td className="px-6 py-4">
                      <div className="font-bold text-slate-900 text-sm flex items-center gap-2">
                        <div className="w-8 h-8 rounded-full bg-indigo-100 text-indigo-700 font-bold text-xs flex items-center justify-center shrink-0">
                          {app.candidateName ? app.candidateName[0].toUpperCase() : 'C'}
                        </div>
                        <span>{app.candidateName}</span>
                      </div>
                    </td>

                    <td className="px-6 py-4 space-y-1">
                      <div className="flex items-center gap-1.5 text-slate-600">
                        <Mail className="w-3.5 h-3.5 text-slate-400" />
                        <span>{app.candidateEmail}</span>
                      </div>
                      {app.candidatePhone && (
                        <div className="flex items-center gap-1.5 text-slate-400 text-[11px]">
                          <Phone className="w-3 h-3" />
                          <span>{app.candidatePhone}</span>
                        </div>
                      )}
                    </td>

                    <td className="px-6 py-4 text-slate-500">{formatDate(app.appliedAt)}</td>

                    <td className="px-6 py-4">
                      <select
                        value={app.status}
                        onChange={(e) => handleStatusChange(app.id, e.target.value)}
                        className="px-2.5 py-1 text-xs font-semibold rounded-lg border border-slate-300 bg-white focus:outline-none focus:border-indigo-500"
                      >
                        <option value="APPLIED">Applied</option>
                        <option value="UNDER_REVIEW">Under Review</option>
                        <option value="SHORTLISTED">Shortlisted</option>
                        <option value="INTERVIEW_SCHEDULED">Interview Scheduled</option>
                        <option value="SELECTED">Selected</option>
                        <option value="REJECTED">Rejected</option>
                      </select>
                    </td>

                    <td className="px-6 py-4 text-right">
                      <div className="flex items-center justify-end gap-2">
                        <button
                          type="button"
                          onClick={() => {
                            setSelectedCandidateApp(app);
                            setDetailsModalOpen(true);
                          }}
                          className="inline-flex items-center gap-1 px-2.5 py-1.5 bg-slate-100 hover:bg-slate-200 text-slate-700 rounded-lg text-xs font-medium transition"
                        >
                          <Eye className="w-3.5 h-3.5" />
                          Review
                        </button>

                        {app.resumeId && (
                          <button
                            type="button"
                            onClick={() => handleDownloadResume(app.resumeId, app.candidateName)}
                            title="Download Resume"
                            className="p-1.5 text-indigo-600 hover:bg-indigo-50 rounded-lg transition"
                          >
                            <Download className="w-4 h-4" />
                          </button>
                        )}

                        <button
                          type="button"
                          onClick={() => {
                            setSelectedCandidateApp(app);
                            setScheduleModalOpen(true);
                          }}
                          className="inline-flex items-center gap-1 px-2.5 py-1.5 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg text-xs font-semibold shadow-sm transition"
                        >
                          <Calendar className="w-3.5 h-3.5" />
                          Interview
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

      {/* Modals */}
      <CandidateDetailsModal
        application={selectedCandidateApp}
        isOpen={detailsModalOpen}
        onClose={() => setDetailsModalOpen(false)}
        onStatusChanged={handleStatusChange}
        onOpenScheduleModal={(app) => {
          setSelectedCandidateApp(app);
          setScheduleModalOpen(true);
        }}
      />

      <ScheduleInterviewModal
        application={selectedCandidateApp}
        isOpen={scheduleModalOpen}
        onClose={() => setScheduleModalOpen(false)}
        onSuccess={() => {
          fetchApplicantsData();
        }}
      />
    </div>
  );
};

export default RecruiterJobApplicantsPage;
