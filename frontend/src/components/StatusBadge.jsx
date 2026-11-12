import React from 'react';
import { getStatusBadgeClass, getStatusLabel } from '../utils/formatters';

export const StatusBadge = ({ status }) => {
  if (!status) return null;
  const badgeClass = getStatusBadgeClass(status);
  const label = getStatusLabel(status);

  return (
    <span
      className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold border ${badgeClass}`}
    >
      {label}
    </span>
  );
};

export default StatusBadge;
