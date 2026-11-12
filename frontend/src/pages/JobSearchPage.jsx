import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import jobService from '../services/jobService';
import JobCard from '../components/JobCard';
import JobFilters from '../components/JobFilters';
import LoadingSpinner from '../components/LoadingSpinner';
import EmptyState from '../components/EmptyState';
import { Search, ChevronLeft, ChevronRight, SlidersHorizontal } from 'lucide-react';

export const JobSearchPage = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);

  const [filters, setFilters] = useState({
    title: searchParams.get('title') || '',
    location: searchParams.get('location') || '',
    employmentType: searchParams.get('employmentType') || '',
    skills: searchParams.get('skills') || '',
    minSalary: searchParams.get('minSalary') || '',
  });

  const [page, setPage] = useState(0);
  const [size] = useState(9);
  const [sortBy, setSortBy] = useState('createdAt');
  const [sortDir, setSortDir] = useState('desc');

  const [jobs, setJobs] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [loading, setLoading] = useState(true);
  const [mobileFilterOpen, setMobileFilterOpen] = useState(false);

  const fetchJobs = async () => {
    setLoading(true);
    try {
      const params = {
        page,
        size,
        sortBy,
        sortDir,
        ...(filters.title ? { title: filters.title } : {}),
        ...(filters.location ? { location: filters.location } : {}),
        ...(filters.employmentType ? { employmentType: filters.employmentType } : {}),
        ...(filters.skills ? { skills: filters.skills } : {}),
        ...(filters.minSalary ? { minSalary: filters.minSalary } : {}),
      };

      const data = await jobService.searchJobs(params);
      setJobs(data.content || []);
      setTotalPages(data.totalPages || 0);
      setTotalElements(data.totalElements || 0);
    } catch {
      setJobs([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchJobs();
  }, [filters, page, sortBy, sortDir]);

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
    setPage(0); // reset to page 0 on filter change
  };

  const handleResetFilters = () => {
    setFilters({
      title: '',
      location: '',
      employmentType: '',
      skills: '',
      minSalary: '',
    });
    setPage(0);
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
      {/* Page Header */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 pb-6 border-b border-slate-200">
        <div>
          <h1 className="text-2xl font-extrabold text-slate-900 tracking-tight">Explore Job Vacancies</h1>
          <p className="text-xs text-slate-500 mt-1">
            Browse through active opportunities from high-growth startups and tech giants.
          </p>
        </div>

        <div className="flex items-center gap-3 w-full sm:w-auto justify-between sm:justify-end">
          <button
            onClick={() => setMobileFilterOpen(!mobileFilterOpen)}
            className="md:hidden inline-flex items-center gap-2 px-3 py-2 text-xs font-semibold text-slate-700 bg-white border border-slate-200 rounded-lg shadow-sm"
          >
            <SlidersHorizontal className="w-3.5 h-3.5" />
            Filters
          </button>

          <div className="flex items-center gap-2 text-xs text-slate-600">
            <span className="font-medium">Sort by:</span>
            <select
              value={`${sortBy}-${sortDir}`}
              onChange={(e) => {
                const [sb, sd] = e.target.value.split('-');
                setSortBy(sb);
                setSortDir(sd);
              }}
              className="px-2.5 py-1.5 text-xs font-medium border border-slate-200 rounded-lg bg-white focus:outline-none focus:border-indigo-500"
            >
              <option value="createdAt-desc">Newest First</option>
              <option value="createdAt-asc">Oldest First</option>
              <option value="salaryMax-desc">Highest Salary</option>
              <option value="title-asc">Title (A-Z)</option>
            </select>
          </div>
        </div>
      </div>

      {/* Main Content Layout */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        {/* Desktop Filter Sidebar */}
        <div className="hidden md:block md:col-span-1">
          <JobFilters
            filters={filters}
            onFilterChange={handleFilterChange}
            onReset={handleResetFilters}
          />
        </div>

        {/* Mobile Filter Drawer */}
        {mobileFilterOpen && (
          <div className="md:hidden col-span-1">
            <JobFilters
              filters={filters}
              onFilterChange={handleFilterChange}
              onReset={handleResetFilters}
            />
          </div>
        )}

        {/* Job Grid */}
        <div className="md:col-span-3 space-y-6">
          <div className="text-xs font-medium text-slate-500 flex items-center justify-between">
            <span>
              Showing <strong className="text-slate-800">{totalElements}</strong> open vacancies
            </span>
          </div>

          {loading ? (
            <div className="py-16">
              <LoadingSpinner message="Searching matching vacancies..." />
            </div>
          ) : jobs.length === 0 ? (
            <EmptyState
              title="No jobs found matching criteria"
              description="Try adjusting your keywords, removing location filters, or resetting search filters."
              actionLabel="Reset all filters"
              onAction={handleResetFilters}
            />
          ) : (
            <>
              <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
                {jobs.map((job) => (
                  <JobCard key={job.id} job={job} />
                ))}
              </div>

              {/* Pagination controls */}
              {totalPages > 1 && (
                <div className="pt-6 border-t border-slate-200 flex items-center justify-between">
                  <button
                    onClick={() => setPage((p) => Math.max(0, p - 1))}
                    disabled={page === 0}
                    className="inline-flex items-center gap-1 px-3 py-1.5 text-xs font-medium text-slate-700 bg-white border border-slate-200 rounded-lg hover:bg-slate-50 disabled:opacity-40 transition"
                  >
                    <ChevronLeft className="w-4 h-4" />
                    Previous
                  </button>

                  <span className="text-xs font-medium text-slate-600">
                    Page {page + 1} of {totalPages}
                  </span>

                  <button
                    onClick={() => setPage((p) => Math.min(totalPages - 1, p + 1))}
                    disabled={page >= totalPages - 1}
                    className="inline-flex items-center gap-1 px-3 py-1.5 text-xs font-medium text-slate-700 bg-white border border-slate-200 rounded-lg hover:bg-slate-50 disabled:opacity-40 transition"
                  >
                    Next
                    <ChevronRight className="w-4 h-4" />
                  </button>
                </div>
              )}
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default JobSearchPage;
