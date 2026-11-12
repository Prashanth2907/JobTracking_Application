import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import NotificationDropdown from './NotificationDropdown';
import {
  Briefcase,
  Menu,
  X,
  User,
  LogOut,
  ChevronDown,
  LayoutDashboard,
  FileText,
  Calendar,
  Building,
  ShieldCheck,
  PlusCircle,
} from 'lucide-react';
import { getRoleBadge } from '../utils/formatters';

export const Navbar = () => {
  const { user, isAuthenticated, logout, role } = useAuth();
  const navigate = useNavigate();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [userDropdownOpen, setUserDropdownOpen] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const roleBadge = role ? getRoleBadge(role) : null;

  return (
    <header className="sticky top-0 z-40 bg-white/95 backdrop-blur border-b border-slate-200">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          {/* Brand Logo */}
          <Link to="/" className="flex items-center gap-2 group">
            <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-indigo-600 to-violet-500 flex items-center justify-center text-white shadow-md shadow-indigo-200 group-hover:scale-105 transition">
              <Briefcase className="w-5 h-5" />
            </div>
            <div className="flex flex-col">
              <span className="font-bold text-xl tracking-tight text-slate-900 group-hover:text-indigo-600 transition">
                JobTrack
              </span>
              <span className="text-[10px] -mt-1 font-semibold tracking-wider uppercase text-slate-400">
                Talent & Careers
              </span>
            </div>
          </Link>

          {/* Desktop Nav Links */}
          <nav className="hidden md:flex items-center space-x-1 lg:space-x-4">
            <Link
              to="/jobs"
              className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
            >
              Explore Jobs
            </Link>

            {isAuthenticated && role === 'CANDIDATE' && (
              <>
                <Link
                  to="/candidate/dashboard"
                  className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
                >
                  Dashboard
                </Link>
                <Link
                  to="/candidate/applications"
                  className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
                >
                  My Applications
                </Link>
                <Link
                  to="/candidate/interviews"
                  className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
                >
                  Interviews
                </Link>
              </>
            )}

            {isAuthenticated && role === 'RECRUITER' && (
              <>
                <Link
                  to="/recruiter/dashboard"
                  className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
                >
                  Dashboard
                </Link>
                <Link
                  to="/recruiter/jobs"
                  className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
                >
                  Manage Jobs
                </Link>
                <Link
                  to="/recruiter/interviews"
                  className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
                >
                  Interviews
                </Link>
                <Link
                  to="/recruiter/jobs/create"
                  className="inline-flex items-center gap-1.5 px-3 py-1.5 text-xs font-semibold text-white bg-indigo-600 rounded-lg hover:bg-indigo-700 shadow-sm transition"
                >
                  <PlusCircle className="w-3.5 h-3.5" />
                  Post Job
                </Link>
              </>
            )}

            {isAuthenticated && role === 'ADMIN' && (
              <>
                <Link
                  to="/admin/dashboard"
                  className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
                >
                  Admin Console
                </Link>
                <Link
                  to="/admin/users"
                  className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
                >
                  Users
                </Link>
                <Link
                  to="/admin/jobs"
                  className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
                >
                  Job Moderation
                </Link>
                <Link
                  to="/admin/applications"
                  className="px-3 py-2 text-sm font-medium text-slate-600 hover:text-indigo-600 rounded-lg hover:bg-slate-50 transition"
                >
                  Applications
                </Link>
              </>
            )}
          </nav>

          {/* Right Action Area */}
          <div className="hidden md:flex items-center space-x-3">
            {isAuthenticated ? (
              <>
                <NotificationDropdown />

                {/* Profile menu */}
                <div className="relative">
                  <button
                    onClick={() => setUserDropdownOpen(!userDropdownOpen)}
                    className="flex items-center gap-2 p-1.5 rounded-lg hover:bg-slate-100 transition text-left"
                  >
                    <div className="w-8 h-8 rounded-full bg-indigo-100 text-indigo-700 font-semibold text-xs flex items-center justify-center border border-indigo-200">
                      {user?.firstName ? user.firstName[0].toUpperCase() : 'U'}
                    </div>
                    <div className="hidden lg:block text-left">
                      <div className="text-xs font-semibold text-slate-800 leading-tight">
                        {user?.firstName} {user?.lastName}
                      </div>
                      <div className="text-[10px] text-slate-500">{user?.email}</div>
                    </div>
                    <ChevronDown className="w-3.5 h-3.5 text-slate-400" />
                  </button>

                  {userDropdownOpen && (
                    <div
                      className="absolute right-0 mt-2 w-56 bg-white rounded-xl shadow-xl border border-slate-100 py-1 z-50"
                      onClick={() => setUserDropdownOpen(false)}
                    >
                      <div className="px-4 py-2 border-b border-slate-100">
                        <p className="text-xs font-semibold text-slate-900">
                          {user?.firstName} {user?.lastName}
                        </p>
                        <p className="text-[11px] text-slate-500 truncate">{user?.email}</p>
                        {roleBadge && (
                          <span
                            className={`inline-block mt-1 px-2 py-0.5 rounded text-[10px] font-semibold ${roleBadge.bg}`}
                          >
                            {roleBadge.label}
                          </span>
                        )}
                      </div>

                      {role === 'CANDIDATE' && (
                        <>
                          <Link
                            to="/candidate/profile"
                            className="flex items-center gap-2 px-4 py-2 text-xs text-slate-700 hover:bg-slate-50"
                          >
                            <User className="w-3.5 h-3.5 text-slate-400" />
                            My Profile & Resume
                          </Link>
                          <Link
                            to="/candidate/applications"
                            className="flex items-center gap-2 px-4 py-2 text-xs text-slate-700 hover:bg-slate-50"
                          >
                            <FileText className="w-3.5 h-3.5 text-slate-400" />
                            Applications
                          </Link>
                        </>
                      )}

                      {role === 'RECRUITER' && (
                        <>
                          <Link
                            to="/recruiter/profile"
                            className="flex items-center gap-2 px-4 py-2 text-xs text-slate-700 hover:bg-slate-50"
                          >
                            <Building className="w-3.5 h-3.5 text-slate-400" />
                            Company Profile
                          </Link>
                          <Link
                            to="/recruiter/jobs"
                            className="flex items-center gap-2 px-4 py-2 text-xs text-slate-700 hover:bg-slate-50"
                          >
                            <Briefcase className="w-3.5 h-3.5 text-slate-400" />
                            My Job Postings
                          </Link>
                        </>
                      )}

                      {role === 'ADMIN' && (
                        <Link
                          to="/admin/dashboard"
                          className="flex items-center gap-2 px-4 py-2 text-xs text-slate-700 hover:bg-slate-50"
                        >
                          <ShieldCheck className="w-3.5 h-3.5 text-slate-400" />
                          Admin Console
                        </Link>
                      )}

                      <div className="border-t border-slate-100 my-1" />

                      <button
                        onClick={handleLogout}
                        className="w-full text-left flex items-center gap-2 px-4 py-2 text-xs text-rose-600 hover:bg-rose-50"
                      >
                        <LogOut className="w-3.5 h-3.5" />
                        Sign Out
                      </button>
                    </div>
                  )}
                </div>
              </>
            ) : (
              <>
                <Link
                  to="/login"
                  className="px-4 py-2 text-sm font-medium text-slate-700 hover:text-indigo-600 transition"
                >
                  Sign In
                </Link>
                <Link
                  to="/register"
                  className="px-4 py-2 text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 rounded-lg shadow-sm transition"
                >
                  Get Started
                </Link>
              </>
            )}
          </div>

          {/* Mobile hamburger */}
          <div className="flex md:hidden items-center gap-2">
            {isAuthenticated && <NotificationDropdown />}
            <button
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
              className="p-2 text-slate-600 hover:text-slate-900 rounded-lg hover:bg-slate-100"
            >
              {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Drawer */}
      {mobileMenuOpen && (
        <div className="md:hidden border-t border-slate-100 bg-white px-4 pt-2 pb-6 space-y-2">
          <Link
            to="/jobs"
            onClick={() => setMobileMenuOpen(false)}
            className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
          >
            Explore Jobs
          </Link>

          {isAuthenticated ? (
            <>
              {role === 'CANDIDATE' && (
                <>
                  <Link
                    to="/candidate/dashboard"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    Dashboard
                  </Link>
                  <Link
                    to="/candidate/applications"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    My Applications
                  </Link>
                  <Link
                    to="/candidate/profile"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    Profile & Resume
                  </Link>
                  <Link
                    to="/candidate/interviews"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    Interviews
                  </Link>
                </>
              )}

              {role === 'RECRUITER' && (
                <>
                  <Link
                    to="/recruiter/dashboard"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    Dashboard
                  </Link>
                  <Link
                    to="/recruiter/jobs"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    Job Postings
                  </Link>
                  <Link
                    to="/recruiter/jobs/create"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-indigo-600 hover:bg-indigo-50 font-semibold"
                  >
                    + Post New Job
                  </Link>
                  <Link
                    to="/recruiter/interviews"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    Interviews
                  </Link>
                </>
              )}

              {role === 'ADMIN' && (
                <>
                  <Link
                    to="/admin/dashboard"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    Dashboard
                  </Link>
                  <Link
                    to="/admin/users"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    User Management
                  </Link>
                  <Link
                    to="/admin/jobs"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    Job Moderation
                  </Link>
                  <Link
                    to="/admin/applications"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-base font-medium text-slate-700 hover:bg-slate-50"
                  >
                    Platform Applications
                  </Link>
                </>
              )}

              <div className="border-t border-slate-100 pt-3">
                <button
                  onClick={() => {
                    setMobileMenuOpen(false);
                    handleLogout();
                  }}
                  className="w-full text-left px-3 py-2 text-rose-600 font-medium"
                >
                  Sign Out
                </button>
              </div>
            </>
          ) : (
            <div className="pt-4 border-t border-slate-100 flex flex-col gap-2">
              <Link
                to="/login"
                onClick={() => setMobileMenuOpen(false)}
                className="w-full text-center px-4 py-2 border border-slate-300 rounded-lg text-slate-700 font-medium"
              >
                Sign In
              </Link>
              <Link
                to="/register"
                onClick={() => setMobileMenuOpen(false)}
                className="w-full text-center px-4 py-2 bg-indigo-600 text-white rounded-lg font-medium"
              >
                Get Started
              </Link>
            </div>
          )}
        </div>
      )}
    </header>
  );
};

export default Navbar;
