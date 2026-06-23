import React, { useEffect, useState } from 'react';
import { dashboardService } from '../../services/dashboardService';
import { User } from '../../types';
import '../Dashboard.css';

const UserManagement: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      setLoading(true);
      const data = await dashboardService.getAllUsers();
      setUsers(data);
    } catch (err) {
      console.error('Failed to load users:', err);
    } finally {
      setLoading(false);
    }
  };

  const toggleUserStatus = async (userId: number) => {
    try {
      await dashboardService.toggleUserActive(userId);
      loadUsers();
    } catch (err: any) {
      alert(err.response?.data?.message || 'Failed to update user status');
    }
  };

  const filteredUsers = users.filter(
    (user) =>
      user.fullName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
      user.username.toLowerCase().includes(searchTerm.toLowerCase())
  );

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
            <h1>User Management</h1>
            <p>Manage all registered users</p>
          </div>
        </div>

        <div className="card mb-4">
          <input
            type="text"
            className="form-input"
            placeholder="Search users by name, email, or username..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        <div className="table-container">
          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Username</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Role</th>
                <th>Referral Code</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredUsers.map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.fullName}</td>
                  <td>{user.username}</td>
                  <td>{user.email}</td>
                  <td>{user.phone}</td>
                  <td>
                    <span className={`badge ${user.role === 'ADMIN' ? 'badge-primary' : 'badge-success'}`}>
                      {user.role}
                    </span>
                  </td>
                  <td className="text-gold">{user.referralCode}</td>
                  <td>
                    <span className={`badge ${user.isActive ? 'badge-success' : 'badge-error'}`}>
                      {user.isActive ? 'Active' : 'Inactive'}
                    </span>
                  </td>
                  <td>
                    <button
                      className={`btn ${user.isActive ? 'btn-error' : 'btn-success'}`}
                      onClick={() => toggleUserStatus(user.id)}
                      style={{ padding: '6px 16px', fontSize: '0.9rem' }}
                    >
                      {user.isActive ? 'Deactivate' : 'Activate'}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {filteredUsers.length === 0 && (
          <div className="no-data">
            <p>No users found</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default UserManagement;
