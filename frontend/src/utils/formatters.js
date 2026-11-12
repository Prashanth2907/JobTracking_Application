export const formatDate = (dateString) => {
  if (!dateString) return 'N/A';
  try {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  } catch {
    return dateString;
  }
};

export const formatDateTime = (dateString) => {
  if (!dateString) return 'N/A';
  try {
    const date = new Date(dateString);
    return date.toLocaleString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  } catch {
    return dateString;
  }
};

export const formatSalary = (min, max) => {
  if (!min && !max) return 'Competitive';
  const fmt = (num) => `$${Number(num).toLocaleString()}`;
  if (min && max) return `${fmt(min)} - ${fmt(max)}`;
  if (min) return `From ${fmt(min)}`;
  return `Up to ${fmt(max)}`;
};

export const getStatusBadgeClass = (status) => {
  switch (status) {
    case 'APPLIED':
      return 'bg-blue-50 text-blue-700 border-blue-200';
    case 'UNDER_REVIEW':
      return 'bg-amber-50 text-amber-700 border-amber-200';
    case 'SHORTLISTED':
      return 'bg-indigo-50 text-indigo-700 border-indigo-200';
    case 'INTERVIEW_SCHEDULED':
      return 'bg-purple-50 text-purple-700 border-purple-200';
    case 'SELECTED':
      return 'bg-emerald-50 text-emerald-700 border-emerald-200';
    case 'REJECTED':
      return 'bg-rose-50 text-rose-700 border-rose-200';
    case 'PUBLISHED':
    case 'ACTIVE':
      return 'bg-emerald-50 text-emerald-700 border-emerald-200';
    case 'DRAFT':
      return 'bg-slate-50 text-slate-700 border-slate-200';
    case 'CLOSED':
    case 'INACTIVE':
      return 'bg-rose-50 text-rose-700 border-rose-200';
    default:
      return 'bg-slate-100 text-slate-700 border-slate-200';
  }
};

export const getStatusLabel = (status) => {
  switch (status) {
    case 'APPLIED':
      return 'Applied';
    case 'UNDER_REVIEW':
      return 'Under Review';
    case 'SHORTLISTED':
      return 'Shortlisted';
    case 'INTERVIEW_SCHEDULED':
      return 'Interview Scheduled';
    case 'SELECTED':
      return 'Selected / Offered';
    case 'REJECTED':
      return 'Rejected';
    default:
      return status ? status.replace(/_/g, ' ') : '';
  }
};

export const getRoleBadge = (role) => {
  switch (role) {
    case 'ADMIN':
      return { label: 'Administrator', bg: 'bg-purple-100 text-purple-800' };
    case 'RECRUITER':
      return { label: 'Recruiter', bg: 'bg-blue-100 text-blue-800' };
    case 'CANDIDATE':
      return { label: 'Job Seeker', bg: 'bg-emerald-100 text-emerald-800' };
    default:
      return { label: role, bg: 'bg-slate-100 text-slate-800' };
  }
};
