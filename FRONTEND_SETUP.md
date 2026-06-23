# Nexurea Entrepreneur - Frontend Setup Guide

## Technology Stack
- **React 18** with TypeScript
- **React Router v6** for navigation
- **Axios** for API calls
- **Razorpay SDK** for payments
- **Context API** for state management
- **CSS Modules** with Navy Blue + Gold theme

## Project Structure
```
nexurea-frontend/
├── public/
│   ├── index.html
│   └── logo.png
├── src/
│   ├── components/
│   │   ├── Navbar.tsx
│   │   ├── Footer.tsx
│   │   ├── CourseCard.tsx
│   │   ├── DashboardCard.tsx
│   │   └── VideoPlayer.tsx
│   ├── pages/
│   │   ├── Home.tsx
│   │   ├── About.tsx
│   │   ├── Courses.tsx
│   │   ├── Login.tsx
│   │   ├── Register.tsx
│   │   ├── Dashboard.tsx
│   │   ├── MyCourses.tsx
│   │   ├── VideoPage.tsx
│   │   ├── Withdrawals.tsx
│   │   └── Admin/
│   │       ├── AdminDashboard.tsx
│   │       ├── UserManagement.tsx
│   │       ├── CourseManagement.tsx
│   │       └── WithdrawalApproval.tsx
│   ├── context/
│   │   └── AuthContext.tsx
│   ├── services/
│   │   ├── api.ts
│   │   ├── authService.ts
│   │   ├── courseService.ts
│   │   ├── paymentService.ts
│   │   └── dashboardService.ts
│   ├── types/
│   │   └── index.ts
│   ├── styles/
│   │   ├── theme.ts
│   │   └── global.css
│   ├── App.tsx
│   └── index.tsx
```

## Color Theme
- **Primary Navy Blue**: #1a237e
- **Secondary Navy**: #283593
- **Gold**: #ffd700
- **Light Gold**: #ffe44d
- **Background**: #f5f5f5
- **White**: #ffffff
- **Success**: #4caf50
- **Error**: #f44336

## Installation Commands
```bash
cd nexurea-frontend
npm install axios react-router-dom
npm install --save-dev @types/react-router-dom
npm install recharts
npm install react-player
```

## Environment Variables (.env)
```
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_RAZORPAY_KEY=rzp_test_YOUR_KEY_ID
```

## Key Features to Implement
1. ✅ JWT Authentication with token storage
2. ✅ Protected routes with auth check
3. ✅ Course package display with pricing
4. ✅ Razorpay payment integration
5. ✅ Affiliate dashboard with metrics
6. ✅ Video player for purchased courses
7. ✅ Withdrawal request system
8. ✅ Admin panel with full management
9. ✅ Responsive design (mobile-first)
10. ✅ Loading states and error handling

## API Integration
Base URL: `http://localhost:8080/api`

### Authentication
- POST `/auth/signup` - User registration
- POST `/auth/signin` - User login

### Courses
- GET `/packages` - Get all active packages
- GET `/packages/{id}` - Get package details
- GET `/videos/package/{id}` - Get videos for package

### Payments
- POST `/payments/create-order` - Create Razorpay order
- POST `/payments/verify` - Verify payment

### Dashboard
- GET `/dashboard/affiliate` - Get affiliate stats
- GET `/commissions/my-commissions` - Get commissions
- GET `/referral/my-code` - Get referral code

### Withdrawals
- POST `/withdrawals` - Create withdrawal request
- GET `/withdrawals/my-withdrawals` - Get user withdrawals

### Admin
- GET `/admin/users` - Get all users
- GET `/dashboard/admin` - Get admin stats
- PATCH `/admin/users/{id}/toggle` - Toggle user status
- GET `/withdrawals/pending` - Get pending withdrawals
- PATCH `/withdrawals/{id}/approve` - Approve withdrawal

## Razorpay Integration Steps
1. Include Razorpay script in public/index.html:
   ```html
   <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
   ```
2. Create order from backend
3. Open Razorpay checkout modal
4. Handle payment success/failure
5. Verify payment on backend

## Running the Application
```bash
# Start backend (Spring Boot)
cd javalld1practice-remote
mvn spring-boot:run

# Start frontend (React)
cd nexurea-frontend
npm start
```

Frontend will run on: http://localhost:3000
Backend API will run on: http://localhost:8080
