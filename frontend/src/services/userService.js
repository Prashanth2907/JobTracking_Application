import api from './api';

export const userService = {
  getProfile: async () => {
    const response = await api.get('/users/profile');
    return response.data;
  },

  updateCandidateProfile: async (data) => {
    const response = await api.put('/users/candidate-profile', data);
    return response.data;
  },

  updateRecruiterProfile: async (data) => {
    const response = await api.put('/users/recruiter-profile', data);
    return response.data;
  },

  updateCompany: async (data) => {
    const response = await api.put('/users/company', data);
    return response.data;
  },

  changePassword: async (currentPassword, newPassword) => {
    const response = await api.post('/users/change-password', {
      currentPassword,
      newPassword,
    });
    return response.data;
  },
};

export default userService;
