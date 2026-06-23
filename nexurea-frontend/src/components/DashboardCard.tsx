import React from 'react';
import './DashboardCard.css';

interface DashboardCardProps {
  title: string;
  value: string | number;
  icon?: React.ReactNode;
  color?: 'primary' | 'success' | 'warning' | 'gold';
  subtitle?: string;
}

const DashboardCard: React.FC<DashboardCardProps> = ({
  title,
  value,
  icon,
  color = 'primary',
  subtitle,
}) => {
  return (
    <div className={`dashboard-card dashboard-card-${color}`}>
      <div className="dashboard-card-content">
        <div className="dashboard-card-text">
          <h4 className="dashboard-card-title">{title}</h4>
          <div className="dashboard-card-value">{value}</div>
          {subtitle && <p className="dashboard-card-subtitle">{subtitle}</p>}
        </div>
        {icon && <div className="dashboard-card-icon">{icon}</div>}
      </div>
    </div>
  );
};

export default DashboardCard;
