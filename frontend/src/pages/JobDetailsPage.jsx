import React, { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import jobService from '../services/jobService';
import applicationService from '../services/applicationService';
import { useAuth } from '../context/AuthContext';
import ApplyModal from '../components/ApplyModal';
import LoadingSpinner from '../components/LoadingSpinner';
import StatusBadge from '../components/StatusBadge';
import {
  Briefcase,
  MapPin,
  DollarSign,
  Building,
  Calendar,
  Users,
  Clock,
  ArrowLeft,
  CheckCircle,
  Share2,
  ExternalLink,
} from 'lucide-react';
import { formatDate, formatSalary } from '../utils/formatters';

export const JobDetailsPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user, isAuthenticated, role } = useAuth();

  const [job, setJob] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [hasApplied, setHasApplied] = useState(false);
  const [existingApplication, setExistingApplication] = useState(null);
  const [applyModalOpen, setApplyModalOpen] = useState(false);
  const [copied, setCopied] = useState(false);

  useEffect(() => {
    const fetchJobData = async () => {
      setLoading(true);
      try {
        const jobData = await jobService.getJobById(id);
        setJob(jobData);

        // If candidate, check if already applied
        if (isAuthenticated && role === 'CANDIDATE') {
          try {
            const myApps = await applicationService.getMyApplications({ size: 100 });
            const matching = (myApps.content || []).find((a) => a.jobId === Number(id));
            if (matching) {
              setHasApplied(true);
              setExistingApplication(matching);
            }
          } catch {
            // Ignore error checking applications
          }
        }
      } catch (err) {
        setError('Job posting not found or has been closed.');
      } finally {
        setLoading(false);
      }
    };

    fetchJobData();
  }, [id, isAuthenticated, role]);

  const handleShare = () => {
    navigator.clipboard.writeText(window.location.href);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  if (loading) {
    return (
      <div className="min-h-[60vh] flex items-center justify-center">
        <LoadingSpinner message="Loading job posting details..." />
      </div>
    );
  }

  if (error || !job) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-16 text-center">
        <div className="w-12 h-12 rounded-full bg-rose-100 text-rose-600 flex items-center justify-center mx-auto mb-4 font-bold">
          !
        </div>
        <h2 className="text-xl font-bold text-slate-900 mb-2">Job Not Found</h2>
        <p className="text-sm text-slate-500 mb-6">{error || 'This job does not exist.'}</p>
        <Link
          to="/jobs"
          className="inline-flex items-center gap-2 px-4 py-2 bg-indigo-600 text-white text-xs font-semibold rounded-lg hover:bg-indigo-700 transition"
        >
          <ArrowLeft className="w-4 h-4" />
          Back to all vacancies
        </Link>
      </div>
    );
  }

  const skillsList = job.skills
    ? job.skills.split(',').map((s) => s.trim()).filter(Boolean)
    : [];

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-8">
      {/* Navigation bar */}
      <div className="flex items-center justify-between">
        <button
          onClick={() => navigate(-1)}
          className="inline-flex items-center gap-1.5 text-xs font-medium text-slate-500 hover:text-slate-900 transition"
        >
          <ArrowLeft className="w-4 h-4" />
          Back to Listings
        </button>

        <button
          onClick={handleShare}
          className="inline-flex items-center gap-1.5 px-3 py-1.5 text-xs font-medium text-slate-600 bg-white border border-slate-200 rounded-lg hover:bg-slate-50 shadow-sm transition"
        >
          <Share2 className="w-3.5 h-3.5" />
          {copied ? 'Link Copied!' : 'Share'}
        </button>
      </div>

      {/* Hero Header Card */}
      <div className="bg-white rounded-2xl border border-slate-200 p-6 sm:p-8 shadow-sm">
        <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-6 pb-6 border-b border-slate-100">
          <div className="flex items-start gap-4">
            <div className="w-16 h-16 rounded-2xl bg-indigo-50 border border-indigo-100 text-indigo-700 flex items-center justify-center font-extrabold text-2xl shrink-0">
              {job.companyName ? job.companyName[0].toUpperCase() : 'C'}
            </div>
            <div>
              <div className="flex flex-wrap items-center gap-2 mb-1">
                <h1 className="text-xl sm:text-2xl font-bold text-slate-900 leading-snug">
                  {job.title}
                </h1>
                <StatusBadge status={job.status} />
              </div>
              <div className="flex flex-wrap items-center gap-3 text-xs text-slate-600">
                <span className="font-semibold text-slate-800">{job.companyName}</span>
                {job.location && (
                  <>
                    <span>•</span>
                    <span className="flex items-center gap-1">
                      <MapPin className="w-3.5 h-3.5 text-slate-400" />
                      {job.location}
                    </span>
                  </>
                )}
                {job.employmentType && (
                  <>
                    <span>•</span>
                    <span className="px-2 py-0.5 bg-slate-100 rounded text-slate-700 font-medium">
                      {job.employmentType.replace(/_/g, ' ')}
                    </span>
                  </>
                )}
              </div>
            </div>
          </div>

          {/* Action CTA */}
          <div className="w-full sm:w-auto shrink-0">
            {hasApplied ? (
              <div className="p-3 bg-emerald-50 border border-emerald-200 rounded-xl text-xs text-emerald-800 flex items-center gap-2">
                <CheckCircle className="w-4 h-4 text-emerald-600 shrink-0" />
                <div>
                  <p className="font-semibold">Application Submitted</p>
                  <p className="text-[11px] text-emerald-700">
                    Status: <strong className="capitalize">{existingApplication?.status?.replace(/_/g, ' ')}</strong>
                  </p>
                </div>
              </div>
            ) : role === 'RECRUITER' || role === 'ADMIN' ? (
              <div className="px-4 py-2 bg-slate-100 rounded-xl text-xs font-semibold text-slate-600">
                Preview Mode ({role})
              </div>
            ) : (
              <button
                onClick={() => {
                  if (!isAuthenticated) {
                    navigate('/login');
                  } else {
                    setApplyModalOpen(true);
                  }
                }}
                className="w-full sm:w-auto px-6 py-3 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold text-sm rounded-xl shadow-md shadow-indigo-200 transition"
              >
                Apply for this Position
              </button>
            )}
          </div>
        </div>

        {/* Key Job Meta Grid */}
        <div className="grid grid-cols-2 sm:grid-cols-4 gap-4 pt-6 text-xs">
          <div>
            <span className="text-slate-400 block mb-0.5">Salary Compensation</span>
            <div className="font-semibold text-slate-900 flex items-center gap-1">
              <DollarSign className="w-3.5 h-3.5 text-slate-400" />
              <span>{formatSalary(job.salaryMin, job.salaryMax)}</span>
            </div>
          </div>

          <div>
            <span className="text-slate-400 block mb-0.5">Required Experience</span>
            <div className="font-semibold text-slate-900 flex items-center gap-1">
              <Briefcase className="w-3.5 h-3.5 text-slate-400" />
              <span>{job.experienceRequired || 'Not specified'}</span>
            </div>
          </div>

          <div>
            <span className="text-slate-400 block mb-0.5">Application Deadline</span>
            <div className="font-semibold text-slate-900 flex items-center gap-1">
              <Calendar className="w-3.5 h-3.5 text-slate-400" />
              <span>{job.deadline ? formatDate(job.deadline) : 'Rolling admission'}</span>
            </div>
          </div>

          <div>
            <span className="text-slate-400 block mb-0.5">Open Vacancies</span>
            <div className="font-semibold text-slate-900 flex items-center gap-1">
              <Users className="w-3.5 h-3.5 text-slate-400" />
              <span>{job.vacancies || 1} Open position(s)</span>
            </div>
          </div>
        </div>
      </div>

      {/* Main Details and Sidebar */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Left column: Description & Requirements */}
        <div className="lg:col-span-2 space-y-6">
          <div className="bg-white rounded-2xl border border-slate-200 p-6 sm:p-8 shadow-sm space-y-6">
            <div>
              <h2 className="text-base font-bold text-slate-900 mb-3">About the Role</h2>
              <div className="text-xs sm:text-sm text-slate-700 whitespace-pre-wrap leading-relaxed">
                {job.description}
              </div>
            </div>

            {job.requirements && (
              <div className="pt-6 border-t border-slate-100">
                <h2 className="text-base font-bold text-slate-900 mb-3">Role Requirements & Qualifications</h2>
                <div className="text-xs sm:text-sm text-slate-700 whitespace-pre-wrap leading-relaxed">
                  {job.requirements}
                </div>
              </div>
            )}

            {skillsList.length > 0 && (
              <div className="pt-6 border-t border-slate-100">
                <h2 className="text-base font-bold text-slate-900 mb-3">Required Technical Skills</h2>
                <div className="flex flex-wrap gap-2">
                  {skillsList.map((skill, index) => (
                    <span
                      key={index}
                      className="px-3 py-1 bg-indigo-50 text-indigo-700 rounded-lg text-xs font-medium border border-indigo-100"
                    >
                      {skill}
                    </span>
                  ))}
                </div>
              </div>
            )}
          </div>
        </div>

        {/* Right column: Company info & quick facts */}
        <div className="space-y-6">
          <div className="bg-white rounded-2xl border border-slate-200 p-6 shadow-sm space-y-4">
            <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider">
              About the Company
            </h3>
            <div className="flex items-center gap-3">
              <div className="w-12 h-12 rounded-xl bg-slate-100 text-slate-700 flex items-center justify-center font-bold text-lg">
                <Building className="w-6 h-6" />
              </div>
              <div>
                <h4 className="font-semibold text-sm text-slate-900">{job.companyName}</h4>
                {job.companyWebsite && (
                  <a
                    href={job.companyWebsite}
                    target="_blank"
                    rel="noreferrer"
                    className="text-xs text-indigo-600 hover:underline flex items-center gap-1"
                  >
                    <span>Visit website</span>
                    <ExternalLink className="w-3 h-3" />
                  </a>
                )}
              </div>
            </div>

            {job.companyDescription && (
              <p className="text-xs text-slate-600 leading-relaxed border-t border-slate-100 pt-3">
                {job.companyDescription}
              </p>
            )}
          </div>

          {/* Safe hiring reminder */}
          <div className="bg-indigo-50/50 rounded-2xl border border-indigo-100 p-5 space-y-2 text-xs">
            <div className="font-semibold text-indigo-900">JobTrack Application Security</div>
            <p className="text-indigo-700/80 leading-relaxed">
              Never share credit card numbers or pay application fees. All employer communications are conducted securely on JobTrack.
            </p>
          </div>
        </div>
      </div>

      {/* Apply Modal */}
      <ApplyModal
        job={job}
        isOpen={applyModalOpen}
        onClose={() => setApplyModalOpen(false)}
        onSuccess={() => {
          setHasApplied(true);
        }}
      />
    </div>
  );
};

export default JobDetailsPage;
