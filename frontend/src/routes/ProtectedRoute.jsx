import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import LoadingSpinner from '../components/LoadingSpinner';

export const ProtectedRoute = ({ children, allowedRoles }) => {
  const { user, isAuthenticated, loading } = useAuth();
  const location = useLocation();

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-slate-50">
        <LoadingSpinner message="Verifying authentication..." />
      </div>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  const userRole = user?.role
    ? typeof user.role === 'string'
      ? user.role.replace('ROLE_', '')
      : user.role
    : null;

  const normalizedAllowed = allowedRoles?.map((r) =>
    typeof r === 'string' ? r.replace('ROLE_', '') : r
  );

  if (normalizedAllowed && !normalizedAllowed.includes(userRole)) {
    // Redirect to their default dashboard based on their role
    if (userRole === 'ADMIN') return <Navigate to="/admin/dashboard" replace />;
    if (userRole === 'RECRUITER') return <Navigate to="/recruiter/dashboard" replace />;
    if (userRole === 'CANDIDATE') return <Navigate to="/candidate/dashboard" replace />;
    return <Navigate to="/" replace />;
  }


  return children;
};

export default ProtectedRoute;
