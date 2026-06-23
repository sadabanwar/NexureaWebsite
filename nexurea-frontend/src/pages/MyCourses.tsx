import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { courseService } from '../services/courseService';
import { CoursePackage } from '../types';
import './MyCourses.css';

const MyCourses: React.FC = () => {
  const [courses, setCourses] = useState<CoursePackage[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadCourses();
  }, []);

  const loadCourses = async () => {
    try {
      setLoading(true);
      const data = await courseService.getUserCourses();
      setCourses(data);
    } catch (err) {
      console.error('Failed to load courses:', err);
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
    <div className="my-courses-page">
      <div className="container">
        <div className="page-header">
          <h1>My Courses</h1>
          <p>Access your purchased courses</p>
        </div>

        {courses.length === 0 ? (
          <div className="no-courses-message card">
            <svg className="empty-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <rect x="2" y="7" width="20" height="14" rx="2" ry="2" strokeWidth="2"/>
              <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16" strokeWidth="2"/>
            </svg>
            <h2>No courses yet</h2>
            <p>You haven't purchased any courses yet. Explore our course catalog to get started.</p>
            <Link to="/courses" className="btn btn-primary">
              Browse Courses
            </Link>
          </div>
        ) : (
          <div className="courses-grid">
            {courses.map((course) => (
              <div key={course.id} className="my-course-card">
                <div className="course-badge">Enrolled</div>
                <h3>{course.name}</h3>
                <p>{course.description}</p>
                <div className="course-info">
                  <div className="info-item">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                      <polygon points="23 7 16 12 23 17 23 7" strokeWidth="2"/>
                      <rect x="1" y="5" width="15" height="14" rx="2" ry="2" strokeWidth="2"/>
                    </svg>
                    <span>{course.videoCount || 0} Videos</span>
                  </div>
                  <div className="info-item">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                      <circle cx="12" cy="12" r="10" strokeWidth="2"/>
                      <polyline points="12 6 12 12 16 14" strokeWidth="2"/>
                    </svg>
                    <span>{course.duration || 'Self-paced'}</span>
                  </div>
                </div>
                <Link to={`/video/${course.id}`} className="btn btn-primary btn-block">
                  Watch Now
                </Link>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default MyCourses;
