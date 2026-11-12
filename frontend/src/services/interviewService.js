import api from './api';

export const interviewService = {
  scheduleInterview: async (data) => {
    const response = await api.post('/interviews', data);
    return response.data;
  },

  updateInterview: async (id, data) => {
    const response = await api.put(`/interviews/${id}`, data);
    return response.data;
  },

  updateStatus: async (id, status) => {
    const response = await api.patch(`/interviews/${id}/status`, null, {
      params: { status },
    });
    return response.data;
  },

  getMyInterviews: async () => {
    const response = await api.get('/interviews/my');
    return response.data;
  },

  getInterviewById: async (id) => {
    const response = await api.get(`/interviews/${id}`);
    return response.data;
  },
};

export default interviewService;
