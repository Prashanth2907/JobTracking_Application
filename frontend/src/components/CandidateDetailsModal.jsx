import React, { useState } from 'react';
import {
  X,
  Download,
  Calendar,
  Mail,
  Phone,
  MapPin,
  Briefcase,
  GraduationCap,
  FileText,
  Clock,
} from 'lucide-react';
import StatusBadge from './StatusBadge';
import resumeService from '../services/resumeService';
import applicationService from '../services/applicationService';
import { formatDateTime } from '../utils/formatters';

export const CandidateDetailsModal = ({
  application,
  isOpen,
  onClose,
  onStatusChanged,
  onOpenScheduleModal,
}) => {
  const [updating, setUpdating] = useState(false);
  const [selectedStatus, setSelectedStatus] = useState(application?.status || 'APPLIED');
  const [downloading, setDownloading] = useState(false);

  if (!isOpen || !application) return null;

  const handleDownloadResume = async () => {
    if (!application.resumeId) return;
    setDownloading(true);
    try {
      await resumeService.downloadResume(
        application.resumeId,
        application.resumeFileName || `${application.candidateName}_resume.pdf`
      );
    } catch {
      alert('Failed to download resume file.');
    } finally {
      setDownloading(false);
    }
  };

  const handleStatusChange = async (newStatus) => {
    setSelectedStatus(newStatus);
    setUpdating(true);
    try {
      await applicationService.updateStatus(application.id, newStatus);
      if (onStatusChanged) onStatusChanged(application.id, newStatus);
    } catch (err) {
      alert('Failed to update status: ' + (err.response?.data?.message || err.message));
    } finally {
      setUpdating(false);
    }
  };

  const statusOptions = [
    { value: 'APPLIED', label: 'Applied' },
    { value: 'UNDER_REVIEW', label: 'Under Review' },
    { value: 'SHORTLISTED', label: 'Shortlisted' },
    { value: 'INTERVIEW_SCHEDULED', label: 'Interview Scheduled' },
    { value: 'SELECTED', label: 'Selected / Offer' },
    { value: 'REJECTED', label: 'Rejected' },
  ];

  return (
    <div className="fixed inset-0 z-50 overflow-y-auto bg-slate-900/60 backdrop-blur-sm flex items-center justify-center p-4">
      <div className="bg-white rounded-2xl max-w-2xl w-full shadow-2xl border border-slate-100 overflow-hidden animate-in fade-in zoom-in-95 duration-200">
        {/* Header */}
        <div className="px-6 py-4 bg-slate-50 border-b border-slate-100 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-full bg-indigo-100 text-indigo-700 flex items-center justify-center font-bold text-sm">
              {application.candidateName ? application.candidateName[0].toUpperCase() : 'C'}
            </div>
            <div>
              <h3 className="text-base font-bold text-slate-900">{application.candidateName}</h3>
              <p className="text-xs text-slate-500">
                Applied for <span className="font-semibold text-slate-700">{application.jobTitle}</span>
              </p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="p-1 rounded-lg text-slate-400 hover:text-slate-600 hover:bg-slate-200 transition"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Modal content */}
        <div className="p-6 space-y-6 max-h-[75vh] overflow-y-auto">
          {/* Quick status & action banner */}
          <div className="p-4 bg-slate-50 rounded-xl border border-slate-200 flex flex-wrap items-center justify-between gap-4">
            <div>
              <div className="text-[11px] font-semibold text-slate-500 uppercase tracking-wider mb-1">
                Current Pipeline Stage
              </div>
              <StatusBadge status={selectedStatus} />
            </div>

            <div className="flex items-center gap-2">
              <select
                value={selectedStatus}
                onChange={(e) => handleStatusChange(e.target.value)}
                disabled={updating}
                className="px-3 py-1.5 text-xs font-medium border border-slate-300 rounded-lg focus:outline-none focus:border-indigo-500 bg-white"
              >
                {statusOptions.map((opt) => (
                  <option key={opt.value} value={opt.value}>
                    Move to {opt.label}
                  </option>
                ))}
              </select>

              <button
                onClick={() => {
                  onClose();
                  if (onOpenScheduleModal) onOpenScheduleModal(application);
                }}
                className="inline-flex items-center gap-1.5 px-3 py-1.5 text-xs font-semibold text-white bg-indigo-600 hover:bg-indigo-700 rounded-lg shadow-sm transition"
              >
                <Calendar className="w-3.5 h-3.5" />
                Schedule Interview
              </button>
            </div>
          </div>

          {/* Contact Details */}
          <div>
            <h4 className="text-xs font-bold uppercase tracking-wider text-slate-500 mb-3">
              Candidate Contact
            </h4>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs">
              <div className="flex items-center gap-2 text-slate-700">
                <Mail className="w-4 h-4 text-slate-400" />
                <span>{application.candidateEmail}</span>
              </div>
              {application.candidatePhone && (
                <div className="flex items-center gap-2 text-slate-700">
                  <Phone className="w-4 h-4 text-slate-400" />
                  <span>{application.candidatePhone}</span>
                </div>
              )}
              <div className="flex items-center gap-2 text-slate-400">
                <Clock className="w-4 h-4" />
                <span>Applied {formatDateTime(application.appliedAt)}</span>
              </div>
            </div>
          </div>

          {/* Resume file section */}
          <div>
            <h4 className="text-xs font-bold uppercase tracking-wider text-slate-500 mb-2">
              Resume Document
            </h4>
            {application.resumeId ? (
              <div className="flex items-center justify-between p-3.5 bg-indigo-50/40 border border-indigo-200 rounded-xl">
                <div className="flex items-center gap-3">
                  <FileText className="w-6 h-6 text-indigo-600" />
                  <div>
                    <p className="text-xs font-semibold text-slate-900">
                      {application.resumeFileName || 'Candidate_Resume.pdf'}
                    </p>
                    <p className="text-[10px] text-slate-500">Verified applicant attachment</p>
                  </div>
                </div>
                <button
                  onClick={handleDownloadResume}
                  disabled={downloading}
                  className="inline-flex items-center gap-1.5 px-3 py-1.5 text-xs font-semibold text-indigo-700 bg-white border border-indigo-300 hover:bg-indigo-50 rounded-lg shadow-sm transition"
                >
                  <Download className="w-3.5 h-3.5" />
                  {downloading ? 'Downloading...' : 'Download Resume'}
                </button>
              </div>
            ) : (
              <p className="text-xs text-slate-400 italic">No resume attached to this application.</p>
            )}
          </div>

          {/* Cover Letter */}
          {application.coverLetter && (
            <div>
              <h4 className="text-xs font-bold uppercase tracking-wider text-slate-500 mb-2">
                Candidate Note / Cover Letter
              </h4>
              <div className="p-3.5 bg-slate-50 rounded-xl border border-slate-200 text-xs text-slate-700 whitespace-pre-wrap leading-relaxed">
                {application.coverLetter}
              </div>
            </div>
          )}
        </div>

        {/* Modal Footer */}
        <div className="px-6 py-3 bg-slate-50 border-t border-slate-100 flex justify-end">
          <button
            onClick={onClose}
            className="px-4 py-2 text-xs font-semibold text-slate-700 hover:bg-slate-200 rounded-lg transition"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
};

export default CandidateDetailsModal;
