-- Nexurea Entrepreneur Digital University - Database Setup Script
-- MySQL Database Initialization

-- Create database
CREATE DATABASE IF NOT EXISTS nexurea_university;
USE nexurea_university;

-- Note: Tables will be auto-created by Hibernate with spring.jpa.hibernate.ddl-auto=update
-- This script is for manual setup or initialization

-- Optional: Create roles manually (or let the application create them)
INSERT INTO roles (name) VALUES ('ROLE_USER') ON DUPLICATE KEY UPDATE name='ROLE_USER';
INSERT INTO roles (name) VALUES ('ROLE_ADMIN') ON DUPLICATE KEY UPDATE name='ROLE_ADMIN';

-- Optional: Create default admin user
-- Password: admin123 (BCrypt encoded)
-- Note: You should change this password after first login
INSERT INTO users (name, username, email, password, phone, referral_code, total_earnings, available_balance, withdrawn_amount, total_referrals, created_at, active)
VALUES (
    'Admin User',
    'admin',
    'admin@nexurea.com',
    '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', -- admin123
    '9999999999',
    'ADMIN001',
    0.00,
    0.00,
    0.00,
    0,
    NOW(),
    true
) ON DUPLICATE KEY UPDATE username='admin';

-- Assign admin role to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
ON DUPLICATE KEY UPDATE user_id=user_id;

-- Create sample course packages
INSERT INTO course_packages (name, description, price, commission_rate, active, features, created_at)
VALUES
(
    'Startup Essentials',
    'Perfect for beginners starting their entrepreneurial journey. Learn the fundamentals of business and digital marketing.',
    499.00,
    50.0,
    true,
    'Business Basics|Digital Marketing Fundamentals|Social Media Strategy|Email Marketing|Basic SEO|Certificate of Completion',
    NOW()
),
(
    'Business Pro',
    'Advance your skills with comprehensive business strategies and advanced marketing techniques.',
    999.00,
    55.0,
    true,
    'Advanced Marketing|Sales Funnel Creation|Paid Advertising|Content Strategy|Analytics & Tracking|E-commerce Basics|Personal Coaching Session|Certificate of Completion',
    NOW()
),
(
    'Growth Accelerator',
    'Take your business to the next level with scaling strategies and automation tools.',
    1499.00,
    57.0,
    true,
    'Business Scaling Strategies|Marketing Automation|Advanced Analytics|Team Building|Leadership Skills|Financial Management|Multiple Coaching Sessions|Premium Support|Certificate of Completion',
    NOW()
),
(
    'Enterprise Master',
    'Complete mastery program with all courses, premium features, and lifetime access.',
    1999.00,
    60.0,
    true,
    'All Pro Features|Advanced Business Strategies|Investment & Funding|Legal & Compliance|International Business|AI & Automation Tools|Unlimited Coaching|Lifetime Access|Priority Support|Premium Certificate',
    NOW()
) ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Sample course videos for Package 1 (Startup Essentials)
INSERT INTO course_videos (title, description, video_url, thumbnail_url, order_index, duration_minutes, package_id, active, created_at)
SELECT
    'Introduction to Entrepreneurship',
    'Learn the basics of starting your own business and developing an entrepreneurial mindset.',
    'https://example.com/videos/intro-entrepreneurship.mp4',
    'https://example.com/thumbnails/intro.jpg',
    1,
    45,
    id,
    true,
    NOW()
FROM course_packages WHERE name = 'Startup Essentials'
ON DUPLICATE KEY UPDATE title=VALUES(title);

INSERT INTO course_videos (title, description, video_url, thumbnail_url, order_index, duration_minutes, package_id, active, created_at)
SELECT
    'Digital Marketing Fundamentals',
    'Master the core concepts of digital marketing including SEO, social media, and content marketing.',
    'https://example.com/videos/digital-marketing.mp4',
    'https://example.com/thumbnails/marketing.jpg',
    2,
    60,
    id,
    true,
    NOW()
FROM course_packages WHERE name = 'Startup Essentials'
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- Indexes for better performance
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_referral_code ON users(referral_code);
CREATE INDEX idx_purchase_user_id ON purchases(user_id);
CREATE INDEX idx_purchase_order_id ON purchases(order_id);
CREATE INDEX idx_commission_affiliate_id ON commissions(affiliate_id);
CREATE INDEX idx_withdrawal_user_id ON withdrawals(user_id);
CREATE INDEX idx_withdrawal_status ON withdrawals(status);

-- Show created tables
SHOW TABLES;

-- Show package data
SELECT id, name, price, commission_rate, active FROM course_packages;

-- Show roles
SELECT * FROM roles;

-- Show admin user
SELECT id, name, username, email, referral_code FROM users WHERE username = 'admin';

-- Success message
SELECT 'Database setup completed successfully!' AS Status;
SELECT 'Default admin credentials: admin / admin123' AS Note;
SELECT 'Please change the admin password after first login!' AS Warning;
