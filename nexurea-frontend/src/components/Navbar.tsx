import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Navbar.css';

const Navbar: React.FC = () => {
  const { user, isAuthenticated, isAdmin, logout } = useAuth();
  const navigate = useNavigate();
  const [menuOpen, setMenuOpen] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  return (
    <nav className="navbar">
      <div className="container">
        <div className="navbar-content">
          <Link to="/" className="navbar-logo">
            <span className="logo-text">Nexurea</span>
            <span className="logo-subtitle">University</span>
          </Link>

          <button className="navbar-toggle" onClick={toggleMenu}>
            <span></span>
            <span></span>
            <span></span>
          </button>

          <div className={`navbar-menu ${menuOpen ? 'active' : ''}`}>
            <Link to="/" className="nav-link" onClick={() => setMenuOpen(false)}>
              Home
            </Link>
            <Link to="/about" className="nav-link" onClick={() => setMenuOpen(false)}>
              About
            </Link>
            <Link to="/courses" className="nav-link" onClick={() => setMenuOpen(false)}>
              Courses
            </Link>

            {isAuthenticated ? (
              <>
                <Link to="/dashboard" className="nav-link" onClick={() => setMenuOpen(false)}>
                  Dashboard
                </Link>
                <Link to="/my-courses" className="nav-link" onClick={() => setMenuOpen(false)}>
                  My Courses
                </Link>
                <Link to="/withdrawals" className="nav-link" onClick={() => setMenuOpen(false)}>
                  Withdrawals
                </Link>
                {isAdmin && (
                  <Link to="/admin" className="nav-link nav-link-admin" onClick={() => setMenuOpen(false)}>
                    Admin
                  </Link>
                )}
                <div className="navbar-user">
                  <span className="user-name">{user?.fullName}</span>
                  <button onClick={handleLogout} className="btn btn-logout">
                    Logout
                  </button>
                </div>
              </>
            ) : (
              <div className="navbar-auth">
                <Link
                  to="/login"
                  className="btn btn-outline"
                  onClick={() => setMenuOpen(false)}
                >
                  Login
                </Link>
                <Link
                  to="/register"
                  className="btn btn-primary"
                  onClick={() => setMenuOpen(false)}
                >
                  Sign Up
                </Link>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
