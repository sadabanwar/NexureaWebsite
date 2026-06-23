import React from 'react';
import { Link } from 'react-router-dom';
import './Footer.css';

const Footer: React.FC = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="footer">
      <div className="container">
        <div className="footer-content">
          <div className="footer-section">
            <h3 className="footer-title">Nexurea University</h3>
            <p className="footer-description">
              Empowering entrepreneurs with world-class digital education and affiliate opportunities.
            </p>
          </div>

          <div className="footer-section">
            <h4 className="footer-heading">Quick Links</h4>
            <div className="footer-links">
              <Link to="/" className="footer-link">Home</Link>
              <Link to="/about" className="footer-link">About Us</Link>
              <Link to="/courses" className="footer-link">Courses</Link>
              <Link to="/dashboard" className="footer-link">Dashboard</Link>
            </div>
          </div>

          <div className="footer-section">
            <h4 className="footer-heading">Support</h4>
            <div className="footer-links">
              <Link to="/contact" className="footer-link">Contact Us</Link>
              <Link to="/privacy" className="footer-link">Privacy Policy</Link>
              <Link to="/terms" className="footer-link">Terms of Service</Link>
              <Link to="/faq" className="footer-link">FAQ</Link>
            </div>
          </div>

          <div className="footer-section">
            <h4 className="footer-heading">Connect With Us</h4>
            <div className="footer-social">
              <a href="https://facebook.com" target="_blank" rel="noopener noreferrer" className="social-link">
                Facebook
              </a>
              <a href="https://twitter.com" target="_blank" rel="noopener noreferrer" className="social-link">
                Twitter
              </a>
              <a href="https://linkedin.com" target="_blank" rel="noopener noreferrer" className="social-link">
                LinkedIn
              </a>
              <a href="https://instagram.com" target="_blank" rel="noopener noreferrer" className="social-link">
                Instagram
              </a>
            </div>
          </div>
        </div>

        <div className="footer-bottom">
          <p>&copy; {currentYear} Nexurea Entrepreneur Digital University. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
