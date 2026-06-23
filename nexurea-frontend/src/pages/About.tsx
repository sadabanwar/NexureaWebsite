import React from 'react';
import './About.css';

const About: React.FC = () => {
  return (
    <div className="about-page">
      <section className="about-hero">
        <div className="container">
          <h1 className="page-title">About Nexurea University</h1>
          <p className="page-subtitle">
            Empowering entrepreneurs worldwide with premium digital education
          </p>
        </div>
      </section>

      <section className="about-content">
        <div className="container">
          <div className="content-grid">
            <div className="content-section">
              <h2>Our Mission</h2>
              <p>
                At Nexurea Entrepreneur Digital University, we believe that entrepreneurship
                is the key to unlocking human potential. Our mission is to provide world-class
                digital education that empowers individuals to build, grow, and scale successful
                businesses while creating opportunities for others.
              </p>
              <p>
                We combine expert-led courses with an innovative affiliate system that allows
                our students to earn while they learn, creating a sustainable ecosystem of
                knowledge sharing and financial growth.
              </p>
            </div>

            <div className="content-section">
              <h2>Our Vision</h2>
              <p>
                To become the world's leading platform for entrepreneurial education, where
                knowledge meets opportunity, and where every learner has the potential to
                become a successful entrepreneur and mentor to others.
              </p>
              <p>
                We envision a future where entrepreneurship education is accessible to
                everyone, and where sharing knowledge creates mutual prosperity for all
                involved.
              </p>
            </div>
          </div>

          <div className="values-section">
            <h2 className="text-center mb-5">Our Core Values</h2>
            <div className="values-grid">
              <div className="value-card">
                <div className="value-icon">🎯</div>
                <h3>Excellence</h3>
                <p>We deliver only the highest quality content from proven industry experts.</p>
              </div>
              <div className="value-card">
                <div className="value-icon">🤝</div>
                <h3>Community</h3>
                <p>We build a supportive network where entrepreneurs help each other succeed.</p>
              </div>
              <div className="value-card">
                <div className="value-icon">💡</div>
                <h3>Innovation</h3>
                <p>We constantly evolve our platform to meet the changing needs of entrepreneurs.</p>
              </div>
              <div className="value-card">
                <div className="value-icon">🌟</div>
                <h3>Integrity</h3>
                <p>We operate with transparency and fairness in all our dealings.</p>
              </div>
            </div>
          </div>

          <div className="stats-section">
            <div className="stats-grid">
              <div className="stat-item">
                <div className="stat-number">10,000+</div>
                <div className="stat-label">Active Students</div>
              </div>
              <div className="stat-item">
                <div className="stat-number">500+</div>
                <div className="stat-label">Course Hours</div>
              </div>
              <div className="stat-item">
                <div className="stat-number">₹50L+</div>
                <div className="stat-label">Commissions Paid</div>
              </div>
              <div className="stat-item">
                <div className="stat-number">4.9/5</div>
                <div className="stat-label">Average Rating</div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default About;
