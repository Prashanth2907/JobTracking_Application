import api from './api';

export const authService = {
  login: async (email, password) => {
    const response = await api.post('/auth/login', { email, password });
    if (response.data && response.data.token) {
      localStorage.setItem('jobtrack_token', response.data.token);
      localStorage.setItem('jobtrack_user', JSON.stringify(response.data));
    }
    return response.data;
  },

  registerCandidate: async (data) => {
    const response = await api.post('/auth/register/candidate', data);
    if (response.data && response.data.token) {
      localStorage.setItem('jobtrack_token', response.data.token);
      localStorage.setItem('jobtrack_user', JSON.stringify(response.data));
    }
    return response.data;
  },

  registerRecruiter: async (data) => {
    const response = await api.post('/auth/register/recruiter', data);
    if (response.data && response.data.token) {
      localStorage.setItem('jobtrack_token', response.data.token);
      localStorage.setItem('jobtrack_user', JSON.stringify(response.data));
    }
    return response.data;
  },

  getCurrentUser: async () => {
    const response = await api.get('/auth/me');
    if (response.data) {
      const stored = JSON.parse(localStorage.getItem('jobtrack_user') || '{}');
      const updated = { ...stored, ...response.data };
      localStorage.setItem('jobtrack_user', JSON.stringify(updated));
      return updated;
    }
    return response.data;
  },

  logout: () => {
    localStorage.removeItem('jobtrack_token');
    localStorage.removeItem('jobtrack_user');
  },

  getStoredUser: () => {
    const u = localStorage.getItem('jobtrack_user');
    return u ? JSON.parse(u) : null;
  },

  getStoredToken: () => {
    return localStorage.getItem('jobtrack_token');
  },
};

export default authService;
