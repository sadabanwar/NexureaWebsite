# 🎓 Nexurea Entrepreneur - Digital University Platform

<div align="center">

![Status](https://img.shields.io/badge/Status-Production%20Ready-success)
![Backend](https://img.shields.io/badge/Backend-Spring%20Boot-green)
![Frontend](https://img.shields.io/badge/Frontend-React%20TypeScript-blue)
![Database](https://img.shields.io/badge/Database-MySQL-orange)
![Payment](https://img.shields.io/badge/Payment-Razorpay-blue)

**A Complete Digital University Platform with Integrated Affiliate Marketing**

[Features](#-features) • [Quick Start](#-quick-start) • [Documentation](#-documentation) • [Tech Stack](#-technology-stack)

</div>

---

## 🌟 Overview

**Nexurea Entrepreneur** is a professional digital university platform that combines e-learning with an innovative affiliate marketing system. Users can purchase courses, refer others, earn commissions, and withdraw their earnings—all in one integrated platform.

### Key Highlights
- 💰 **Multi-tier Commission System** (50%-60% commissions)
- 🎥 **Premium Course Content** with video streaming
- 💳 **Secure Payment Gateway** (Razorpay integration)
- 📊 **Real-time Analytics Dashboard**
- 🔗 **Unique Referral Links** for every user
- 🛡️ **Complete Admin Panel** for management
- 📱 **Fully Responsive** mobile-friendly design
- 🎨 **Premium UI** with Navy Blue + Gold theme

---

## ✨ Features

### For Students/Affiliates
- ✅ Browse and purchase course packages
- ✅ Access course videos after purchase
- ✅ Get unique referral link
- ✅ Track commissions in real-time
- ✅ Request withdrawals (min ₹100)
- ✅ View complete transaction history
- ✅ Refer friends and earn up to 60% commission

### For Administrators
- ✅ Manage users (activate/deactivate)
- ✅ Create and edit course packages
- ✅ Upload and manage course videos
- ✅ Approve/reject withdrawal requests
- ✅ View comprehensive analytics
- ✅ Generate sales reports
- ✅ Control commission rates

### Technical Features
- ✅ JWT-based authentication
- ✅ Role-based access control (USER, ADMIN)
- ✅ Secure password encryption
- ✅ Payment signature verification
- ✅ Transaction tracking
- ✅ RESTful API architecture
- ✅ Responsive design (mobile, tablet, desktop)

---

## 💰 Course Packages & Commission Structure

| Package | Price | Commission Rate | Commission Amount |
|---------|-------|----------------|-------------------|
| **Startup Essentials** | ₹499 | 50% | ₹249.50 |
| **Business Pro** | ₹999 | 55% | ₹549.45 |
| **Growth Accelerator** | ₹1,499 | 57% | ₹854.43 |
| **Enterprise Master** | ₹1,999 | 60% | ₹1,199.40 |

---

## 🚀 Quick Start

### Prerequisites
- Java 8+
- Node.js 14+
- MySQL 8.0+
- Maven 3.6+
- Razorpay Account

### 1. Clone the Repository
```bash
git clone <repository-url>
cd javalld1practice-remote
```

### 2. Setup Database
```bash
mysql -u root -p < DATABASE_SETUP.sql
```

### 3. Configure Backend
Edit `src/main/resources/application.properties`:
```properties
# Database
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

# Razorpay
razorpay.key.id=YOUR_RAZORPAY_KEY_ID
razorpay.key.secret=YOUR_RAZORPAY_KEY_SECRET
```

### 4. Start Backend
```bash
mvn clean install
mvn spring-boot:run
```
Backend runs on: **http://localhost:8080**

### 5. Setup Frontend
```bash
cd nexurea-frontend
npm install
```

Edit `.env`:
```env
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_RAZORPAY_KEY=YOUR_RAZORPAY_KEY_ID
```

### 6. Start Frontend
```bash
npm start
```
Frontend runs on: **http://localhost:3000**

### 7. Login as Admin
- URL: http://localhost:3000/login
- Username: `admin`
- Password: `admin123`
- **⚠️ Change password immediately!**

---

## 📚 Documentation

- **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)** - Complete project overview
- **[DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md)** - Detailed setup and deployment
- **[FRONTEND_SETUP.md](FRONTEND_SETUP.md)** - Frontend specific guide
- **[DATABASE_SETUP.sql](DATABASE_SETUP.sql)** - Database initialization
- **[nexurea-frontend/README.md](nexurea-frontend/README.md)** - React app docs

---

## 🛠️ Technology Stack

### Backend
| Technology | Version | Purpose |
|------------|---------|---------|
| Spring Boot | 2.4.2 | REST API Framework |
| Spring Security | 2.4.2 | Authentication & Authorization |
| Spring Data JPA | 2.4.2 | ORM & Database Access |
| MySQL | 8.0+ | Database |
| JWT | 0.9.1 | Token-based Auth |
| Razorpay SDK | 1.4.3 | Payment Gateway |
| Lombok | Latest | Boilerplate Reduction |
| ModelMapper | 2.3.9 | DTO Mapping |
| Swagger | 3.0.0 | API Documentation |

### Frontend
| Technology | Version | Purpose |
|------------|---------|---------|
| React | 18.2 | UI Framework |
| TypeScript | 4.9 | Type Safety |
| React Router | 6.20 | Routing |
| Axios | 1.6 | HTTP Client |
| React Player | 2.13 | Video Player |
| Recharts | 2.10 | Charts & Graphs |
| Razorpay Checkout | Latest | Payment UI |

---

## 📂 Project Structure

```
javalld1practice-remote/
├── src/main/java/com/springboot/blog/
│   ├── entity/              # JPA Entities (7 entities)
│   ├── repository/          # Data Repositories (7 repos)
│   ├── service/             # Business Logic (8 services)
│   │   └── impl/            # Service Implementations
│   ├── controller/          # REST Controllers (9 controllers)
│   ├── payload/             # DTOs (14 DTOs)
│   ├── config/              # Configuration (Security, Swagger)
│   ├── security/            # JWT & Auth utilities
│   └── exception/           # Custom exceptions
│
├── src/main/resources/
│   └── application.properties   # App configuration
│
├── nexurea-frontend/
│   ├── src/
│   │   ├── components/      # Reusable components (6)
│   │   ├── pages/           # Page components (13)
│   │   ├── services/        # API services (6)
│   │   ├── context/         # React Context
│   │   ├── types/           # TypeScript types
│   │   └── styles/          # CSS & theme
│   └── public/
│
├── DATABASE_SETUP.sql       # Database init script
├── DEPLOYMENT_GUIDE.md      # Setup guide
├── PROJECT_SUMMARY.md       # Complete summary
└── README.md                # This file
```

---

## 🎯 API Endpoints

### Public Endpoints
```
POST   /api/auth/signup      - Register new user
POST   /api/auth/signin      - User login
GET    /api/packages          - Get all active courses
GET    /api/packages/{id}     - Get course details
GET    /api/referral/validate/{code} - Validate referral
```

### Protected Endpoints (USER)
```
GET    /api/dashboard/affiliate       - User dashboard
GET    /api/commissions/my-commissions - User commissions
GET    /api/purchases/my-purchases    - User purchases
GET    /api/videos/package/{id}       - Course videos
POST   /api/payments/create-order     - Create payment
POST   /api/payments/verify           - Verify payment
POST   /api/withdrawals               - Request withdrawal
GET    /api/withdrawals/my-withdrawals - Withdrawal history
GET    /api/referral/my-code          - Get referral code
```

### Admin Endpoints (ADMIN)
```
GET    /api/admin/users               - All users
PATCH  /api/admin/users/{id}/toggle   - Toggle user status
GET    /api/dashboard/admin           - Admin analytics
GET    /api/withdrawals/pending       - Pending withdrawals
PATCH  /api/withdrawals/{id}/approve  - Approve withdrawal
PATCH  /api/withdrawals/{id}/reject   - Reject withdrawal
POST   /api/packages                  - Create course
PUT    /api/packages/{id}             - Update course
POST   /api/videos                    - Add video
```

**Swagger Documentation:** http://localhost:8080/swagger-ui.html

---

## 🎨 Design & Theme

### Color Palette
```css
--primary-navy: #1a237e
--secondary-navy: #283593
--gold: #ffd700
--light-gold: #ffe44d
--background: #f5f5f5
--success: #4caf50
--error: #f44336
```

### Design Principles
- **Professional & Premium** - Clean, modern interface
- **Mobile-First** - Responsive on all devices
- **Fast & Smooth** - Optimized performance
- **Intuitive** - Easy navigation and UX

---

## 🔒 Security Features

- ✅ **JWT Authentication** - Secure token-based auth
- ✅ **BCrypt Password Encryption** - Industry-standard hashing
- ✅ **Role-Based Access Control** - USER and ADMIN roles
- ✅ **Payment Signature Verification** - Razorpay HMAC validation
- ✅ **CORS Configuration** - Controlled cross-origin access
- ✅ **SQL Injection Prevention** - Parameterized queries
- ✅ **XSS Protection** - Input sanitization

---

## 🧪 Testing

### Test User Flow
1. **Register** → Create account with optional referral code
2. **Browse** → View course packages
3. **Purchase** → Buy course with Razorpay
4. **Learn** → Watch course videos
5. **Refer** → Share referral link
6. **Earn** → Get commissions from referrals
7. **Withdraw** → Request payout (min ₹100)

### Razorpay Test Cards
**Success:**
- Card: `4111 1111 1111 1111`
- CVV: Any 3 digits
- Expiry: Any future date

**Failure:**
- Card: `4111 1111 1111 1234`

---

## 📊 Dashboard Metrics

### User Dashboard
- 💵 **Total Sales** - Revenue from referrals
- 💰 **Total Commission** - Earnings
- 👥 **Referral Count** - Number of referrals
- 💎 **Available Balance** - Current balance
- 📤 **Withdrawn Amount** - Total withdrawn
- 🔗 **Referral Link** - Copy-to-clipboard

### Admin Dashboard
- 👤 Total Registered Users
- 💵 Total Platform Revenue
- ⏳ Pending Withdrawal Requests
- 📈 Revenue Chart (last 6 months)
- 📋 Recent Transactions
- 📊 Package Performance

---

## 🚢 Deployment

### Production Checklist
- [ ] Change admin password
- [ ] Update JWT secret
- [ ] Use production Razorpay keys
- [ ] Enable HTTPS/SSL
- [ ] Configure CORS for production domain
- [ ] Set up database backups
- [ ] Enable logging and monitoring
- [ ] Test all payment flows
- [ ] Load test the application
- [ ] Set up CI/CD pipeline

### Deployment Options
- **Backend:** AWS EC2, Heroku, DigitalOcean, Docker
- **Frontend:** Netlify, Vercel, AWS S3+CloudFront
- **Database:** AWS RDS, MySQL managed hosting

See **[DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md)** for detailed instructions.

---

## 🐛 Troubleshooting

### Common Issues

**Database Connection Error**
```
Solution: Check MySQL service is running and credentials are correct
```

**Port Already in Use**
```bash
# Change port in application.properties
server.port=8081
```

**Frontend Can't Connect**
```
Solution: Verify REACT_APP_API_BASE_URL in .env
```

**Razorpay Not Loading**
```html
<!-- Ensure this is in public/index.html -->
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
```

---

## 📈 Future Enhancements

- [ ] Email notifications (welcome, commission alerts)
- [ ] SMS notifications via Twilio
- [ ] Course progress tracking
- [ ] Certificate generation
- [ ] Multi-level affiliate system
- [ ] Automated withdrawal processing
- [ ] Mobile app (React Native)
- [ ] Live classes integration
- [ ] Discussion forums
- [ ] Gamification (badges, leaderboards)

---

## 🤝 Contributing

This is a complete production-ready project. For custom modifications or enhancements, please follow the existing code structure and conventions.

---

## 📝 License

This project is proprietary software developed for Nexurea Entrepreneur Digital University.

---

## 📞 Support

For technical support:
- Check the documentation files
- Review API documentation at `/swagger-ui.html`
- Test with sample data first
- Check browser/server console logs

---

## 🎉 Acknowledgments

Built with:
- Spring Boot Framework
- React + TypeScript
- MySQL Database
- Razorpay Payment Gateway
- Love and lots of coffee ☕

---

<div align="center">

**Made with ❤️ for Digital Education**

**Nexurea Entrepreneur - Empowering Digital Entrepreneurs** 🚀

[⬆ Back to Top](#-nexurea-entrepreneur---digital-university-platform)

</div>
