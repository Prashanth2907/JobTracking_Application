import React from 'react';
import { Link } from 'react-router-dom';
import { Briefcase, Heart, Github, Linkedin, Mail } from 'lucide-react';

export const Footer = () => {
  return (
    <footer className="bg-slate-900 text-slate-400 text-sm border-t border-slate-800">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-8">
          {/* Brand info */}
          <div className="space-y-4 md:col-span-1">
            <Link to="/" className="flex items-center gap-2">
              <div className="w-8 h-8 rounded-lg bg-indigo-600 flex items-center justify-center text-white">
                <Briefcase className="w-4 h-4" />
              </div>
              <span className="font-bold text-lg text-white">JobTrack</span>
            </Link>
            <p className="text-xs text-slate-400 leading-relaxed">
              Enterprise full-stack recruitment & job application management platform connecting top talent with high-growth companies.
            </p>
            <div className="flex items-center gap-3 text-slate-400">
              <a href="https://github.com" target="_blank" rel="noreferrer" className="hover:text-white transition">
                <Github className="w-4 h-4" />
              </a>
              <a href="https://linkedin.com" target="_blank" rel="noreferrer" className="hover:text-white transition">
                <Linkedin className="w-4 h-4" />
              </a>
              <a href="mailto:contact@jobtrack.com" className="hover:text-white transition">
                <Mail className="w-4 h-4" />
              </a>
            </div>
          </div>

          {/* Candidates */}
          <div>
            <h4 className="text-white font-semibold text-xs tracking-wider uppercase mb-3">For Candidates</h4>
            <ul className="space-y-2 text-xs">
              <li>
                <Link to="/jobs" className="hover:text-white transition">Browse All Jobs</Link>
              </li>
              <li>
                <Link to="/candidate/dashboard" className="hover:text-white transition">Candidate Dashboard</Link>
              </li>
              <li>
                <Link to="/candidate/applications" className="hover:text-white transition">Application Tracking</Link>
              </li>
              <li>
                <Link to="/candidate/profile" className="hover:text-white transition">Resume & Profile</Link>
              </li>
            </ul>
          </div>

          {/* Recruiters */}
          <div>
            <h4 className="text-white font-semibold text-xs tracking-wider uppercase mb-3">For Employers</h4>
            <ul className="space-y-2 text-xs">
              <li>
                <Link to="/recruiter/jobs/create" className="hover:text-white transition">Post a Job Vacancy</Link>
              </li>
              <li>
                <Link to="/recruiter/dashboard" className="hover:text-white transition">Recruiter Dashboard</Link>
              </li>
              <li>
                <Link to="/recruiter/jobs" className="hover:text-white transition">Candidate Pipeline</Link>
              </li>
              <li>
                <Link to="/recruiter/interviews" className="hover:text-white transition">Interview Scheduling</Link>
              </li>
            </ul>
          </div>

          {/* Architecture / Tech Stack */}
          <div>
            <h4 className="text-white font-semibold text-xs tracking-wider uppercase mb-3">Technology Stack</h4>
            <div className="flex flex-wrap gap-1.5 text-[11px]">
              <span className="px-2 py-0.5 bg-slate-800 text-indigo-400 rounded border border-slate-700">Spring Boot 3</span>
              <span className="px-2 py-0.5 bg-slate-800 text-indigo-400 rounded border border-slate-700">Java 17+</span>
              <span className="px-2 py-0.5 bg-slate-800 text-emerald-400 rounded border border-slate-700">MySQL 8</span>
              <span className="px-2 py-0.5 bg-slate-800 text-cyan-400 rounded border border-slate-700">React.js</span>
              <span className="px-2 py-0.5 bg-slate-800 text-sky-400 rounded border border-slate-700">Tailwind CSS</span>
              <span className="px-2 py-0.5 bg-slate-800 text-amber-400 rounded border border-slate-700">Spring Security & JWT</span>
              <span className="px-2 py-0.5 bg-slate-800 text-purple-400 rounded border border-slate-700">Recharts</span>
            </div>
            <div className="mt-4 p-2.5 bg-slate-800/60 rounded-lg border border-slate-800 text-[11px] text-slate-400">
              <span className="text-amber-400 font-semibold">Demo Credentials:</span> Password: <code className="text-white font-mono">Password@123</code> for all seed accounts.
            </div>
          </div>
        </div>

        <div className="pt-8 mt-8 border-t border-slate-800 flex flex-col sm:flex-row items-center justify-between text-xs text-slate-500 gap-4">
          <p>© {new Date().getFullYear()} JobTrack Inc. All rights reserved.</p>
          <div className="flex items-center gap-1">
            <span>Crafted with</span>
            <Heart className="w-3.5 h-3.5 text-rose-500 fill-rose-500 inline" />
            <span>for Full Stack Software Engineering.</span>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
