import React, { useState } from 'react';
import { X, Calendar, Clock, Video, FileText, AlertCircle, CheckCircle } from 'lucide-react';
import interviewService from '../services/interviewService';

export const ScheduleInterviewModal = ({ application, isOpen, onClose, onSuccess }) => {
  const [formData, setFormData] = useState({
    interviewType: 'ONLINE',
    scheduledAt: '',
    endAt: '',
    meetingLink: '',
    location: '',
    notes: '',
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  if (!isOpen || !application) return null;

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!formData.scheduledAt) {
      setError('Please select an interview start time.');
      return;
    }

    setLoading(true);
    try {
      await interviewService.scheduleInterview({
        applicationId: application.id,
        scheduledAt: formData.scheduledAt,
        endAt: formData.endAt || null,
        interviewType: formData.interviewType,
        meetingLink: formData.meetingLink,
        location: formData.location,
        notes: formData.notes,
      });

      setSuccess(true);
      setTimeout(() => {
        if (onSuccess) onSuccess();
        onClose();
      }, 1500);
    } catch (err) {
      setError(
        err.response?.data?.message || 'Failed to schedule interview. Please check inputs.'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 overflow-y-auto bg-slate-900/60 backdrop-blur-sm flex items-center justify-center p-4">
      <div className="bg-white rounded-2xl max-w-lg w-full shadow-2xl border border-slate-100 overflow-hidden animate-in fade-in zoom-in-95 duration-200">
        <div className="px-6 py-4 bg-slate-50 border-b border-slate-100 flex items-center justify-between">
          <div>
            <h3 className="text-base font-bold text-slate-900">Schedule Interview</h3>
            <p className="text-xs text-slate-500">
              Candidate: <span className="font-semibold text-slate-700">{application.candidateName}</span> ({application.candidateEmail})
            </p>
          </div>
          <button
            onClick={onClose}
            className="p-1 rounded-lg text-slate-400 hover:text-slate-600 hover:bg-slate-200 transition"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

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
              <span>Interview scheduled & candidate notified!</span>
            </div>
          )}

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-xs font-semibold text-slate-700 mb-1">
                Interview Type
              </label>
              <select
                name="interviewType"
                value={formData.interviewType}
                onChange={handleChange}
                className="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500 bg-white"
              >
                <option value="ONLINE">Online Video Call</option>
                <option value="PHONE">Phone Call</option>
                <option value="TECHNICAL">Technical Round</option>
                <option value="HR">HR Discussion</option>
                <option value="IN_PERSON">In-Person Meeting</option>
              </select>
            </div>

            <div>
              <label className="block text-xs font-semibold text-slate-700 mb-1">
                Meeting Link / Location
              </label>
              <input
                type="text"
                name="meetingLink"
                placeholder="Google Meet / Zoom URL"
                value={formData.meetingLink}
                onChange={handleChange}
                className="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500"
              />
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-xs font-semibold text-slate-700 mb-1">
                Start Date & Time *
              </label>
              <input
                type="datetime-local"
                name="scheduledAt"
                required
                value={formData.scheduledAt}
                onChange={handleChange}
                className="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-slate-700 mb-1">
                End Date & Time (Optional)
              </label>
              <input
                type="datetime-local"
                name="endAt"
                value={formData.endAt}
                onChange={handleChange}
                className="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500"
              />
            </div>
          </div>

          <div>
            <label className="block text-xs font-semibold text-slate-700 mb-1">
              Instructions & Notes for Candidate
            </label>
            <textarea
              name="notes"
              rows={3}
              placeholder="e.g. Please be prepared to discuss your system design project and have an IDE ready..."
              value={formData.notes}
              onChange={handleChange}
              className="w-full p-3 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500"
            />
          </div>

          <div className="pt-2 flex items-center justify-end gap-3 border-t border-slate-100">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-xs font-medium text-slate-600 hover:bg-slate-100 rounded-lg transition"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={loading || success}
              className="px-5 py-2 text-xs font-semibold text-white bg-indigo-600 hover:bg-indigo-700 rounded-lg shadow-sm transition disabled:opacity-50"
            >
              {loading ? 'Scheduling...' : 'Confirm Schedule'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ScheduleInterviewModal;
