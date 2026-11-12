import React from 'react';
import { NavLink } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import {
  LayoutDashboard,
  FileText,
  User,
  Calendar,
  Briefcase,
  PlusCircle,
  Building,
  Users,
  ShieldCheck,
  LogOut,
} from 'lucide-react';

export const Sidebar = () => {
  const { role, logout, user } = useAuth();

  const candidateLinks = [
    { to: '/candidate/dashboard', icon: LayoutDashboard, label: 'Dashboard' },
    { to: '/candidate/applications', icon: FileText, label: 'My Applications' },
    { to: '/candidate/profile', icon: User, label: 'Profile & Resume' },
    { to: '/candidate/interviews', icon: Calendar, label: 'Interviews' },
  ];

  const recruiterLinks = [
    { to: '/recruiter/dashboard', icon: LayoutDashboard, label: 'Dashboard' },
    { to: '/recruiter/jobs', icon: Briefcase, label: 'Job Postings' },
    { to: '/recruiter/jobs/create', icon: PlusCircle, label: 'Post New Job' },
    { to: '/recruiter/interviews', icon: Calendar, label: 'Interviews' },
    { to: '/recruiter/profile', icon: Building, label: 'Company Profile' },
  ];

  const adminLinks = [
    { to: '/admin/dashboard', icon: LayoutDashboard, label: 'Dashboard' },
    { to: '/admin/users', icon: Users, label: 'Manage Users' },
    { to: '/admin/jobs', icon: Briefcase, label: 'Job Moderation' },
    { to: '/admin/applications', icon: FileText, label: 'Applications' },
  ];

  let links = [];
  if (role === 'CANDIDATE') links = candidateLinks;
  else if (role === 'RECRUITER') links = recruiterLinks;
  else if (role === 'ADMIN') links = adminLinks;

  return (
    <aside className="w-64 bg-white border-r border-slate-200 hidden md:flex flex-col justify-between p-4 min-h-[calc(100vh-4rem)]">
      <div className="space-y-6">
        {/* User Card */}
        <div className="p-3 bg-slate-50 rounded-xl border border-slate-200/60">
          <p className="text-xs font-semibold text-slate-900 truncate">
            {user?.firstName} {user?.lastName}
          </p>
          <p className="text-[11px] text-slate-500 truncate">{user?.email}</p>
          <div className="mt-2 text-[10px] font-bold uppercase tracking-wider text-indigo-600 bg-indigo-50 inline-block px-2 py-0.5 rounded">
            {role} PORTAL
          </div>
        </div>

        {/* Navigation list */}
        <nav className="space-y-1">
          {links.map((item) => {
            const Icon = item.icon;
            return (
              <NavLink
                key={item.to}
                to={item.to}
                end={item.to.endsWith('/dashboard')}
                className={({ isActive }) =>
                  `flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition ${
                    isActive
                      ? 'bg-indigo-50 text-indigo-700 font-semibold'
                      : 'text-slate-600 hover:text-slate-900 hover:bg-slate-50'
                  }`
                }
              >
                <Icon className="w-4 h-4" />
                <span>{item.label}</span>
              </NavLink>
            );
          })}
        </nav>
      </div>

      <div className="border-t border-slate-200 pt-4">
        <button
          onClick={logout}
          className="w-full flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium text-rose-600 hover:bg-rose-50 transition"
        >
          <LogOut className="w-4 h-4" />
          <span>Sign Out</span>
        </button>
      </div>
    </aside>
  );
};

export default Sidebar;
