import React, { useEffect, useState } from 'react';
import { withdrawalService } from '../../services/withdrawalService';
import { Withdrawal } from '../../types';
import '../Dashboard.css';

const WithdrawalApproval: React.FC = () => {
  const [withdrawals, setWithdrawals] = useState<Withdrawal[]>([]);
  const [loading, setLoading] = useState(true);
  const [processing, setProcessing] = useState<number | null>(null);
  const [transactionId, setTransactionId] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [selectedWithdrawal, setSelectedWithdrawal] = useState<Withdrawal | null>(null);

  useEffect(() => {
    loadWithdrawals();
  }, []);

  const loadWithdrawals = async () => {
    try {
      setLoading(true);
      const data = await withdrawalService.getAllWithdrawals();
      setWithdrawals(data);
    } catch (err) {
      console.error('Failed to load withdrawals:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleApprove = async () => {
    if (!selectedWithdrawal || !transactionId.trim()) {
      alert('Please enter transaction ID');
      return;
    }

    try {
      setProcessing(selectedWithdrawal.id);
      await withdrawalService.approveWithdrawal(selectedWithdrawal.id, transactionId);
      alert('Withdrawal approved successfully!');
      setShowModal(false);
      setTransactionId('');
      setSelectedWithdrawal(null);
      loadWithdrawals();
    } catch (err: any) {
      alert(err.response?.data?.message || 'Failed to approve withdrawal');
    } finally {
      setProcessing(null);
    }
  };

  const handleReject = async (withdrawalId: number) => {
    if (!window.confirm('Are you sure you want to reject this withdrawal?')) {
      return;
    }

    try {
      setProcessing(withdrawalId);
      await withdrawalService.rejectWithdrawal(withdrawalId);
      alert('Withdrawal rejected');
      loadWithdrawals();
    } catch (err: any) {
      alert(err.response?.data?.message || 'Failed to reject withdrawal');
    } finally {
      setProcessing(null);
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
    <div className="dashboard-page">
      <div className="container">
        <div className="page-header">
          <div>
            <h1>Withdrawal Approval</h1>
            <p>Review and process withdrawal requests</p>
          </div>
        </div>

        <div className="table-container">
          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>User</th>
                <th>Amount</th>
                <th>Account Details</th>
                <th>Request Date</th>
                <th>Status</th>
                <th>Transaction ID</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {withdrawals.map((withdrawal) => (
                <tr key={withdrawal.id}>
                  <td>{withdrawal.id}</td>
                  <td>{withdrawal.user?.fullName || 'N/A'}</td>
                  <td className="text-gold">₹{withdrawal.amount.toLocaleString()}</td>
                  <td>
                    <div style={{ fontSize: '0.9rem' }}>
                      <div><strong>{withdrawal.accountHolderName}</strong></div>
                      <div>{withdrawal.accountNumber}</div>
                      <div>IFSC: {withdrawal.ifscCode}</div>
                      {withdrawal.upiId && <div>UPI: {withdrawal.upiId}</div>}
                    </div>
                  </td>
                  <td>{new Date(withdrawal.requestDate).toLocaleDateString()}</td>
                  <td>
                    <span className={`badge ${getStatusBadge(withdrawal.status)}`}>
                      {withdrawal.status}
                    </span>
                  </td>
                  <td>{withdrawal.transactionId || '-'}</td>
                  <td>
                    {withdrawal.status === 'PENDING' && (
                      <div style={{ display: 'flex', gap: '8px' }}>
                        <button
                          className="btn btn-success"
                          onClick={() => {
                            setSelectedWithdrawal(withdrawal);
                            setShowModal(true);
                          }}
                          disabled={processing === withdrawal.id}
                          style={{ padding: '6px 16px', fontSize: '0.9rem' }}
                        >
                          Approve
                        </button>
                        <button
                          className="btn btn-error"
                          onClick={() => handleReject(withdrawal.id)}
                          disabled={processing === withdrawal.id}
                          style={{ padding: '6px 16px', fontSize: '0.9rem' }}
                        >
                          Reject
                        </button>
                      </div>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {withdrawals.length === 0 && (
          <div className="no-data">
            <p>No withdrawal requests</p>
          </div>
        )}

        {showModal && selectedWithdrawal && (
          <div
            style={{
              position: 'fixed',
              top: 0,
              left: 0,
              right: 0,
              bottom: 0,
              background: 'rgba(0,0,0,0.5)',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              zIndex: 1000,
            }}
            onClick={() => setShowModal(false)}
          >
            <div
              className="card"
              style={{ maxWidth: '500px', width: '90%', padding: '32px' }}
              onClick={(e) => e.stopPropagation()}
            >
              <h2 style={{ marginBottom: '24px', color: 'var(--primary-navy)' }}>
                Approve Withdrawal
              </h2>
              <p style={{ marginBottom: '16px', color: 'var(--text-light)' }}>
                Amount: <strong>₹{selectedWithdrawal.amount.toLocaleString()}</strong>
              </p>
              <p style={{ marginBottom: '24px', color: 'var(--text-light)' }}>
                User: <strong>{selectedWithdrawal.user?.fullName}</strong>
              </p>
              <div className="form-group">
                <label className="form-label">Transaction ID *</label>
                <input
                  type="text"
                  className="form-input"
                  value={transactionId}
                  onChange={(e) => setTransactionId(e.target.value)}
                  placeholder="Enter transaction ID"
                  required
                />
              </div>
              <div style={{ display: 'flex', gap: '12px' }}>
                <button
                  className="btn btn-success"
                  onClick={handleApprove}
                  disabled={!transactionId.trim() || processing === selectedWithdrawal.id}
                >
                  {processing === selectedWithdrawal.id ? 'Processing...' : 'Approve'}
                </button>
                <button
                  className="btn btn-secondary"
                  onClick={() => {
                    setShowModal(false);
                    setTransactionId('');
                    setSelectedWithdrawal(null);
                  }}
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default WithdrawalApproval;
