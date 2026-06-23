# Nexurea Entrepreneur - Complete Deployment Guide

## 🚀 Quick Start

### Prerequisites
- Java 8 or higher
- Node.js 14+ and npm
- MySQL 8.0+
- Maven 3.6+
- Razorpay account (for payment gateway)

## 📋 Step-by-Step Setup

### 1. Database Setup

```bash
# Login to MySQL
mysql -u root -p

# Run the setup script
source DATABASE_SETUP.sql

# Or manually create database
CREATE DATABASE nexurea_university;
```

**Default Admin Credentials:**
- Username: `admin`
- Password: `admin123`
- **⚠️ Change this password immediately after first login!**

### 2. Backend Configuration

#### Update `src/main/resources/application.properties`

```properties
# Database Configuration (Update with your credentials)
spring.datasource.url=jdbc:mysql://localhost:3306/nexurea_university?useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

# Razorpay Configuration (Get from https://dashboard.razorpay.com/)
razorpay.key.id=YOUR_RAZORPAY_KEY_ID
razorpay.key.secret=YOUR_RAZORPAY_KEY_SECRET
```

#### Start Backend Server

```bash
# Navigate to project directory
cd javalld1practice-remote

# Install dependencies
mvn clean install

# Run the application
mvn spring-boot:run

# Backend will start on http://localhost:8080
```

### 3. Frontend Configuration

#### Update `.env` file in `nexurea-frontend/`

```env
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_RAZORPAY_KEY=YOUR_RAZORPAY_KEY_ID
```

#### Install and Start Frontend

```bash
# Navigate to frontend directory
cd nexurea-frontend

# Install dependencies
npm install

# Start development server
npm start

# Frontend will start on http://localhost:3000
```

## 🔑 Getting Razorpay Keys

1. Sign up at [Razorpay Dashboard](https://dashboard.razorpay.com/)
2. Navigate to Settings → API Keys
3. Generate Test/Live keys
4. Copy Key ID and Key Secret
5. Update in both backend `application.properties` and frontend `.env`

## 🌐 Production Deployment

### Backend Deployment

#### Option 1: JAR Deployment
```bash
# Build JAR file
mvn clean package -DskipTests

# Run JAR
java -jar target/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar
```

#### Option 2: Docker Deployment
```dockerfile
# Create Dockerfile
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
# Build Docker image
docker build -t nexurea-backend .

# Run container
docker run -p 8080:8080 nexurea-backend
```

### Frontend Deployment

#### Build for Production
```bash
cd nexurea-frontend
npm run build
```

#### Deploy to Hosting

**Option 1: Netlify**
```bash
# Install Netlify CLI
npm install -g netlify-cli

# Deploy
netlify deploy --prod --dir=build
```

**Option 2: Vercel**
```bash
# Install Vercel CLI
npm install -g vercel

# Deploy
vercel --prod
```

**Option 3: Traditional Web Server (Apache/Nginx)**
- Copy `build` folder contents to web server directory
- Configure server to serve `index.html` for all routes

## 🔒 Security Checklist

### Before Going Live:

- [ ] Change default admin password
- [ ] Update JWT secret in `application.properties`
- [ ] Use production Razorpay keys
- [ ] Enable HTTPS/SSL
- [ ] Configure CORS properly
- [ ] Set secure database credentials
- [ ] Enable firewall rules
- [ ] Set up backup strategy
- [ ] Configure logging and monitoring
- [ ] Test all payment flows

## 📊 Testing the Application

### 1. Test User Registration
- Go to http://localhost:3000/register
- Create a new user account
- Note your referral code

### 2. Test Course Purchase
- Browse courses at http://localhost:3000/courses
- Select a package
- Complete payment with Razorpay test cards
- Verify course appears in "My Courses"

### 3. Test Affiliate System
- Copy your referral code
- Logout and register a new user with your referral code
- Have the new user purchase a course
- Check your dashboard for commission

### 4. Test Withdrawal
- Go to Dashboard
- Check available balance
- Request withdrawal (min ₹100)
- Login as admin to approve

### 5. Test Admin Panel
- Login with admin credentials
- Access http://localhost:3000/admin
- Test user management
- Test course management
- Test withdrawal approval

## 🧪 Razorpay Test Cards

For testing payments in test mode:

**Successful Payment:**
- Card: 4111 1111 1111 1111
- CVV: Any 3 digits
- Expiry: Any future date

**Failed Payment:**
- Card: 4111 1111 1111 1234
- Use this to test payment failure scenarios

## 📱 Mobile Responsiveness

The application is fully responsive. Test on:
- Desktop (1920x1080)
- Tablet (768x1024)
- Mobile (375x667)

## 🐛 Troubleshooting

### Backend Issues

**Database Connection Error:**
```
Error: Unable to create initial connections of pool
Solution: Check MySQL is running and credentials are correct
```

**Port Already in Use:**
```bash
# Change port in application.properties
server.port=8081
```

**Razorpay API Error:**
```
Solution: Verify Razorpay keys are correct and account is active
```

### Frontend Issues

**Cannot Connect to Backend:**
```
Solution: Ensure REACT_APP_API_BASE_URL in .env is correct
```

**Razorpay Not Loading:**
```
Solution: Check if Razorpay script is added in public/index.html
```

**Build Errors:**
```bash
# Clear cache and reinstall
rm -rf node_modules package-lock.json
npm install
```

## 🎯 Post-Deployment Tasks

1. **Create Course Content:**
   - Login as admin
   - Go to Course Management
   - Upload actual video content
   - Add course descriptions and thumbnails

2. **Configure Email (Optional):**
   - Set up SMTP in Spring Boot
   - Send welcome emails on registration
   - Send commission notifications

3. **Set Up Analytics:**
   - Google Analytics for frontend
   - Application performance monitoring
   - Database query optimization

4. **Marketing Setup:**
   - Create landing page content
   - Set up social media links
   - Configure SEO meta tags

## 📈 Monitoring

### Key Metrics to Track:
- User registrations
- Course purchases
- Affiliate conversions
- Withdrawal requests
- API response times
- Error rates

### Recommended Tools:
- **Backend**: Spring Boot Actuator, Prometheus
- **Frontend**: Google Analytics, Sentry
- **Database**: MySQL Workbench, phpMyAdmin
- **Payments**: Razorpay Dashboard

## 🆘 Support

For issues or questions:
- Check the `README.md` files
- Review API documentation at http://localhost:8080/swagger-ui.html
- Test with sample data first
- Check browser console for frontend errors
- Check backend logs for API errors

## 🎉 Success!

Once everything is set up, you should have:
- ✅ Backend API running on port 8080
- ✅ Frontend running on port 3000
- ✅ Database initialized with default data
- ✅ Payment gateway configured
- ✅ Admin panel accessible
- ✅ Affiliate system working
- ✅ All features functional

**Your Nexurea Entrepreneur Digital University is now live!** 🚀
