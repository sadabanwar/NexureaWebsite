import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import DashboardCard from '../../components/DashboardCard';
import { dashboardService } from '../../services/dashboardService';
import { AdminDashboardData } from '../../types';
import './AdminDashboard.css';

const AdminDashboard: React.FC = () => {
  const [data, setData] = useState<AdminDashboardData | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    try {
      setLoading(true);
      const dashboardData = await dashboardService.getAdminDashboard();
      setData(dashboardData);
    } catch (err) {
      console.error('Failed to load admin dashboard:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="loading-container">
        <div className="spinner"></div>
      </div>
    );
  }

  return (
    <div className="admin-dashboard">
      <div className="container">
        <div className="admin-header">
          <h1>Admin Dashboard</h1>
          <p>Manage your platform</p>
        </div>

        <div className="admin-stats">
          <DashboardCard
            title="Total Users"
            value={data?.totalUsers || 0}
            color="primary"
            subtitle={`Active: ${data?.activeUsers || 0}`}
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" strokeWidth="2"/>
                <circle cx="9" cy="7" r="4" strokeWidth="2"/>
                <path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75" strokeWidth="2"/>
              </svg>
            }
          />
          <DashboardCard
            title="Total Sales"
            value={data?.totalSales || 0}
            color="success"
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <circle cx="9" cy="21" r="1" strokeWidth="2"/>
                <circle cx="20" cy="21" r="1" strokeWidth="2"/>
                <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6" strokeWidth="2"/>
              </svg>
            }
          />
          <DashboardCard
            title="Total Revenue"
            value={`₹${data?.totalRevenue.toLocaleString() || 0}`}
            color="gold"
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <line x1="12" y1="1" x2="12" y2="23" strokeWidth="2"/>
                <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6" strokeWidth="2"/>
              </svg>
            }
          />
          <DashboardCard
            title="Pending Withdrawals"
            value={data?.pendingWithdrawals || 0}
            color="warning"
            icon={
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <circle cx="12" cy="12" r="10" strokeWidth="2"/>
                <polyline points="12 6 12 12 16 14" strokeWidth="2"/>
              </svg>
            }
          />
        </div>

        <div className="admin-quick-links">
          <Link to="/admin/users" className="quick-link-card">
            <div className="quick-link-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" strokeWidth="2"/>
                <circle cx="9" cy="7" r="4" strokeWidth="2"/>
                <path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75" strokeWidth="2"/>
              </svg>
            </div>
            <h3>User Management</h3>
            <p>Manage users and their access</p>
          </Link>

          <Link to="/admin/courses" className="quick-link-card">
            <div className="quick-link-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z" strokeWidth="2"/>
                <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z" strokeWidth="2"/>
              </svg>
            </div>
            <h3>Course Management</h3>
            <p>Create and manage courses</p>
          </Link>

          <Link to="/admin/withdrawals" className="quick-link-card">
            <div className="quick-link-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <rect x="1" y="4" width="22" height="16" rx="2" ry="2" strokeWidth="2"/>
                <line x1="1" y1="10" x2="23" y2="10" strokeWidth="2"/>
              </svg>
            </div>
            <h3>Withdrawal Approval</h3>
            <p>Process withdrawal requests</p>
          </Link>
        </div>

        {data?.revenueByMonth && data.revenueByMonth.length > 0 && (
          <div className="revenue-chart card">
            <h2>Revenue by Month</h2>
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={data.revenueByMonth}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis />
                <Tooltip />
                <Line type="monotone" dataKey="revenue" stroke="#ffd700" strokeWidth={2} />
              </LineChart>
            </ResponsiveContainer>
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminDashboard;
