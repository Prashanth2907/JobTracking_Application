import React, { createContext, useContext, useState, useEffect } from 'react';
import authService from '../services/authService';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => authService.getStoredUser());
  const [token, setToken] = useState(() => authService.getStoredToken());
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const initAuth = async () => {
      const storedToken = authService.getStoredToken();
      if (storedToken) {
        try {
          const freshUser = await authService.getCurrentUser();
          setUser(freshUser);
        } catch (err) {
          // Token is likely invalid or expired
          authService.logout();
          setUser(null);
          setToken(null);
        }
      }
      setLoading(false);
    };

    initAuth();
  }, []);

  const login = async (email, password) => {
    const data = await authService.login(email, password);
    setToken(data.token);
    setUser(data);
    return data;
  };

  const registerCandidate = async (regData) => {
    const data = await authService.registerCandidate(regData);
    setToken(data.token);
    setUser(data);
    return data;
  };

  const registerRecruiter = async (regData) => {
    const data = await authService.registerRecruiter(regData);
    setToken(data.token);
    setUser(data);
    return data;
  };

  const logout = () => {
    authService.logout();
    setToken(null);
    setUser(null);
  };

  const refreshUser = async () => {
    try {
      const freshUser = await authService.getCurrentUser();
      setUser(freshUser);
      return freshUser;
    } catch {
      return null;
    }
  };

  const userRole = user?.role
    ? typeof user.role === 'string'
      ? user.role.replace('ROLE_', '')
      : user.role
    : null;

  const value = {
    user,
    token,
    role: userRole,
    isAuthenticated: !!token && !!user,
    loading,
    login,
    registerCandidate,
    registerRecruiter,
    logout,
    refreshUser,
  };


  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export default AuthContext;
