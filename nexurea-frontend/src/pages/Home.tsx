import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

const Home: React.FC = () => {
  return (
    <div className="home">
      {/* Hero Section */}
      <section className="hero">
        <div className="hero-background"></div>
        <div className="container">
          <div className="hero-content">
            <h1 className="hero-title fade-in">
              Master Entrepreneurship
              <br />
              <span className="hero-highlight">Earn While You Learn</span>
            </h1>
            <p className="hero-subtitle fade-in">
              Join Nexurea Entrepreneur Digital University and unlock your potential
              with expert-led courses. Start earning generous commissions by sharing
              knowledge with your network.
            </p>
            <div className="hero-buttons fade-in">
              <Link to="/courses" className="btn btn-primary btn-large">
                Explore Courses
              </Link>
              <Link to="/register" className="btn btn-outline btn-large">
                Join Now
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="features">
        <div className="container">
          <h2 className="section-title text-center">Why Choose Nexurea?</h2>
          <div className="features-grid">
            <div className="feature-card">
              <div className="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <path d="M12 2L2 7l10 5 10-5-10-5z" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                  <path d="M2 17l10 5 10-5M2 12l10 5 10-5" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
              </div>
              <h3>Expert-Led Content</h3>
              <p>Learn from industry leaders and successful entrepreneurs who share real-world insights and proven strategies.</p>
            </div>

            <div className="feature-card">
              <div className="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                  <polyline points="22 4 12 14.01 9 11.01" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
              </div>
              <h3>Flexible Learning</h3>
              <p>Access courses anytime, anywhere. Learn at your own pace with lifetime access to all course materials.</p>
            </div>

            <div className="feature-card">
              <div className="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <line x1="12" y1="1" x2="12" y2="23" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                  <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
              </div>
              <h3>Earn Commissions</h3>
              <p>Share courses with your network and earn up to 50% commission on every sale. Build passive income while helping others.</p>
            </div>

            <div className="feature-card">
              <div className="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                  <circle cx="9" cy="7" r="4" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                  <path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
              </div>
              <h3>Growing Community</h3>
              <p>Join thousands of entrepreneurs building their future. Network, collaborate, and grow together.</p>
            </div>
          </div>
        </div>
      </section>

      {/* Course Preview Section */}
      <section className="course-preview">
        <div className="container">
          <h2 className="section-title text-center">Our Course Packages</h2>
          <p className="section-subtitle text-center">
            Choose the perfect package for your entrepreneurial journey
          </p>
          <div className="preview-grid">
            <div className="preview-card">
              <div className="preview-price">₹499</div>
              <h3>Starter Package</h3>
              <p>Perfect for beginners starting their entrepreneurial journey</p>
              <div className="preview-commission">30% Commission</div>
            </div>

            <div className="preview-card preview-featured">
              <div className="preview-badge">Most Popular</div>
              <div className="preview-price">₹999</div>
              <h3>Growth Package</h3>
              <p>Comprehensive courses for scaling your business</p>
              <div className="preview-commission">40% Commission</div>
            </div>

            <div className="preview-card">
              <div className="preview-price">₹1499</div>
              <h3>Pro Package</h3>
              <p>Advanced strategies for established entrepreneurs</p>
              <div className="preview-commission">45% Commission</div>
            </div>

            <div className="preview-card">
              <div className="preview-price">₹1999</div>
              <h3>Elite Package</h3>
              <p>Premium content with exclusive mentorship access</p>
              <div className="preview-commission">50% Commission</div>
            </div>
          </div>
          <div className="text-center mt-5">
            <Link to="/courses" className="btn btn-primary btn-large">
              View All Courses
            </Link>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="cta">
        <div className="container">
          <div className="cta-content">
            <h2 className="cta-title">Ready to Start Your Journey?</h2>
            <p className="cta-subtitle">
              Join thousands of entrepreneurs who are building their success with Nexurea
            </p>
            <Link to="/register" className="btn btn-primary btn-large">
              Get Started Today
            </Link>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Home;
