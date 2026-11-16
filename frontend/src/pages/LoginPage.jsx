import React, { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Briefcase, Lock, Mail, AlertCircle, ArrowRight, ShieldCheck, UserCheck } from 'lucide-react';

export const LoginPage = () => {
  const { login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const searchParams = new URLSearchParams(location.search);
  const expired = searchParams.get('expired');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const data = await login(email, password);
      // Route based on role
      const userRole = data?.role ? String(data.role).replace('ROLE_', '') : '';
      if (userRole === 'ADMIN') {
        navigate('/admin/dashboard');
      } else if (userRole === 'RECRUITER') {
        navigate('/recruiter/dashboard');
      } else {
        navigate('/candidate/dashboard');
      }
    } catch (err) {
      setError(
        err.response?.data?.message || 'Invalid email or password. Please try again.'
      );
    } finally {
      setLoading(false);
    }
  };

  const handleDemoFill = (demoEmail, demoPassword) => {
    setEmail(demoEmail);
    setPassword(demoPassword);
    setError('');
  };

  return (
    <div className="min-h-[calc(100vh-8rem)] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-6">
        {/* Brand header */}
        <div className="text-center">
          <Link to="/" className="inline-flex items-center gap-2 mb-3">
            <div className="w-12 h-12 rounded-2xl bg-indigo-600 text-white flex items-center justify-center shadow-lg shadow-indigo-200">
              <Briefcase className="w-6 h-6" />
            </div>
          </Link>
          <h2 className="text-2xl font-extrabold text-slate-900">Sign in to JobTrack</h2>
          <p className="mt-1 text-xs text-slate-500">
            Welcome back! Select a demo role or sign in with your credentials.
          </p>
        </div>

        {/* Demo Quick Logins */}
        <div className="bg-indigo-50/60 border border-indigo-100 rounded-2xl p-4 space-y-2">
          <p className="text-[11px] font-bold uppercase tracking-wider text-indigo-800 text-center">
            ⚡ Quick Demo Logins (1-Click Fill)
          </p>
          <div className="grid grid-cols-3 gap-2">
            <button
              type="button"
              onClick={() => handleDemoFill('candidate@jobtrack.com', 'Password@123')}
              className="p-2 bg-white rounded-lg border border-indigo-200 hover:border-indigo-400 text-center transition hover:shadow-sm"
            >
              <div className="text-xs font-semibold text-slate-800">Candidate</div>
              <div className="text-[10px] text-slate-400">Job Seeker</div>
            </button>
            <button
              type="button"
              onClick={() => handleDemoFill('recruiter@jobtrack.com', 'Password@123')}
              className="p-2 bg-white rounded-lg border border-indigo-200 hover:border-indigo-400 text-center transition hover:shadow-sm"
            >
              <div className="text-xs font-semibold text-slate-800">Recruiter</div>
              <div className="text-[10px] text-slate-400">TechCorp HR</div>
            </button>
            <button
              type="button"
              onClick={() => handleDemoFill('admin@jobtrack.com', 'Password@123')}
              className="p-2 bg-white rounded-lg border border-indigo-200 hover:border-indigo-400 text-center transition hover:shadow-sm"
            >
              <div className="text-xs font-semibold text-slate-800">Admin</div>
              <div className="text-[10px] text-slate-400">Super User</div>
            </button>
          </div>
        </div>

        {expired && (
          <div className="p-3 bg-amber-50 border border-amber-200 rounded-xl text-xs text-amber-800 flex items-center gap-2">
            <AlertCircle className="w-4 h-4 shrink-0" />
            <span>Your session has expired. Please log in again to continue.</span>
          </div>
        )}

        {error && (
          <div className="p-3 bg-rose-50 border border-rose-200 rounded-xl text-xs text-rose-700 flex items-center gap-2">
            <AlertCircle className="w-4 h-4 shrink-0" />
            <span>{error}</span>
          </div>
        )}

        {/* Login form */}
        <form onSubmit={handleSubmit} className="bg-white p-6 sm:p-8 rounded-2xl border border-slate-200 shadow-sm space-y-4">
          <div>
            <label className="block text-xs font-semibold text-slate-700 mb-1">
              Email Address
            </label>
            <div className="relative">
              <Mail className="w-4 h-4 text-slate-400 absolute left-3 top-3" />
              <input
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="name@example.com"
                className="w-full pl-9 pr-3 py-2.5 text-xs border border-slate-200 rounded-xl focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
              />
            </div>
          </div>

          <div>
            <div className="flex items-center justify-between mb-1">
              <label className="block text-xs font-semibold text-slate-700">
                Password
              </label>
            </div>
            <div className="relative">
              <Lock className="w-4 h-4 text-slate-400 absolute left-3 top-3" />
              <input
                type="password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="••••••••"
                className="w-full pl-9 pr-3 py-2.5 text-xs border border-slate-200 rounded-xl focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
              />
            </div>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-3 px-4 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold text-sm rounded-xl transition shadow-md shadow-indigo-200 disabled:opacity-50 flex items-center justify-center gap-2"
          >
            {loading ? 'Authenticating...' : 'Sign In'}
            <ArrowRight className="w-4 h-4" />
          </button>
        </form>

        <p className="text-center text-xs text-slate-500">
          Don't have an account?{' '}
          <Link to="/register" className="text-indigo-600 font-semibold hover:underline">
            Register here
          </Link>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
