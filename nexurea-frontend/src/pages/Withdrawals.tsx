import React, { useEffect, useState } from 'react';
import { withdrawalService, WithdrawalRequest } from '../services/withdrawalService';
import { Withdrawal } from '../types';
import './Withdrawals.css';

const Withdrawals: React.FC = () => {
  const [withdrawals, setWithdrawals] = useState<Withdrawal[]>([]);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState<WithdrawalRequest>({
    amount: 0,
    accountHolderName: '',
    accountNumber: '',
    ifscCode: '',
    upiId: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    loadWithdrawals();
  }, []);

  const loadWithdrawals = async () => {
    try {
      setLoading(true);
      const data = await withdrawalService.getMyWithdrawals();
      setWithdrawals(data);
    } catch (err) {
      console.error('Failed to load withdrawals:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    if (formData.amount < 500) {
      setError('Minimum withdrawal amount is ₹500');
      return;
    }

    try {
      setSubmitting(true);
      await withdrawalService.requestWithdrawal(formData);
      setSuccess('Withdrawal request submitted successfully!');
      setShowForm(false);
      setFormData({
        amount: 0,
        accountHolderName: '',
        accountNumber: '',
        ifscCode: '',
        upiId: '',
      });
      loadWithdrawals();
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to submit withdrawal request');
    } finally {
      setSubmitting(false);
    }
  };

  const getStatusBadge = (status: string) => {
    const statusMap = {
      PENDING: 'badge-warning',
      APPROVED: 'badge-success',
      REJECTED: 'badge-error',
    };
    return statusMap[status as keyof typeof statusMap] || 'badge-primary';
  };

  if (loading) {
    return (
      <div className="loading-container">
        <div className="spinner"></div>
      </div>
    );
  }

  return (
    <div className="withdrawals-page">
      <div className="container">
        <div className="page-header">
          <div>
            <h1>Withdrawal Requests</h1>
            <p>Manage your commission withdrawals</p>
          </div>
          <button
            className="btn btn-primary"
            onClick={() => setShowForm(!showForm)}
          >
            {showForm ? 'Cancel' : 'New Withdrawal'}
          </button>
        </div>

        {error && <div className="alert alert-error">{error}</div>}
        {success && <div className="alert alert-success">{success}</div>}

        {showForm && (
          <div className="withdrawal-form-section card">
            <h2>Request Withdrawal</h2>
            <p className="text-light">Minimum withdrawal amount is ₹500</p>
            <form onSubmit={handleSubmit} className="withdrawal-form">
              <div className="form-row">
                <div className="form-group">
                  <label className="form-label">Amount *</label>
                  <input
                    type="number"
                    name="amount"
                    className="form-input"
                    value={formData.amount || ''}
                    onChange={handleChange}
                    required
                    min="500"
                    step="1"
                  />
                </div>
                <div className="form-group">
                  <label className="form-label">Account Holder Name *</label>
                  <input
                    type="text"
                    name="accountHolderName"
                    className="form-input"
                    value={formData.accountHolderName}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>

              <div className="form-row">
                <div className="form-group">
                  <label className="form-label">Account Number *</label>
                  <input
                    type="text"
                    name="accountNumber"
                    className="form-input"
                    value={formData.accountNumber}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group">
                  <label className="form-label">IFSC Code *</label>
                  <input
                    type="text"
                    name="ifscCode"
                    className="form-input"
                    value={formData.ifscCode}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>

              <div className="form-group">
                <label className="form-label">UPI ID (Optional)</label>
                <input
                  type="text"
                  name="upiId"
                  className="form-input"
                  value={formData.upiId}
                  onChange={handleChange}
                  placeholder="example@upi"
                />
              </div>

              <button
                type="submit"
                className="btn btn-primary"
                disabled={submitting}
              >
                {submitting ? 'Submitting...' : 'Submit Request'}
              </button>
            </form>
          </div>
        )}

        <div className="withdrawals-list">
          <h2>Withdrawal History</h2>
          {withdrawals.length === 0 ? (
            <div className="no-data">
              <p>No withdrawal requests yet</p>
            </div>
          ) : (
            <div className="table-container">
              <table className="table">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Amount</th>
                    <th>Account Details</th>
                    <th>Status</th>
                    <th>Transaction ID</th>
                    <th>Processed Date</th>
                  </tr>
                </thead>
                <tbody>
                  {withdrawals.map((withdrawal) => (
                    <tr key={withdrawal.id}>
                      <td>{new Date(withdrawal.requestDate).toLocaleDateString()}</td>
                      <td className="text-gold">₹{withdrawal.amount.toLocaleString()}</td>
                      <td>
                        <div className="account-details">
                          <div>{withdrawal.accountHolderName}</div>
                          <div className="text-small">{withdrawal.accountNumber}</div>
                          {withdrawal.upiId && (
                            <div className="text-small">{withdrawal.upiId}</div>
                          )}
                        </div>
                      </td>
                      <td>
                        <span className={`badge ${getStatusBadge(withdrawal.status)}`}>
                          {withdrawal.status}
                        </span>
                      </td>
                      <td>{withdrawal.transactionId || '-'}</td>
                      <td>
                        {withdrawal.processedDate
                          ? new Date(withdrawal.processedDate).toLocaleDateString()
                          : '-'}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Withdrawals;
