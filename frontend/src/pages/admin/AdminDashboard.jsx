import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import adminService from '../../services/adminService';
import LoadingSpinner from '../../components/LoadingSpinner';
import {
  Users,
  Briefcase,
  Building,
  FileText,
  ShieldCheck,
  UserCheck,
  TrendingUp,
  Activity,
  Server,
  ArrowRight,
} from 'lucide-react';
import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  Cell,
  PieChart,
  Pie,
} from 'recharts';

export const AdminDashboard = () => {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      setLoading(true);
      try {
        const data = await adminService.getStats();
        setStats(data);
      } catch {
        // Fallback default stats for display
        setStats({
          totalCandidates: 3,
          totalRecruiters: 2,
          totalCompanies: 2,
          totalJobs: 5,
          totalApplications: 4,
        });
      } finally {
        setLoading(false);
      }
    };
    fetchStats();
  }, []);

  if (loading) {
    return (
      <div className="py-24">
        <LoadingSpinner message="Loading administrator analytics..." />
      </div>
    );
  }

  const userDistributionData = [
    { name: 'Candidates', value: stats?.totalCandidates || 0, color: '#10b981' },
    { name: 'Recruiters', value: stats?.totalRecruiters || 0, color: '#6366f1' },
  ];

  const platformActivityData = [
    { name: 'Users', count: (stats?.totalCandidates || 0) + (stats?.totalRecruiters || 0), color: '#3b82f6' },
    { name: 'Companies', count: stats?.totalCompanies || 0, color: '#8b5cf6' },
    { name: 'Jobs', count: stats?.totalJobs || 0, color: '#f59e0b' },
    { name: 'Applications', count: stats?.totalApplications || 0, color: '#ec4899' },
  ];

  return (
    <div className="space-y-6">
      {/* Header Banner */}
      <div className="bg-gradient-to-r from-purple-900 via-indigo-900 to-slate-900 rounded-2xl p-6 sm:p-8 text-white shadow-xl flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
        <div>
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-purple-500/20 text-purple-200 text-xs font-semibold uppercase tracking-wider mb-2">
            <ShieldCheck className="w-3.5 h-3.5" />
            <span>JobTrack Super Admin Console</span>
          </div>
          <h1 className="text-2xl sm:text-3xl font-bold">Platform Overview & Management</h1>
          <p className="text-xs sm:text-sm text-purple-200 mt-1 max-w-xl">
            Monitor real-time platform metrics, moderate job postings, oversee registered accounts, and audit all applications.
          </p>
        </div>

        <div className="flex items-center gap-2">
          <Link
            to="/admin/users"
            className="px-4 py-2 bg-white/10 hover:bg-white/20 text-white font-semibold text-xs rounded-xl transition"
          >
            Manage Users
          </Link>
          <Link
            to="/admin/jobs"
            className="px-4 py-2 bg-white text-purple-950 font-semibold text-xs rounded-xl shadow hover:bg-purple-50 transition"
          >
            Job Moderation
          </Link>
        </div>
      </div>

      {/* 5-Column Stats Cards */}
      <div className="grid grid-cols-2 sm:grid-cols-5 gap-4">
        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Candidates</span>
            <Users className="w-4 h-4 text-emerald-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{stats?.totalCandidates || 0}</div>
          <p className="text-[11px] text-slate-400 mt-1">Job seekers</p>
        </div>

        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Recruiters</span>
            <UserCheck className="w-4 h-4 text-indigo-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{stats?.totalRecruiters || 0}</div>
          <p className="text-[11px] text-slate-400 mt-1">Hiring managers</p>
        </div>

        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Companies</span>
            <Building className="w-4 h-4 text-violet-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{stats?.totalCompanies || 0}</div>
          <p className="text-[11px] text-slate-400 mt-1">Registered employers</p>
        </div>

        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Total Jobs</span>
            <Briefcase className="w-4 h-4 text-amber-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{stats?.totalJobs || 0}</div>
          <p className="text-[11px] text-slate-400 mt-1">Platform listings</p>
        </div>

        <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm col-span-2 sm:col-span-1">
          <div className="flex items-center justify-between text-slate-400 mb-2">
            <span className="text-xs font-semibold text-slate-500 uppercase">Applications</span>
            <FileText className="w-4 h-4 text-rose-500" />
          </div>
          <div className="text-2xl font-bold text-slate-900">{stats?.totalApplications || 0}</div>
          <p className="text-[11px] text-slate-400 mt-1">Submissions processed</p>
        </div>
      </div>

      {/* Analytics Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Platform Volume Distribution */}
        <div className="lg:col-span-2 bg-white p-6 rounded-2xl border border-slate-200 shadow-sm">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider">
              Platform Data Distribution
            </h3>
            <span className="text-xs text-slate-400">Entities in MySQL</span>
          </div>

          <div className="h-60 w-full">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={platformActivityData} margin={{ top: 10, right: 10, left: -20, bottom: 0 }}>
                <XAxis dataKey="name" tick={{ fontSize: 11 }} />
                <YAxis allowDecimals={false} tick={{ fontSize: 11 }} />
                <Tooltip
                  formatter={(val) => [`${val} Records`, 'Count']}
                  contentStyle={{ backgroundColor: '#1e293b', borderRadius: '8px', color: '#fff', fontSize: '12px' }}
                />
                <Bar dataKey="count" radius={[6, 6, 0, 0]}>
                  {platformActivityData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={entry.color} />
                  ))}
                </Bar>
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* User Roles Ratio Pie Chart */}
        <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm flex flex-col justify-between">
          <div>
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-sm font-bold text-slate-900 uppercase tracking-wider">
                User Breakdown
              </h3>
              <span className="text-xs text-slate-400">By Role</span>
            </div>

            <div className="h-44 w-full flex items-center justify-center">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={userDistributionData}
                    dataKey="value"
                    nameKey="name"
                    cx="50%"
                    cy="50%"
                    outerRadius={60}
                    innerRadius={35}
                    paddingAngle={5}
                  >
                    {userDistributionData.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Pie>
                  <Tooltip
                    formatter={(val) => [`${val} Users`, 'Total']}
                    contentStyle={{ backgroundColor: '#1e293b', borderRadius: '8px', color: '#fff', fontSize: '12px' }}
                  />
                </PieChart>
              </ResponsiveContainer>
            </div>

            <div className="flex items-center justify-center gap-6 text-xs text-slate-600 mt-2">
              <div className="flex items-center gap-1.5">
                <div className="w-3 h-3 rounded-full bg-emerald-500" />
                <span>Candidates ({stats?.totalCandidates || 0})</span>
              </div>
              <div className="flex items-center gap-1.5">
                <div className="w-3 h-3 rounded-full bg-indigo-500" />
                <span>Recruiters ({stats?.totalRecruiters || 0})</span>
              </div>
            </div>
          </div>

          <div className="pt-4 border-t border-slate-100 mt-4">
            <Link
              to="/admin/users"
              className="text-xs text-purple-700 font-semibold hover:underline flex items-center justify-between"
            >
              <span>Manage all accounts</span>
              <ArrowRight className="w-3.5 h-3.5" />
            </Link>
          </div>
        </div>
      </div>

      {/* System Health / Quick Operations */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <Link
          to="/admin/users"
          className="p-5 bg-white rounded-2xl border border-slate-200 hover:border-purple-300 hover:shadow-md transition group"
        >
          <div className="flex items-center gap-3 mb-2">
            <div className="w-10 h-10 rounded-xl bg-purple-50 text-purple-700 flex items-center justify-center font-bold">
              <Users className="w-5 h-5" />
            </div>
            <div>
              <h4 className="text-sm font-bold text-slate-900 group-hover:text-purple-700 transition">
                Manage User Accounts
              </h4>
              <p className="text-[11px] text-slate-400">Activate or deactivate user logins</p>
            </div>
          </div>
        </Link>

        <Link
          to="/admin/jobs"
          className="p-5 bg-white rounded-2xl border border-slate-200 hover:border-amber-300 hover:shadow-md transition group"
        >
          <div className="flex items-center gap-3 mb-2">
            <div className="w-10 h-10 rounded-xl bg-amber-50 text-amber-700 flex items-center justify-center font-bold">
              <Briefcase className="w-5 h-5" />
            </div>
            <div>
              <h4 className="text-sm font-bold text-slate-900 group-hover:text-amber-700 transition">
                Job Posting Moderation
              </h4>
              <p className="text-[11px] text-slate-400">Review & remove inappropriate postings</p>
            </div>
          </div>
        </Link>

        <Link
          to="/admin/applications"
          className="p-5 bg-white rounded-2xl border border-slate-200 hover:border-rose-300 hover:shadow-md transition group"
        >
          <div className="flex items-center gap-3 mb-2">
            <div className="w-10 h-10 rounded-xl bg-rose-50 text-rose-700 flex items-center justify-center font-bold">
              <FileText className="w-5 h-5" />
            </div>
            <div>
              <h4 className="text-sm font-bold text-slate-900 group-hover:text-rose-700 transition">
                Platform Application Audit
              </h4>
              <p className="text-[11px] text-slate-400">Monitor all applications and statuses</p>
            </div>
          </div>
        </Link>
      </div>
    </div>
  );
};

export default AdminDashboard;
