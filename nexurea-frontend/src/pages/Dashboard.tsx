import React, { useEffect, useState } from 'react';
import DashboardCard from '../components/DashboardCard';
import { dashboardService } from '../services/dashboardService';
import { DashboardData } from '../types';
import './Dashboard.css';

const Dashboard: React.FC = () => {
  const [data, setData] = useState<DashboardData | null>(null);
  const [referralLink, setReferralLink] = useState('');
  const [loading, setLoading] = useState(true);
  const [copied, setCopied] = useState(false);

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    try {
      setLoading(true);
      const [dashboardData, referralCode] = await Promise.all([
        dashboardService.getDashboard(),
        dashboardService.getReferralCode(),
      ]);
      setData(dashboardData);
      setReferralLink(`${window.location.origin}/register?ref=${referralCode}`);
    } catch (err) {
      console.error('Failed to load dashboard:', err);
    } finally {
      setLoading(false);
    }
  };

  const copyReferralLink = () => {
    navigator.clipboard.writeText(referralLink);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
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
        <div className="dashboard-header">
          <h1>Affiliate Dashboard</h1>
          <p>Track your performance and earnings</p>
        </div>

        <div className="dashboard-stats">
          <DashboardCard
            title="Total Sales"
            value={`₹${data?.totalSales.toLocaleString() || 0}`}
            color="primary"
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <line x1="12" y1="1" x2="12" y2="23" strokeWidth="2"/>
                <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6" strokeWidth="2"/>
              </svg>
            }
          />
          <DashboardCard
            title="Total Commission"
            value={`₹${data?.totalCommission.toLocaleString() || 0}`}
            color="gold"
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6" strokeWidth="2"/>
              </svg>
            }
          />
          <DashboardCard
            title="Referrals"
            value={data?.referralCount || 0}
            color="success"
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" strokeWidth="2"/>
                <circle cx="9" cy="7" r="4" strokeWidth="2"/>
                <path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75" strokeWidth="2"/>
              </svg>
            }
          />
          <DashboardCard
            title="Available Balance"
            value={`₹${data?.availableBalance.toLocaleString() || 0}`}
            color="warning"
            subtitle={`Withdrawn: ₹${data?.withdrawnAmount.toLocaleString() || 0}`}
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <rect x="1" y="4" width="22" height="16" rx="2" ry="2" strokeWidth="2"/>
                <line x1="1" y1="10" x2="23" y2="10" strokeWidth="2"/>
              </svg>
            }
          />
        </div>

        <div className="referral-section card">
          <h2>Your Referral Link</h2>
          <p className="text-light">Share this link to earn commissions</p>
          <div className="referral-link-container">
            <input
              type="text"
              className="form-input"
              value={referralLink}
              readOnly
            />
            <button onClick={copyReferralLink} className="btn btn-primary">
              {copied ? 'Copied!' : 'Copy'}
            </button>
          </div>
        </div>

        <div className="commissions-section">
          <h2>Recent Commissions</h2>
          {data?.recentCommissions && data.recentCommissions.length > 0 ? (
            <div className="table-container">
              <table className="table">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Referred User</th>
                    <th>Level</th>
                    <th>Amount</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {data.recentCommissions.map((commission) => (
                    <tr key={commission.id}>
                      <td>{new Date(commission.earnedDate).toLocaleDateString()}</td>
                      <td>{commission.referredUser?.fullName || 'N/A'}</td>
                      <td>Level {commission.level}</td>
                      <td className="text-gold">₹{commission.amount.toLocaleString()}</td>
                      <td>
                        <span className={`badge badge-${commission.status === 'PAID' ? 'success' : 'warning'}`}>
                          {commission.status}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <div className="no-data">
              <p>No commissions yet. Start sharing your referral link!</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
