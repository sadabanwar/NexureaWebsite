import React, { useEffect, useState } from 'react';
import CourseCard from '../components/CourseCard';
import { courseService } from '../services/courseService';
import { CoursePackage } from '../types';
import './Courses.css';

const Courses: React.FC = () => {
  const [courses, setCourses] = useState<CoursePackage[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadCourses();
  }, []);

  const loadCourses = async () => {
    try {
      setLoading(true);
      const data = await courseService.getPackages();
      setCourses(data);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to load courses');
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

  if (error) {
    return (
      <div className="container mt-5">
        <div className="error-message">{error}</div>
      </div>
    );
  }

  return (
    <div className="courses-page">
      <section className="courses-hero">
        <div className="container">
          <h1 className="page-title">Our Course Packages</h1>
          <p className="page-subtitle">
            Choose the perfect package for your entrepreneurial journey and start earning
            generous commissions
          </p>
        </div>
      </section>

      <section className="courses-content">
        <div className="container">
          {courses.length === 0 ? (
            <div className="no-courses">
              <h3>No courses available at the moment</h3>
              <p>Please check back later for new course offerings.</p>
            </div>
          ) : (
            <div className="courses-grid">
              {courses.map((course) => (
                <CourseCard key={course.id} course={course} />
              ))}
            </div>
          )}

          <div className="courses-benefits">
            <h2 className="text-center mb-5">What You Get With Every Package</h2>
            <div className="benefits-grid">
              <div className="benefit-item">
                <svg className="benefit-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <polyline points="20 6 9 17 4 12" strokeWidth="2" strokeLinecap="round"/>
                </svg>
                <h3>Lifetime Access</h3>
                <p>Access all course materials anytime, anywhere, forever</p>
              </div>
              <div className="benefit-item">
                <svg className="benefit-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <polyline points="20 6 9 17 4 12" strokeWidth="2" strokeLinecap="round"/>
                </svg>
                <h3>Certificate</h3>
                <p>Receive a certificate of completion to showcase your skills</p>
              </div>
              <div className="benefit-item">
                <svg className="benefit-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <polyline points="20 6 9 17 4 12" strokeWidth="2" strokeLinecap="round"/>
                </svg>
                <h3>Expert Support</h3>
                <p>Get help from our team of experienced entrepreneurs</p>
              </div>
              <div className="benefit-item">
                <svg className="benefit-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <polyline points="20 6 9 17 4 12" strokeWidth="2" strokeLinecap="round"/>
                </svg>
                <h3>Affiliate Earnings</h3>
                <p>Share your referral link and earn commissions on every sale</p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Courses;
