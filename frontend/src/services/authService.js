import api from './api';

const normalizeUser = (user, token) => {
  if (!user) return null;
  const rawRole = user.role || 'ROLE_CANDIDATE';
  const role = typeof rawRole === 'string' ? rawRole.replace('ROLE_', '') : 'CANDIDATE';
  const fullName = user.fullName || `${user.firstName || ''} ${user.lastName || ''}`.trim() || user.email;
  const nameParts = fullName.split(' ');
  const firstName = user.firstName || nameParts[0] || 'User';
  const lastName = user.lastName || (nameParts.length > 1 ? nameParts.slice(1).join(' ') : '');

  return {
    ...user,
    token: token || user.token,
    role,
    rawRole,
    fullName,
    firstName,
    lastName,
  };
};

export const authService = {
  login: async (email, password) => {
    const response = await api.post('/auth/login', { email, password });
    const payload = response.data?.data || response.data;
    const token = payload?.token || payload?.accessToken;
    const rawUser = payload?.user || payload;

    const user = normalizeUser(rawUser, token);

    if (token) {
      localStorage.setItem('jobtrack_token', token);
    }
    if (user) {
      localStorage.setItem('jobtrack_user', JSON.stringify(user));
    }
    return user;
  },

  registerCandidate: async (data) => {
    const response = await api.post('/auth/register/candidate', data);
    const payload = response.data?.data || response.data;
    const token = payload?.token || payload?.accessToken;
    const rawUser = payload?.user || payload;

    const user = normalizeUser(rawUser, token);

    if (token) {
      localStorage.setItem('jobtrack_token', token);
    }
    if (user) {
      localStorage.setItem('jobtrack_user', JSON.stringify(user));
    }
    return user;
  },

  registerRecruiter: async (data) => {
    const response = await api.post('/auth/register/recruiter', data);
    const payload = response.data?.data || response.data;
    const token = payload?.token || payload?.accessToken;
    const rawUser = payload?.user || payload;

    const user = normalizeUser(rawUser, token);

    if (token) {
      localStorage.setItem('jobtrack_token', token);
    }
    if (user) {
      localStorage.setItem('jobtrack_user', JSON.stringify(user));
    }
    return user;
  },

  getCurrentUser: async () => {
    const response = await api.get('/auth/me');
    const payload = response.data?.data || response.data;
    const currentToken = localStorage.getItem('jobtrack_token');

    if (payload && payload.email) {
      const user = normalizeUser(payload, currentToken);
      localStorage.setItem('jobtrack_user', JSON.stringify(user));
      return user;
    }
    return payload;
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

