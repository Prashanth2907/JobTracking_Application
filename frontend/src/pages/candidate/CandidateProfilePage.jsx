import React, { useState, useEffect } from 'react';
import userService from '../../services/userService';
import resumeService from '../../services/resumeService';
import { useAuth } from '../../context/AuthContext';
import LoadingSpinner from '../../components/LoadingSpinner';
import {
  User,
  FileText,
  Upload,
  Download,
  CheckCircle,
  AlertCircle,
  Plus,
  X,
  Briefcase,
  GraduationCap,
} from 'lucide-react';

export const CandidateProfilePage = () => {
  const { user, refreshUser } = useAuth();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [uploadingResume, setUploadingResume] = useState(false);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const [resume, setResume] = useState(null);
  const [newSkill, setNewSkill] = useState('');
  const [skillsList, setSkillsList] = useState([]);

  const [formData, setFormData] = useState({
    headline: '',
    bio: '',
    yearsOfExperience: 0,
    expectedSalary: '',
    education: '',
    experience: '',
    skills: '',
  });

  useEffect(() => {
    loadProfileData();
  }, []);

  const loadProfileData = async () => {
    setLoading(true);
    try {
      const [profileData, resumeData] = await Promise.all([
        userService.getProfile(),
        resumeService.getMyResume().catch(() => null),
      ]);

      const cand = profileData.candidateProfile || {};
      setFormData({
        headline: cand.headline || '',
        bio: cand.bio || '',
        yearsOfExperience: cand.yearsOfExperience || 0,
        expectedSalary: cand.expectedSalary || '',
        education: cand.education || '',
        experience: cand.experience || '',
        skills: cand.skills || '',
      });

      if (cand.skills) {
        setSkillsList(
          cand.skills
            .split(',')
            .map((s) => s.trim())
            .filter(Boolean)
        );
      }

      setResume(resumeData);
    } catch {
      setError('Failed to load profile data.');
    } finally {
      setLoading(false);
    }
  };

  const handleAddSkill = (e) => {
    e.preventDefault();
    if (!newSkill.trim()) return;
    if (!skillsList.includes(newSkill.trim())) {
      const updated = [...skillsList, newSkill.trim()];
      setSkillsList(updated);
      setFormData({ ...formData, skills: updated.join(', ') });
    }
    setNewSkill('');
  };

  const handleRemoveSkill = (skillToRemove) => {
    const updated = skillsList.filter((s) => s !== skillToRemove);
    setSkillsList(updated);
    setFormData({ ...formData, skills: updated.join(', ') });
  };

  const handleSubmitProfile = async (e) => {
    e.preventDefault();
    setError('');
    setMessage('');
    setSaving(true);

    try {
      await userService.updateCandidateProfile({
        ...formData,
        skills: skillsList.join(', '),
      });
      await refreshUser();
      setMessage('Profile updated successfully!');
      setTimeout(() => setMessage(''), 3000);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to update profile.');
    } finally {
      setSaving(false);
    }
  };

  const handleFileUpload = async (e) => {
    if (e.target.files && e.target.files[0]) {
      const file = e.target.files[0];
      if (file.size > 5 * 1024 * 1024) {
        setError('Resume file must be smaller than 5MB');
        return;
      }
      setUploadingResume(true);
      setError('');
      try {
        const uploaded = await resumeService.uploadResume(file);
        setResume(uploaded);
        setMessage('Resume uploaded successfully!');
        setTimeout(() => setMessage(''), 3000);
      } catch (err) {
        setError(err.response?.data?.message || 'Failed to upload resume.');
      } finally {
        setUploadingResume(false);
      }
    }
  };

  const handleDownloadResume = async () => {
    if (!resume?.id) return;
    try {
      await resumeService.downloadResume(resume.id, resume.originalFileName);
    } catch {
      setError('Failed to download resume.');
    }
  };

  if (loading) {
    return (
      <div className="py-24">
        <LoadingSpinner message="Loading candidate profile..." />
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-slate-900 tracking-tight">Candidate Profile & Resume</h1>
        <p className="text-xs text-slate-500 mt-1">
          Keep your professional details up to date to increase visibility to recruiters.
        </p>
      </div>

      {message && (
        <div className="p-3 bg-emerald-50 border border-emerald-200 rounded-xl text-xs text-emerald-800 flex items-center gap-2">
          <CheckCircle className="w-4 h-4 text-emerald-600 shrink-0" />
          <span>{message}</span>
        </div>
      )}

      {error && (
        <div className="p-3 bg-rose-50 border border-rose-200 rounded-xl text-xs text-rose-700 flex items-center gap-2">
          <AlertCircle className="w-4 h-4 shrink-0" />
          <span>{error}</span>
        </div>
      )}

      {/* Resume Management Card */}
      <div className="bg-white rounded-2xl border border-slate-200 p-6 shadow-sm space-y-4">
        <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider flex items-center gap-2">
          <FileText className="w-4 h-4 text-indigo-600" />
          <span>Resume Document</span>
        </h3>

        {resume ? (
          <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between p-4 bg-indigo-50/50 border border-indigo-200 rounded-xl gap-4">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-lg bg-indigo-600 text-white flex items-center justify-center font-bold">
                PDF
              </div>
              <div>
                <p className="text-xs font-semibold text-slate-900">{resume.originalFileName}</p>
                <p className="text-[10px] text-slate-500">
                  Uploaded {new Date(resume.uploadedAt).toLocaleDateString()} • {(resume.fileSize / 1024).toFixed(1)} KB
                </p>
              </div>
            </div>

            <div className="flex items-center gap-2">
              <button
                type="button"
                onClick={handleDownloadResume}
                className="inline-flex items-center gap-1.5 px-3 py-1.5 bg-white border border-indigo-200 text-indigo-700 rounded-lg text-xs font-medium hover:bg-indigo-50 shadow-sm"
              >
                <Download className="w-3.5 h-3.5" />
                Download
              </button>

              <label className="inline-flex items-center gap-1.5 px-3 py-1.5 bg-indigo-600 text-white rounded-lg text-xs font-semibold hover:bg-indigo-700 shadow-sm cursor-pointer">
                <Upload className="w-3.5 h-3.5" />
                {uploadingResume ? 'Uploading...' : 'Replace Resume'}
                <input
                  type="file"
                  accept=".pdf,.doc,.docx"
                  className="hidden"
                  onChange={handleFileUpload}
                  disabled={uploadingResume}
                />
              </label>
            </div>
          </div>
        ) : (
          <div className="border-2 border-dashed border-slate-200 rounded-xl p-8 text-center bg-slate-50/50">
            <Upload className="w-8 h-8 text-slate-400 mx-auto mb-2" />
            <p className="text-xs font-semibold text-slate-800">No resume uploaded yet</p>
            <p className="text-[11px] text-slate-500 mb-4">Upload a PDF or Word document up to 5MB</p>
            <label className="inline-flex items-center gap-2 px-4 py-2 bg-indigo-600 text-white text-xs font-semibold rounded-lg hover:bg-indigo-700 shadow-sm cursor-pointer transition">
              <Upload className="w-4 h-4" />
              <span>{uploadingResume ? 'Uploading...' : 'Upload Resume Now'}</span>
              <input
                type="file"
                accept=".pdf,.doc,.docx"
                className="hidden"
                onChange={handleFileUpload}
                disabled={uploadingResume}
              />
            </label>
          </div>
        )}
      </div>

      {/* Profile Details Form */}
      <form onSubmit={handleSubmitProfile} className="bg-white rounded-2xl border border-slate-200 p-6 shadow-sm space-y-5">
        <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider flex items-center gap-2 pb-3 border-b border-slate-100">
          <User className="w-4 h-4 text-indigo-600" />
          <span>Professional Details</span>
        </h3>

        <div>
          <label className="block text-xs font-semibold text-slate-700 mb-1">
            Professional Headline
          </label>
          <input
            type="text"
            value={formData.headline}
            onChange={(e) => setFormData({ ...formData, headline: e.target.value })}
            placeholder="e.g. Senior Full Stack Java & Cloud Architect"
            className="w-full px-3 py-2 text-xs border border-slate-200 rounded-xl focus:outline-none focus:border-indigo-500"
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-xs font-semibold text-slate-700 mb-1">
              Years of Experience
            </label>
            <input
              type="number"
              min="0"
              max="50"
              value={formData.yearsOfExperience}
              onChange={(e) => setFormData({ ...formData, yearsOfExperience: Number(e.target.value) })}
              className="w-full px-3 py-2 text-xs border border-slate-200 rounded-xl focus:outline-none focus:border-indigo-500"
            />
          </div>

          <div>
            <label className="block text-xs font-semibold text-slate-700 mb-1">
              Expected Annual Salary ($)
            </label>
            <input
              type="number"
              step="5000"
              value={formData.expectedSalary}
              onChange={(e) => setFormData({ ...formData, expectedSalary: e.target.value })}
              placeholder="e.g. 135000"
              className="w-full px-3 py-2 text-xs border border-slate-200 rounded-xl focus:outline-none focus:border-indigo-500"
            />
          </div>
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-700 mb-1">
            Professional Summary / Bio
          </label>
          <textarea
            rows={3}
            value={formData.bio}
            onChange={(e) => setFormData({ ...formData, bio: e.target.value })}
            placeholder="Write a concise overview of your background, technical focus, and achievements..."
            className="w-full p-3 text-xs border border-slate-200 rounded-xl focus:outline-none focus:border-indigo-500"
          />
        </div>

        {/* Skills Tag Input */}
        <div>
          <label className="block text-xs font-semibold text-slate-700 mb-1">
            Technical Skills & Technologies
          </label>
          <div className="flex gap-2 mb-2">
            <input
              type="text"
              value={newSkill}
              onChange={(e) => setNewSkill(e.target.value)}
              placeholder="Type a skill (e.g. Docker, Spring Boot) and click Add"
              className="flex-1 px-3 py-2 text-xs border border-slate-200 rounded-xl focus:outline-none focus:border-indigo-500"
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  e.preventDefault();
                  handleAddSkill(e);
                }
              }}
            />
            <button
              type="button"
              onClick={handleAddSkill}
              className="px-4 py-2 bg-indigo-600 text-white rounded-xl text-xs font-semibold hover:bg-indigo-700 shadow-sm flex items-center gap-1"
            >
              <Plus className="w-3.5 h-3.5" />
              Add
            </button>
          </div>

          <div className="flex flex-wrap gap-1.5 min-h-8">
            {skillsList.map((skill, index) => (
              <span
                key={index}
                className="inline-flex items-center gap-1 px-2.5 py-1 bg-indigo-50 text-indigo-700 border border-indigo-200 rounded-lg text-xs font-medium"
              >
                <span>{skill}</span>
                <button
                  type="button"
                  onClick={() => handleRemoveSkill(skill)}
                  className="text-indigo-400 hover:text-indigo-800"
                >
                  <X className="w-3 h-3" />
                </button>
              </span>
            ))}
          </div>
        </div>

        {/* Education History */}
        <div>
          <label className="block text-xs font-semibold text-slate-700 mb-1 flex items-center gap-1.5">
            <GraduationCap className="w-4 h-4 text-slate-400" />
            <span>Education & Degrees</span>
          </label>
          <textarea
            rows={3}
            value={formData.education}
            onChange={(e) => setFormData({ ...formData, education: e.target.value })}
            placeholder="e.g. B.S. in Computer Science - University of California, Berkeley (2018 - 2022)"
            className="w-full p-3 text-xs border border-slate-200 rounded-xl focus:outline-none focus:border-indigo-500"
          />
        </div>

        {/* Work Experience */}
        <div>
          <label className="block text-xs font-semibold text-slate-700 mb-1 flex items-center gap-1.5">
            <Briefcase className="w-4 h-4 text-slate-400" />
            <span>Work Experience</span>
          </label>
          <textarea
            rows={4}
            value={formData.experience}
            onChange={(e) => setFormData({ ...formData, experience: e.target.value })}
            placeholder="e.g. Senior Software Engineer at Tech Innovators (2022 - Present)&#10;• Designed microservices with Spring Boot and Kafka&#10;• Led frontend revamp with React and Tailwind"
            className="w-full p-3 text-xs border border-slate-200 rounded-xl focus:outline-none focus:border-indigo-500"
          />
        </div>

        <div className="pt-4 border-t border-slate-100 flex justify-end">
          <button
            type="submit"
            disabled={saving}
            className="px-6 py-2.5 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold text-xs rounded-xl shadow-md shadow-indigo-200 disabled:opacity-50 transition"
          >
            {saving ? 'Saving Profile...' : 'Save Profile Changes'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default CandidateProfilePage;
