import React, { useState, useEffect } from 'react';
import { X, Upload, FileText, CheckCircle, AlertCircle } from 'lucide-react';
import resumeService from '../services/resumeService';
import applicationService from '../services/applicationService';

export const ApplyModal = ({ job, isOpen, onClose, onSuccess }) => {
  const [resume, setResume] = useState(null);
  const [selectedFile, setSelectedFile] = useState(null);
  const [coverLetter, setCoverLetter] = useState('');
  const [loading, setLoading] = useState(false);
  const [uploadingResume, setUploadingResume] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    if (isOpen) {
      loadExistingResume();
      setError('');
      setSuccess(false);
      setCoverLetter('');
      setSelectedFile(null);
    }
  }, [isOpen]);

  const loadExistingResume = async () => {
    try {
      const data = await resumeService.getMyResume();
      setResume(data);
    } catch {
      setResume(null);
    }
  };

  const handleFileChange = (e) => {
    if (e.target.files && e.target.files[0]) {
      const file = e.target.files[0];
      if (file.size > 5 * 1024 * 1024) {
        setError('Resume file must be under 5MB');
        return;
      }
      setSelectedFile(file);
      setError('');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      let activeResumeId = resume?.id;

      // If user selected a new file, upload it first
      if (selectedFile) {
        setUploadingResume(true);
        const uploaded = await resumeService.uploadResume(selectedFile);
        activeResumeId = uploaded.id;
        setUploadingResume(false);
      }

      if (!activeResumeId) {
        setError('Please attach or upload a resume to proceed with your application.');
        setLoading(false);
        return;
      }

      // Submit application
      await applicationService.applyForJob({
        jobId: job.id,
        resumeId: activeResumeId,
        coverLetter: coverLetter.trim(),
      });

      setSuccess(true);
      setTimeout(() => {
        if (onSuccess) onSuccess();
        onClose();
      }, 1500);
    } catch (err) {
      setError(
        err.response?.data?.message || 'Failed to submit application. Please try again.'
      );
    } finally {
      setLoading(false);
      setUploadingResume(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 overflow-y-auto bg-slate-900/60 backdrop-blur-sm flex items-center justify-center p-4">
      <div className="bg-white rounded-2xl max-w-lg w-full shadow-2xl border border-slate-100 overflow-hidden animate-in fade-in zoom-in-95 duration-200">
        {/* Header */}
        <div className="px-6 py-4 bg-slate-50 border-b border-slate-100 flex items-center justify-between">
          <div>
            <h3 className="text-base font-bold text-slate-900">Apply for Position</h3>
            <p className="text-xs text-slate-500">
              {job?.title} at <span className="font-semibold text-slate-700">{job?.companyName}</span>
            </p>
          </div>
          <button
            onClick={onClose}
            className="p-1 rounded-lg text-slate-400 hover:text-slate-600 hover:bg-slate-200 transition"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Form Body */}
        <form onSubmit={handleSubmit} className="p-6 space-y-4">
          {error && (
            <div className="p-3 bg-rose-50 border border-rose-200 rounded-lg flex items-center gap-2 text-rose-700 text-xs">
              <AlertCircle className="w-4 h-4 shrink-0" />
              <span>{error}</span>
            </div>
          )}

          {success && (
            <div className="p-4 bg-emerald-50 border border-emerald-200 rounded-lg flex items-center gap-2 text-emerald-800 text-sm font-medium">
              <CheckCircle className="w-5 h-5 text-emerald-600 shrink-0" />
              <span>Application submitted successfully! Redirecting...</span>
            </div>
          )}

          {/* Resume Section */}
          <div>
            <label className="block text-xs font-semibold text-slate-700 mb-2">
              Candidate Resume (PDF or DOCX, max 5MB)
            </label>

            {resume && !selectedFile ? (
              <div className="p-3 bg-indigo-50/50 border border-indigo-200 rounded-xl flex items-center justify-between">
                <div className="flex items-center gap-2.5">
                  <FileText className="w-5 h-5 text-indigo-600" />
                  <div>
                    <p className="text-xs font-semibold text-slate-800">{resume.originalFileName}</p>
                    <p className="text-[10px] text-slate-500">
                      Uploaded {new Date(resume.uploadedAt).toLocaleDateString()}
                    </p>
                  </div>
                </div>
                <label className="text-xs font-semibold text-indigo-600 hover:text-indigo-800 cursor-pointer">
                  Replace
                  <input
                    type="file"
                    accept=".pdf,.doc,.docx"
                    className="hidden"
                    onChange={handleFileChange}
                  />
                </label>
              </div>
            ) : (
              <div className="border-2 border-dashed border-slate-300 rounded-xl p-4 text-center hover:border-indigo-400 transition cursor-pointer relative bg-slate-50/50">
                <input
                  type="file"
                  accept=".pdf,.doc,.docx"
                  className="absolute inset-0 w-full h-full opacity-0 cursor-pointer"
                  onChange={handleFileChange}
                />
                <Upload className="w-6 h-6 text-slate-400 mx-auto mb-1" />
                {selectedFile ? (
                  <p className="text-xs font-semibold text-indigo-600">{selectedFile.name}</p>
                ) : (
                  <>
                    <p className="text-xs font-semibold text-slate-700">Click to upload your resume</p>
                    <p className="text-[10px] text-slate-400 mt-0.5">Supports PDF, DOC, DOCX up to 5MB</p>
                  </>
                )}
              </div>
            )}
          </div>

          {/* Cover Letter */}
          <div>
            <label className="block text-xs font-semibold text-slate-700 mb-1">
              Cover Letter / Note to Recruiter (Optional)
            </label>
            <textarea
              rows={4}
              value={coverLetter}
              onChange={(e) => setCoverLetter(e.target.value)}
              placeholder="Introduce yourself and explain why you're a great fit for this role..."
              className="w-full p-3 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
            />
          </div>

          {/* Action buttons */}
          <div className="pt-2 flex items-center justify-end gap-3 border-t border-slate-100">
            <button
              type="button"
              onClick={onClose}
              disabled={loading}
              className="px-4 py-2 text-xs font-medium text-slate-600 hover:bg-slate-100 rounded-lg transition"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={loading || success}
              className="px-5 py-2 text-xs font-semibold text-white bg-indigo-600 hover:bg-indigo-700 rounded-lg shadow-sm transition disabled:opacity-50 flex items-center gap-1.5"
            >
              {loading
                ? uploadingResume
                  ? 'Uploading Resume...'
                  : 'Submitting...'
                : 'Submit Application'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ApplyModal;
