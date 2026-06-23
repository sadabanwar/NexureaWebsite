import api from './api';
import { Withdrawal } from '../types';

export interface WithdrawalRequest {
  amount: number;
  accountHolderName: string;
  accountNumber: string;
  ifscCode: string;
  upiId?: string;
}

export const withdrawalService = {
  requestWithdrawal: async (data: WithdrawalRequest): Promise<Withdrawal> => {
    const response = await api.post<Withdrawal>('/withdrawals/request', data);
    return response.data;
  },

  getMyWithdrawals: async (): Promise<Withdrawal[]> => {
    const response = await api.get<Withdrawal[]>('/withdrawals/my-withdrawals');
    return response.data;
  },

  // Admin functions
  getPendingWithdrawals: async (): Promise<Withdrawal[]> => {
    const response = await api.get<Withdrawal[]>('/admin/withdrawals/pending');
    return response.data;
  },

  getAllWithdrawals: async (): Promise<Withdrawal[]> => {
    const response = await api.get<Withdrawal[]>('/admin/withdrawals');
    return response.data;
  },

  approveWithdrawal: async (withdrawalId: number, transactionId: string): Promise<Withdrawal> => {
    const response = await api.put<Withdrawal>(`/admin/withdrawals/${withdrawalId}/approve`, {
      transactionId,
    });
    return response.data;
  },

  rejectWithdrawal: async (withdrawalId: number): Promise<Withdrawal> => {
    const response = await api.put<Withdrawal>(`/admin/withdrawals/${withdrawalId}/reject`);
    return response.data;
  },
};
