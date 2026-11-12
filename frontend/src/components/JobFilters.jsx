import React from 'react';
import { Search, MapPin, Briefcase, DollarSign, X } from 'lucide-react';

export const JobFilters = ({ filters, onFilterChange, onReset }) => {
  const employmentTypes = [
    { value: '', label: 'All Employment Types' },
    { value: 'FULL_TIME', label: 'Full Time' },
    { value: 'PART_TIME', label: 'Part Time' },
    { value: 'CONTRACT', label: 'Contract' },
    { value: 'REMOTE', label: 'Remote' },
    { value: 'INTERNSHIP', label: 'Internship' },
  ];

  const hasActiveFilters = Boolean(
    filters.title ||
    filters.location ||
    filters.employmentType ||
    filters.skills ||
    filters.minSalary
  );

  return (
    <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-sm space-y-4">
      <div className="flex items-center justify-between pb-3 border-b border-slate-100">
        <h3 className="text-sm font-bold text-slate-800 uppercase tracking-wider">
          Filter Vacancies
        </h3>
        {hasActiveFilters && (
          <button
            onClick={onReset}
            className="flex items-center gap-1 text-xs text-rose-600 hover:text-rose-700 font-medium"
          >
            <X className="w-3.5 h-3.5" />
            Reset all
          </button>
        )}
      </div>

      {/* Keyword search */}
      <div>
        <label className="block text-xs font-semibold text-slate-700 mb-1">
          Job Title or Role
        </label>
        <div className="relative">
          <Search className="w-4 h-4 text-slate-400 absolute left-3 top-2.5" />
          <input
            type="text"
            placeholder="e.g. Software Engineer..."
            value={filters.title || ''}
            onChange={(e) => onFilterChange('title', e.target.value)}
            className="w-full pl-9 pr-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
          />
        </div>
      </div>

      {/* Location */}
      <div>
        <label className="block text-xs font-semibold text-slate-700 mb-1">
          Location
        </label>
        <div className="relative">
          <MapPin className="w-4 h-4 text-slate-400 absolute left-3 top-2.5" />
          <input
            type="text"
            placeholder="e.g. San Francisco, Remote..."
            value={filters.location || ''}
            onChange={(e) => onFilterChange('location', e.target.value)}
            className="w-full pl-9 pr-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
          />
        </div>
      </div>

      {/* Employment Type */}
      <div>
        <label className="block text-xs font-semibold text-slate-700 mb-1">
          Employment Type
        </label>
        <div className="relative">
          <Briefcase className="w-4 h-4 text-slate-400 absolute left-3 top-2.5" />
          <select
            value={filters.employmentType || ''}
            onChange={(e) => onFilterChange('employmentType', e.target.value)}
            className="w-full pl-9 pr-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500 bg-white"
          >
            {employmentTypes.map((t) => (
              <option key={t.value} value={t.value}>
                {t.label}
              </option>
            ))}
          </select>
        </div>
      </div>

      {/* Skills filter */}
      <div>
        <label className="block text-xs font-semibold text-slate-700 mb-1">
          Required Skills
        </label>
        <input
          type="text"
          placeholder="e.g. React, Java, Spring..."
          value={filters.skills || ''}
          onChange={(e) => onFilterChange('skills', e.target.value)}
          className="w-full px-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500"
        />
      </div>

      {/* Min Salary */}
      <div>
        <label className="block text-xs font-semibold text-slate-700 mb-1">
          Minimum Salary ($/yr)
        </label>
        <div className="relative">
          <DollarSign className="w-4 h-4 text-slate-400 absolute left-3 top-2.5" />
          <input
            type="number"
            placeholder="e.g. 80000"
            step="5000"
            value={filters.minSalary || ''}
            onChange={(e) => onFilterChange('minSalary', e.target.value)}
            className="w-full pl-9 pr-3 py-2 text-xs border border-slate-200 rounded-lg focus:outline-none focus:border-indigo-500"
          />
        </div>
      </div>
    </div>
  );
};

export default JobFilters;
