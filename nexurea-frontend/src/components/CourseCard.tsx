import React from 'react';
import { useNavigate } from 'react-router-dom';
import { CoursePackage } from '../types';
import { useAuth } from '../context/AuthContext';
import { paymentService } from '../services/paymentService';
import './CourseCard.css';

interface CourseCardProps {
  course: CoursePackage;
}

const CourseCard: React.FC<CourseCardProps> = ({ course }) => {
  const navigate = useNavigate();
  const { isAuthenticated, user } = useAuth();
  const [loading, setLoading] = React.useState(false);

  const handleEnroll = async () => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }

    setLoading(true);
    await paymentService.handlePayment(
      course.id,
      undefined,
      () => {
        navigate('/my-courses');
      },
      (error) => {
        alert(error);
        setLoading(false);
      }
    );
  };

  const features = course.features || [
    'Lifetime Access',
    'Certificate of Completion',
    'Expert Support',
    'Premium Resources',
  ];

  return (
    <div className="course-card">
      <div className="course-card-header">
        <h3 className="course-title">{course.name}</h3>
        <div className="course-price">
          <span className="currency">₹</span>
          <span className="amount">{course.price}</span>
        </div>
      </div>

      <div className="course-commission">
        <span className="commission-label">Earn Commission:</span>
        <span className="commission-rate">{course.commissionRate}%</span>
      </div>

      <p className="course-description">{course.description}</p>

      <div className="course-meta">
        <div className="meta-item">
          <svg className="meta-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <circle cx="12" cy="12" r="10" strokeWidth="2"/>
            <polyline points="12 6 12 12 16 14" strokeWidth="2"/>
          </svg>
          <span>{course.duration || 'Self-paced'}</span>
        </div>
        <div className="meta-item">
          <svg className="meta-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <polygon points="23 7 16 12 23 17 23 7" strokeWidth="2"/>
            <rect x="1" y="5" width="15" height="14" rx="2" ry="2" strokeWidth="2"/>
          </svg>
          <span>{course.videoCount || 0} Videos</span>
        </div>
      </div>

      <div className="course-features">
        {features.map((feature, index) => (
          <div key={index} className="feature-item">
            <svg className="feature-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <polyline points="20 6 9 17 4 12" strokeWidth="2" strokeLinecap="round"/>
            </svg>
            <span>{feature}</span>
          </div>
        ))}
      </div>

      <button
        className="btn btn-primary btn-enroll"
        onClick={handleEnroll}
        disabled={loading || !course.isActive}
      >
        {loading ? 'Processing...' : 'Enroll Now'}
      </button>

      {!course.isActive && (
        <div className="course-inactive">Currently Unavailable</div>
      )}
    </div>
  );
};

export default CourseCard;
