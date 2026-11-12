import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import {
  Briefcase,
  Search,
  MapPin,
  TrendingUp,
  ShieldCheck,
  Users,
  Building,
  CheckCircle,
  ArrowRight,
  Sparkles,
} from 'lucide-react';
import jobService from '../services/jobService';
import JobCard from '../components/JobCard';

export const LandingPage = () => {
  const [featuredJobs, setFeaturedJobs] = useState([]);
  const [searchTitle, setSearchTitle] = useState('');
  const [searchLocation, setSearchLocation] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchFeatured = async () => {
      try {
        const data = await jobService.searchJobs({ size: 6, sortBy: 'createdAt', sortDir: 'desc' });
        setFeaturedJobs(data.content || []);
      } catch {
        setFeaturedJobs([]);
      } finally {
        setLoading(false);
      }
    };
    fetchFeatured();
  }, []);

  const stats = [
    { label: 'Active Jobs', value: '1,500+' },
    { label: 'Partner Companies', value: '450+' },
    { label: 'Verified Candidates', value: '25,000+' },
    { label: 'Interviews Scheduled', value: '12,000+' },
  ];

  const popularCategories = [
    { title: 'Software Engineering', count: '420+ Openings' },
    { title: 'Cloud & DevOps', count: '180+ Openings' },
    { title: 'Data & Analytics', count: '230+ Openings' },
    { title: 'Product & Design', count: '140+ Openings' },
  ];

  return (
    <div className="space-y-16 pb-16">
      {/* Hero Section */}
      <section className="relative overflow-hidden bg-gradient-to-b from-indigo-50/60 via-white to-white py-16 lg:py-24">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center relative z-10">
          <div className="inline-flex items-center gap-2 px-3 py-1.5 rounded-full bg-indigo-100 text-indigo-700 text-xs font-semibold mb-6">
            <Sparkles className="w-3.5 h-3.5" />
            <span>Next-Generation Recruitment Management System</span>
          </div>

          <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold text-slate-900 tracking-tight max-w-4xl mx-auto leading-tight sm:leading-none">
            Find Your Dream Career or Hire <span className="text-indigo-600">World-Class Talent</span>
          </h1>

          <p className="mt-5 text-base sm:text-lg text-slate-600 max-w-2xl mx-auto leading-relaxed">
            JobTrack connects ambitious job seekers with industry-leading companies through streamlined job tracking, resume management, and automated interview scheduling.
          </p>

          {/* Quick Search Box */}
          <div className="mt-8 max-w-3xl mx-auto bg-white p-2.5 rounded-2xl shadow-xl border border-slate-200/80">
            <form
              onSubmit={(e) => {
                e.preventDefault();
                window.location.href = `/jobs?title=${encodeURIComponent(searchTitle)}&location=${encodeURIComponent(searchLocation)}`;
              }}
              className="flex flex-col md:flex-row items-center gap-2"
            >
              <div className="flex items-center gap-2 px-3 py-2 w-full md:w-1/2 border-b md:border-b-0 md:border-r border-slate-100">
                <Search className="w-5 h-5 text-slate-400 shrink-0" />
                <input
                  type="text"
                  placeholder="Job title, keywords, or role..."
                  value={searchTitle}
                  onChange={(e) => setSearchTitle(e.target.value)}
                  className="w-full text-sm outline-none bg-transparent placeholder-slate-400"
                />
              </div>

              <div className="flex items-center gap-2 px-3 py-2 w-full md:w-1/2">
                <MapPin className="w-5 h-5 text-slate-400 shrink-0" />
                <input
                  type="text"
                  placeholder="City, State, or Remote..."
                  value={searchLocation}
                  onChange={(e) => setSearchLocation(e.target.value)}
                  className="w-full text-sm outline-none bg-transparent placeholder-slate-400"
                />
              </div>

              <button
                type="submit"
                className="w-full md:w-auto px-6 py-3 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold text-sm rounded-xl transition shadow-md shadow-indigo-200 shrink-0"
              >
                Find Jobs
              </button>
            </form>
          </div>

          {/* Key metrics */}
          <div className="mt-12 grid grid-cols-2 md:grid-cols-4 gap-6 max-w-4xl mx-auto pt-8 border-t border-slate-200/70">
            {stats.map((s, idx) => (
              <div key={idx} className="text-center">
                <div className="text-2xl sm:text-3xl font-extrabold text-slate-900">{s.value}</div>
                <div className="text-xs font-medium text-slate-500 mt-1">{s.label}</div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Featured Jobs Section */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex flex-col sm:flex-row items-start sm:items-end justify-between gap-4 mb-8">
          <div>
            <h2 className="text-2xl font-bold text-slate-900">Featured Job Openings</h2>
            <p className="text-sm text-slate-500 mt-1">
              Explore high-growth opportunities handpicked from top tech companies.
            </p>
          </div>
          <Link
            to="/jobs"
            className="inline-flex items-center gap-1.5 text-sm font-semibold text-indigo-600 hover:text-indigo-800 transition"
          >
            <span>View all vacancies</span>
            <ArrowRight className="w-4 h-4" />
          </Link>
        </div>

        {loading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {[1, 2, 3, 4, 5, 6].map((i) => (
              <div key={i} className="h-48 bg-slate-100 rounded-xl animate-pulse" />
            ))}
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {featuredJobs.map((job) => (
              <JobCard key={job.id} job={job} />
            ))}
          </div>
        )}
      </section>

      {/* Popular Categories */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center max-w-xl mx-auto mb-10">
          <h2 className="text-2xl font-bold text-slate-900">Popular Career Tracks</h2>
          <p className="text-sm text-slate-500 mt-1">Discover roles tailored to your professional expertise.</p>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {popularCategories.map((cat, i) => (
            <Link
              key={i}
              to={`/jobs?title=${encodeURIComponent(cat.title)}`}
              className="p-5 bg-white rounded-xl border border-slate-200 hover:border-indigo-400 hover:shadow-md transition group text-left"
            >
              <div className="w-10 h-10 rounded-lg bg-indigo-50 text-indigo-600 flex items-center justify-center font-bold mb-3 group-hover:scale-105 transition">
                <Briefcase className="w-5 h-5" />
              </div>
              <h3 className="font-semibold text-slate-900 group-hover:text-indigo-600 transition text-sm">
                {cat.title}
              </h3>
              <p className="text-xs text-slate-400 mt-0.5">{cat.count}</p>
            </Link>
          ))}
        </div>
      </section>

      {/* How It Works */}
      <section className="bg-slate-50 py-16 border-y border-slate-200">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center max-w-xl mx-auto mb-12">
            <h2 className="text-2xl font-bold text-slate-900">How JobTrack Works</h2>
            <p className="text-sm text-slate-500 mt-1">
              A transparent, end-to-end recruitment lifecycle for candidates and recruiters.
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="bg-white p-6 rounded-xl border border-slate-200 shadow-sm text-center">
              <div className="w-12 h-12 rounded-xl bg-indigo-100 text-indigo-600 flex items-center justify-center mx-auto mb-4 font-bold text-lg">
                1
              </div>
              <h3 className="font-semibold text-slate-900 mb-2">Create Profile & Resume</h3>
              <p className="text-xs text-slate-600 leading-relaxed">
                Sign up as a job seeker, upload your PDF resume, outline skills, education, and career experience.
              </p>
            </div>

            <div className="bg-white p-6 rounded-xl border border-slate-200 shadow-sm text-center">
              <div className="w-12 h-12 rounded-xl bg-violet-100 text-violet-600 flex items-center justify-center mx-auto mb-4 font-bold text-lg">
                2
              </div>
              <h3 className="font-semibold text-slate-900 mb-2">Apply in One Click</h3>
              <p className="text-xs text-slate-600 leading-relaxed">
                Filter jobs by location, skills, and salary. Submit applications with a custom cover letter and your stored resume.
              </p>
            </div>

            <div className="bg-white p-6 rounded-xl border border-slate-200 shadow-sm text-center">
              <div className="w-12 h-12 rounded-xl bg-emerald-100 text-emerald-600 flex items-center justify-center mx-auto mb-4 font-bold text-lg">
                3
              </div>
              <h3 className="font-semibold text-slate-900 mb-2">Track & Interview</h3>
              <p className="text-xs text-slate-600 leading-relaxed">
                Receive notifications when recruiters review your application, shortlist your profile, and schedule interviews.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Recruiter / Employer Callout Banner */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="bg-gradient-to-r from-indigo-900 via-indigo-800 to-slate-900 rounded-3xl p-8 sm:p-12 text-white shadow-xl flex flex-col md:flex-row items-center justify-between gap-8">
          <div className="max-w-xl space-y-3">
            <span className="px-3 py-1 bg-white/10 rounded-full text-xs font-semibold uppercase tracking-wider text-indigo-200">
              For Employers & Hiring Teams
            </span>
            <h2 className="text-2xl sm:text-3xl font-bold leading-tight">
              Looking to Hire Exceptional Engineering & Tech Talent?
            </h2>
            <p className="text-sm text-indigo-100 leading-relaxed">
              Post job openings, manage candidates with our intuitive pipeline, review candidate resumes, and coordinate interviews in one place.
            </p>
          </div>

          <div className="flex flex-col sm:flex-row gap-3 shrink-0">
            <Link
              to="/register?role=recruiter"
              className="px-6 py-3 bg-white text-indigo-900 font-semibold text-sm rounded-xl hover:bg-indigo-50 transition shadow-lg text-center"
            >
              Start Hiring Today
            </Link>
            <Link
              to="/login"
              className="px-6 py-3 bg-white/10 text-white font-semibold text-sm rounded-xl hover:bg-white/20 transition text-center"
            >
              Recruiter Sign In
            </Link>
          </div>
        </div>
      </section>
    </div>
  );
};

export default LandingPage;
