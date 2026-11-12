import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import ProtectedRoute from './ProtectedRoute';
import Navbar from '../components/Navbar';
import Sidebar from '../components/Sidebar';
import Footer from '../components/Footer';

// Public Pages
import LandingPage from '../pages/LandingPage';
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/RegisterPage';
import JobSearchPage from '../pages/JobSearchPage';
import JobDetailsPage from '../pages/JobDetailsPage';

// Candidate Pages
import CandidateDashboard from '../pages/candidate/CandidateDashboard';
import CandidateApplications from '../pages/candidate/CandidateApplications';
import CandidateProfilePage from '../pages/candidate/CandidateProfilePage';
import CandidateInterviews from '../pages/candidate/CandidateInterviews';

// Recruiter Pages
import RecruiterDashboard from '../pages/recruiter/RecruiterDashboard';
import RecruiterJobsPage from '../pages/recruiter/RecruiterJobsPage';
import RecruiterCreateJobPage from '../pages/recruiter/RecruiterCreateJobPage';
import RecruiterEditJobPage from '../pages/recruiter/RecruiterEditJobPage';
import RecruiterJobApplicantsPage from '../pages/recruiter/RecruiterJobApplicantsPage';
import RecruiterInterviewsPage from '../pages/recruiter/RecruiterInterviewsPage';
import RecruiterCompanyProfilePage from '../pages/recruiter/RecruiterCompanyProfilePage';

// Admin Pages
import AdminDashboard from '../pages/admin/AdminDashboard';
import AdminUsersPage from '../pages/admin/AdminUsersPage';
import AdminJobsPage from '../pages/admin/AdminJobsPage';
import AdminApplicationsPage from '../pages/admin/AdminApplicationsPage';

// Layout wrappers
const PublicLayout = ({ children }) => (
  <div className="min-h-screen flex flex-col bg-slate-50">
    <Navbar />
    <main className="flex-1">{children}</main>
    <Footer />
  </div>
);

const DashboardLayout = ({ children }) => (
  <div className="min-h-screen flex flex-col bg-slate-50">
    <Navbar />
    <div className="flex-1 flex">
      <Sidebar />
      <main className="flex-1 p-4 sm:p-6 lg:p-8 max-w-7xl mx-auto w-full">
        {children}
      </main>
    </div>
  </div>
);

export const AppRoutes = () => {
  return (
    <Routes>
      {/* Public Routes */}
      <Route
        path="/"
        element={
          <PublicLayout>
            <LandingPage />
          </PublicLayout>
        }
      />
      <Route
        path="/login"
        element={
          <PublicLayout>
            <LoginPage />
          </PublicLayout>
        }
      />
      <Route
        path="/register"
        element={
          <PublicLayout>
            <RegisterPage />
          </PublicLayout>
        }
      />
      <Route
        path="/jobs"
        element={
          <PublicLayout>
            <JobSearchPage />
          </PublicLayout>
        }
      />
      <Route
        path="/jobs/:id"
        element={
          <PublicLayout>
            <JobDetailsPage />
          </PublicLayout>
        }
      />

      {/* Candidate Routes */}
      <Route
        path="/candidate/dashboard"
        element={
          <ProtectedRoute allowedRoles={['CANDIDATE']}>
            <DashboardLayout>
              <CandidateDashboard />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/candidate/applications"
        element={
          <ProtectedRoute allowedRoles={['CANDIDATE']}>
            <DashboardLayout>
              <CandidateApplications />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/candidate/profile"
        element={
          <ProtectedRoute allowedRoles={['CANDIDATE']}>
            <DashboardLayout>
              <CandidateProfilePage />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/candidate/interviews"
        element={
          <ProtectedRoute allowedRoles={['CANDIDATE']}>
            <DashboardLayout>
              <CandidateInterviews />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />

      {/* Recruiter Routes */}
      <Route
        path="/recruiter/dashboard"
        element={
          <ProtectedRoute allowedRoles={['RECRUITER']}>
            <DashboardLayout>
              <RecruiterDashboard />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/recruiter/jobs"
        element={
          <ProtectedRoute allowedRoles={['RECRUITER']}>
            <DashboardLayout>
              <RecruiterJobsPage />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/recruiter/jobs/create"
        element={
          <ProtectedRoute allowedRoles={['RECRUITER']}>
            <DashboardLayout>
              <RecruiterCreateJobPage />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/recruiter/jobs/:id/edit"
        element={
          <ProtectedRoute allowedRoles={['RECRUITER']}>
            <DashboardLayout>
              <RecruiterEditJobPage />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/recruiter/jobs/:jobId/applicants"
        element={
          <ProtectedRoute allowedRoles={['RECRUITER']}>
            <DashboardLayout>
              <RecruiterJobApplicantsPage />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/recruiter/interviews"
        element={
          <ProtectedRoute allowedRoles={['RECRUITER']}>
            <DashboardLayout>
              <RecruiterInterviewsPage />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/recruiter/profile"
        element={
          <ProtectedRoute allowedRoles={['RECRUITER']}>
            <DashboardLayout>
              <RecruiterCompanyProfilePage />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />

      {/* Admin Routes */}
      <Route
        path="/admin/dashboard"
        element={
          <ProtectedRoute allowedRoles={['ADMIN']}>
            <DashboardLayout>
              <AdminDashboard />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/admin/users"
        element={
          <ProtectedRoute allowedRoles={['ADMIN']}>
            <DashboardLayout>
              <AdminUsersPage />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/admin/jobs"
        element={
          <ProtectedRoute allowedRoles={['ADMIN']}>
            <DashboardLayout>
              <AdminJobsPage />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />
      <Route
        path="/admin/applications"
        element={
          <ProtectedRoute allowedRoles={['ADMIN']}>
            <DashboardLayout>
              <AdminApplicationsPage />
            </DashboardLayout>
          </ProtectedRoute>
        }
      />

      {/* Fallback */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default AppRoutes;
