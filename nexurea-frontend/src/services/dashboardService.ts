import api from './api';
import { DashboardData, Commission, AdminDashboardData, User } from '../types';

export const dashboardService = {
  getDashboard: async (): Promise<DashboardData> => {
    const response = await api.get<DashboardData>('/dashboard');
    return response.data;
  },

  getCommissions: async (): Promise<Commission[]> => {
    const response = await api.get<Commission[]>('/dashboard/commissions');
    return response.data;
  },

  getReferralCode: async (): Promise<string> => {
    const response = await api.get<{ referralCode: string }>('/dashboard/referral-code');
    return response.data.referralCode;
  },

  // Admin functions
  getAdminDashboard: async (): Promise<AdminDashboardData> => {
    const response = await api.get<AdminDashboardData>('/admin/dashboard');
    return response.data;
  },

  getAllUsers: async (): Promise<User[]> => {
    const response = await api.get<User[]>('/admin/users');
    return response.data;
  },

  toggleUserActive: async (userId: number): Promise<User> => {
    const response = await api.put<User>(`/admin/users/${userId}/toggle-active`);
    return response.data;
  },
};
