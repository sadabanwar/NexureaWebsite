# 🚀 Nexurea Frontend - Quick Reference

## 📦 Installation (3 commands)
```bash
cd nexurea-frontend
npm install
npm start
```

## 🎨 Theme Colors
```css
--primary-navy: #1a237e
--secondary-navy: #283593
--gold: #ffd700
--light-gold: #ffe44d
--background: #f5f5f5
```

## 🗂️ File Structure
```
nexurea-frontend/
├── src/
│   ├── components/     # Reusable components (6)
│   ├── pages/          # Page components (12)
│   ├── services/       # API services (6)
│   ├── context/        # React context (1)
│   ├── types/          # TypeScript types (1)
│   └── styles/         # Global styles (1)
├── public/             # Static assets
├── .env                # Environment variables
└── package.json        # Dependencies
```

## 🔑 Environment Variables (.env)
```env
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_RAZORPAY_KEY=your_razorpay_key_here
```

## 🛣️ Routes Quick Reference

### Public (5)
- `/` - Home
- `/about` - About Us
- `/courses` - Course Catalog
- `/login` - Login
- `/register` - Register

### Protected (4)
- `/dashboard` - Affiliate Dashboard
- `/my-courses` - My Courses
- `/video/:id` - Video Player
- `/withdrawals` - Withdrawals

### Admin (4)
- `/admin` - Admin Dashboard
- `/admin/users` - User Management
- `/admin/courses` - Course Management
- `/admin/withdrawals` - Withdrawal Approval

## 📡 API Endpoints

### Auth
- `POST /api/auth/login`
- `POST /api/auth/register`

### Courses
- `GET /api/courses/packages`
- `GET /api/courses/packages/:id`
- `GET /api/courses/packages/:id/videos`
- `GET /api/courses/my-courses`

### Payments
- `POST /api/payments/create-order`
- `POST /api/payments/verify`

### Dashboard
- `GET /api/dashboard`
- `GET /api/dashboard/commissions`
- `GET /api/dashboard/referral-code`

### Withdrawals
- `POST /api/withdrawals/request`
- `GET /api/withdrawals/my-withdrawals`

### Admin
- `GET /api/admin/dashboard`
- `GET /api/admin/users`
- `PUT /api/admin/users/:id/toggle-active`
- `POST /api/admin/courses/packages`
- `POST /api/admin/courses/packages/:id/videos`
- `GET /api/admin/withdrawals`
- `PUT /api/admin/withdrawals/:id/approve`
- `PUT /api/admin/withdrawals/:id/reject`

## 🧩 Key Components

### Navbar
- Location: `src/components/Navbar.tsx`
- Features: Responsive, sticky, auth-aware

### CourseCard
- Location: `src/components/CourseCard.tsx`
- Features: Price, commission, features, enroll button

### DashboardCard
- Location: `src/components/DashboardCard.tsx`
- Features: Stat display with icon

### ProtectedRoute
- Location: `src/components/ProtectedRoute.tsx`
- Usage: Wrap routes requiring authentication

### AdminRoute
- Location: `src/components/AdminRoute.tsx`
- Usage: Wrap admin-only routes

## 🔧 Common Commands
```bash
# Install dependencies
npm install

# Start development server
npm start

# Build for production
npm run build

# Run tests
npm test

# Eject (not recommended)
npm run eject
```

## 🎯 Course Packages
| Package | Price | Commission |
|---------|-------|------------|
| Starter | ₹499  | 30% |
| Growth  | ₹999  | 40% |
| Pro     | ₹1499 | 45% |
| Elite   | ₹1999 | 50% |

## 🔐 Auth Flow
1. User registers/logs in
2. JWT token stored in localStorage
3. Token added to all API requests
4. Auto-logout on 401

## 💳 Payment Flow
1. User clicks "Enroll Now"
2. Order created via API
3. Razorpay modal opens
4. Payment processed
5. Verification via API
6. Redirect to My Courses

## 📱 Responsive Breakpoints
- Mobile: < 768px
- Tablet: 768px - 968px
- Desktop: > 968px

## 🎨 CSS Utilities
```css
/* Spacing */
.mt-1 to .mt-5    /* margin-top */
.mb-1 to .mb-5    /* margin-bottom */
.p-1 to .p-5      /* padding */

/* Layout */
.flex             /* display: flex */
.flex-center      /* center content */
.flex-between     /* space-between */
.grid             /* display: grid */
.grid-2, .grid-3, .grid-4

/* Text */
.text-center, .text-left, .text-right
.text-primary, .text-gold, .text-success

/* Buttons */
.btn              /* base button */
.btn-primary      /* gold button */
.btn-secondary    /* navy button */
.btn-outline      /* outlined button */
.btn-success, .btn-error

/* Cards */
.card             /* white card */
.card-premium     /* navy gradient card */

/* Badges */
.badge            /* base badge */
.badge-success, .badge-error, .badge-warning
```

## 🔍 Debugging Tips

### Check Token
```javascript
localStorage.getItem('token')
```

### Check User
```javascript
localStorage.getItem('user')
```

### Clear Auth
```javascript
localStorage.clear()
```

### View API Calls
Open DevTools > Network tab > XHR filter

## ⚡ Performance Tips
- Images: Use WebP format
- Videos: Use YouTube/Vimeo URLs
- Icons: Use SVG (included)
- Lazy load: Use React.lazy()
- Code splitting: Automatic with CRA

## 🐛 Common Issues

### Port in use
```bash
npx kill-port 3000
```

### Module not found
```bash
npm install
```

### Build fails
```bash
rm -rf node_modules package-lock.json
npm install
```

### TypeScript errors
Restart TS server in VS Code

## 📚 Dependencies Installed
- react, react-dom
- react-router-dom (routing)
- axios (HTTP client)
- react-player (video player)
- recharts (charts)
- typescript (type safety)

## ✅ Pre-Launch Checklist
- [ ] Update .env with production URLs
- [ ] Add Razorpay production keys
- [ ] Test all payment flows
- [ ] Test on mobile devices
- [ ] Test with backend API
- [ ] Enable HTTPS
- [ ] Setup error tracking
- [ ] Add analytics
- [ ] SEO optimization
- [ ] Performance audit

## 🎉 You're Ready!

```bash
cd nexurea-frontend
npm install
npm start
```

Visit: http://localhost:3000

**Happy Coding! 🚀**
