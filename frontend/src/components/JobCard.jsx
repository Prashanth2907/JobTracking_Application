import React from 'react';
import { Link } from 'react-router-dom';
import {
  Briefcase,
  MapPin,
  Clock,
  DollarSign,
  Building2,
  Calendar,
  Users,
} from 'lucide-react';
import { formatDate, formatSalary } from '../utils/formatters';

export const JobCard = ({ job, isRecruiterView = false, onAction }) => {
  if (!job) return null;

  const skillsList = job.skills
    ? job.skills
        .split(',')
        .map((s) => s.trim())
        .filter(Boolean)
    : [];

  return (
    <div className="bg-white rounded-xl border border-slate-200 p-5 hover:shadow-md hover:border-indigo-200 transition-smooth flex flex-col justify-between">
      <div>
        {/* Top Header */}
        <div className="flex items-start justify-between gap-3 mb-3">
          <div className="flex items-start gap-3">
            <div className="w-12 h-12 rounded-xl bg-slate-100 text-slate-700 flex items-center justify-center font-bold text-base border border-slate-200 shrink-0">
              {job.companyName ? job.companyName[0].toUpperCase() : 'C'}
            </div>
            <div>
              <Link
                to={`/jobs/${job.id}`}
                className="font-semibold text-base text-slate-900 hover:text-indigo-600 transition leading-snug line-clamp-1"
              >
                {job.title}
              </Link>
              <div className="flex items-center gap-1.5 text-xs text-slate-500 mt-0.5">
                <Building2 className="w-3.5 h-3.5" />
                <span className="font-medium text-slate-700">{job.companyName || 'Company'}</span>
                {job.location && (
                  <>
                    <span>•</span>
                    <MapPin className="w-3.5 h-3.5" />
                    <span>{job.location}</span>
                  </>
                )}
              </div>
            </div>
          </div>

          {job.employmentType && (
            <span className="inline-flex items-center px-2.5 py-1 rounded-md text-xs font-semibold bg-slate-100 text-slate-700 shrink-0">
              {job.employmentType.replace(/_/g, ' ')}
            </span>
          )}
        </div>

        {/* Description snippet */}
        <p className="text-xs text-slate-600 line-clamp-2 mb-4 leading-relaxed">
          {job.description}
        </p>

        {/* Skills badges */}
        {skillsList.length > 0 && (
          <div className="flex flex-wrap gap-1.5 mb-4">
            {skillsList.slice(0, 4).map((skill, index) => (
              <span
                key={index}
                className="px-2 py-0.5 text-[11px] font-medium bg-indigo-50 text-indigo-700 rounded-md"
              >
                {skill}
              </span>
            ))}
            {skillsList.length > 4 && (
              <span className="px-1.5 py-0.5 text-[11px] text-slate-400">
                +{skillsList.length - 4} more
              </span>
            )}
          </div>
        )}
      </div>

      {/* Footer Info & Actions */}
      <div className="pt-3 border-t border-slate-100 flex flex-wrap items-center justify-between gap-2 text-xs text-slate-500">
        <div className="flex items-center gap-3">
          <div className="flex items-center gap-1 text-slate-800 font-semibold">
            <DollarSign className="w-3.5 h-3.5 text-slate-400" />
            <span>{formatSalary(job.salaryMin, job.salaryMax)}</span>
          </div>
          {job.createdAt && (
            <div className="flex items-center gap-1 text-slate-400">
              <Calendar className="w-3.5 h-3.5" />
              <span>Posted {formatDate(job.createdAt)}</span>
            </div>
          )}
          {isRecruiterView && (
            <div className="flex items-center gap-1 text-indigo-600 font-medium">
              <Users className="w-3.5 h-3.5" />
              <span>{job.applicantCount || 0} Applicants</span>
            </div>
          )}
        </div>

        <div className="flex items-center gap-2">
          <Link
            to={`/jobs/${job.id}`}
            className="px-3 py-1.5 text-xs font-medium text-slate-700 hover:text-indigo-600 hover:bg-slate-100 rounded-lg transition"
          >
            Details
          </Link>
          {!isRecruiterView && (
            <Link
              to={`/jobs/${job.id}`}
              className="px-3.5 py-1.5 text-xs font-semibold text-white bg-indigo-600 hover:bg-indigo-700 rounded-lg shadow-sm transition"
            >
              Apply Now
            </Link>
          )}
        </div>
      </div>
    </div>
  );
};

export default JobCard;
