# 🏗️ Nexurea Frontend Architecture

## System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     NEXUREA FRONTEND                        │
│                   (React + TypeScript)                      │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                        ROUTING LAYER                        │
│                    (React Router v6)                        │
├─────────────────────────────────────────────────────────────┤
│  Public Routes:                                             │
│    / → Home                                                 │
│    /about → About                                           │
│    /courses → Courses                                       │
│    /login → Login                                           │
│    /register → Register                                     │
├─────────────────────────────────────────────────────────────┤
│  Protected Routes (ProtectedRoute wrapper):                 │
│    /dashboard → Dashboard                                   │
│    /my-courses → MyCourses                                  │
│    /video/:id → VideoPage                                   │
│    /withdrawals → Withdrawals                               │
├─────────────────────────────────────────────────────────────┤
│  Admin Routes (AdminRoute wrapper):                         │
│    /admin → AdminDashboard                                  │
│    /admin/users → UserManagement                            │
│    /admin/courses → CourseManagement                        │
│    /admin/withdrawals → WithdrawalApproval                  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                      CONTEXT LAYER                          │
│                     (AuthContext)                           │
├─────────────────────────────────────────────────────────────┤
│  • user: User | null                                        │
│  • isAuthenticated: boolean                                 │
│  • isAdmin: boolean                                         │
│  • login(credentials)                                       │
│  • register(data)                                           │
│  • logout()                                                 │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                      SERVICE LAYER                          │
├─────────────────────────────────────────────────────────────┤
│  api.ts (Axios Instance)                                    │
│    • Base URL configuration                                 │
│    • JWT token interceptor (request)                        │
│    • 401 auto-logout interceptor (response)                 │
├─────────────────────────────────────────────────────────────┤
│  authService.ts                                             │
│    • login(credentials) → JWT + User                        │
│    • register(data) → JWT + User                            │
│    • logout() → Clear storage                               │
│    • getCurrentUser() → User from localStorage              │
├─────────────────────────────────────────────────────────────┤
│  courseService.ts                                           │
│    • getPackages() → CoursePackage[]                        │
│    • getPackageById(id) → CoursePackage                     │
│    • getVideos(packageId) → CourseVideo[]                   │
│    • getUserCourses() → CoursePackage[]                     │
│    • createPackage(data) → CoursePackage (admin)            │
│    • createVideo(data) → CourseVideo (admin)                │
├─────────────────────────────────────────────────────────────┤
│  paymentService.ts                                          │
│    • createOrder(packageId, refCode) → PaymentOrder         │
│    • verifyPayment(verification) → void                     │
│    • handlePayment() → Razorpay integration                 │
├─────────────────────────────────────────────────────────────┤
│  dashboardService.ts                                        │
│    • getDashboard() → DashboardData                         │
│    • getCommissions() → Commission[]                        │
│    • getReferralCode() → string                             │
│    • getAdminDashboard() → AdminDashboardData (admin)       │
│    • getAllUsers() → User[] (admin)                         │
│    • toggleUserActive(userId) → User (admin)                │
├─────────────────────────────────────────────────────────────┤
│  withdrawalService.ts                                       │
│    • requestWithdrawal(data) → Withdrawal                   │
│    • getMyWithdrawals() → Withdrawal[]                      │
│    • getPendingWithdrawals() → Withdrawal[] (admin)         │
│    • approveWithdrawal(id, txnId) → Withdrawal (admin)      │
│    • rejectWithdrawal(id) → Withdrawal (admin)              │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                  COMPONENT HIERARCHY                        │
├─────────────────────────────────────────────────────────────┤
│  App                                                        │
│  ├── AuthProvider                                           │
│  │   ├── Router                                             │
│  │   │   ├── Navbar                                         │
│  │   │   ├── Routes                                         │
│  │   │   │   ├── Home                                       │
│  │   │   │   ├── About                                      │
│  │   │   │   ├── Courses                                    │
│  │   │   │   │   └── CourseCard (multiple)                 │
│  │   │   │   ├── Login                                      │
│  │   │   │   ├── Register                                   │
│  │   │   │   ├── ProtectedRoute                             │
│  │   │   │   │   ├── Dashboard                              │
│  │   │   │   │   │   └── DashboardCard (multiple)          │
│  │   │   │   │   ├── MyCourses                              │
│  │   │   │   │   ├── VideoPage                              │
│  │   │   │   │   │   └── ReactPlayer                        │
│  │   │   │   │   └── Withdrawals                            │
│  │   │   │   └── AdminRoute                                 │
│  │   │   │       ├── AdminDashboard                         │
│  │   │   │       │   └── DashboardCard (multiple)           │
│  │   │   │       ├── UserManagement                         │
│  │   │   │       ├── CourseManagement                       │
│  │   │   │       └── WithdrawalApproval                     │
│  │   │   └── Footer                                         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     BACKEND API                             │
│              (Spring Boot - Port 8080)                      │
├─────────────────────────────────────────────────────────────┤
│  Auth API:         /api/auth/*                              │
│  Course API:       /api/courses/*                           │
│  Payment API:      /api/payments/*                          │
│  Dashboard API:    /api/dashboard/*                         │
│  Withdrawal API:   /api/withdrawals/*                       │
│  Admin API:        /api/admin/*                             │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                   EXTERNAL SERVICES                         │
├─────────────────────────────────────────────────────────────┤
│  Razorpay Payment Gateway                                   │
│    • Order creation                                         │
│    • Payment processing                                     │
│    • Webhook verification                                   │
└─────────────────────────────────────────────────────────────┘
```

## Data Flow Diagrams

### 1. Authentication Flow

```
User → Login Page
  ↓
  Enter credentials
  ↓
  authService.login(credentials)
  ↓
  POST /api/auth/login
  ↓
  Backend validates
  ↓
  Returns: { token, user }
  ↓
  Store in localStorage
  ↓
  Update AuthContext
  ↓
  Redirect to Dashboard
```

### 2. Course Purchase Flow

```
User → Courses Page
  ↓
  Browse courses
  ↓
  Click "Enroll Now" on CourseCard
  ↓
  Check authentication
  ↓
  paymentService.handlePayment(packageId)
  ↓
  POST /api/payments/create-order
  ↓
  Backend creates Razorpay order
  ↓
  Returns: { orderId, amount, currency }
  ↓
  Open Razorpay modal
  ↓
  User completes payment
  ↓
  Razorpay returns: { orderId, paymentId, signature }
  ↓
  POST /api/payments/verify
  ↓
  Backend verifies signature
  ↓
  Creates purchase record
  ↓
  Calculates commissions
  ↓
  Redirect to My Courses
```

### 3. Video Watching Flow

```
User → My Courses Page
  ↓
  courseService.getUserCourses()
  ↓
  GET /api/courses/my-courses
  ↓
  Display purchased courses
  ↓
  Click "Watch Now"
  ↓
  Navigate to /video/:packageId
  ↓
  courseService.getVideos(packageId)
  ↓
  GET /api/courses/packages/:id/videos
  ↓
  Display video list
  ↓
  Load first video in ReactPlayer
  ↓
  User watches video
  ↓
  Click on playlist to switch videos
```

### 4. Withdrawal Request Flow

```
User → Withdrawals Page
  ↓
  Click "New Withdrawal"
  ↓
  Fill form (amount, account details)
  ↓
  withdrawalService.requestWithdrawal(data)
  ↓
  POST /api/withdrawals/request
  ↓
  Backend validates:
    • Sufficient balance
    • Minimum amount (₹500)
  ↓
  Create withdrawal record (PENDING)
  ↓
  Success message
  ↓
  Reload withdrawal list
  ↓
  Admin → Withdrawal Approval Page
  ↓
  GET /api/admin/withdrawals
  ↓
  Review pending withdrawals
  ↓
  Click "Approve" → Enter Transaction ID
  ↓
  PUT /api/admin/withdrawals/:id/approve
  ↓
  Backend updates status (APPROVED)
  ↓
  Update user's withdrawn amount
  ↓
  User sees updated status
```

### 5. Affiliate Earnings Flow

```
Referrer → Dashboard
  ↓
  Copy referral link
  ↓
  Share with friend
  ↓
  Friend clicks link
  ↓
  Navigate to /register?ref=REFCODE
  ↓
  Friend registers with referral code
  ↓
  POST /api/auth/register { referralCode }
  ↓
  Backend creates user with referredBy
  ↓
  Friend purchases course
  ↓
  Backend calculates commission:
    • Course price × commission rate
    • Store in Commission table
  ↓
  Referrer → Dashboard
  ↓
  GET /api/dashboard
  ↓
  See updated:
    • Total Sales
    • Total Commission
    • Referral Count
    • Available Balance
  ↓
  GET /api/dashboard/commissions
  ↓
  See commission details in table
```

## State Management

### Global State (AuthContext)
```typescript
{
  user: {
    id: number
    username: string
    email: string
    fullName: string
    role: 'USER' | 'ADMIN'
    referralCode: string
  } | null,
  isAuthenticated: boolean,
  isAdmin: boolean,
  login: (credentials) => Promise<void>,
  register: (data) => Promise<void>,
  logout: () => void
}
```

### Local Storage
```javascript
localStorage {
  token: string,          // JWT token
  user: string            // JSON.stringify(User)
}
```

### Component State Examples
```typescript
// Dashboard
const [data, setData] = useState<DashboardData | null>(null)
const [loading, setLoading] = useState(true)

// Courses
const [courses, setCourses] = useState<CoursePackage[]>([])
const [loading, setLoading] = useState(true)

// VideoPage
const [videos, setVideos] = useState<CourseVideo[]>([])
const [currentVideo, setCurrentVideo] = useState<CourseVideo | null>(null)
```

## Security Architecture

### JWT Token Flow
```
1. User logs in
   ↓
2. Backend generates JWT
   ↓
3. Frontend stores in localStorage
   ↓
4. Every API request:
   • Add header: Authorization: Bearer <token>
   ↓
5. Backend validates token
   ↓
6. If valid → Process request
7. If invalid (401) → Frontend auto-logout
```

### Route Protection
```
Public Route → Anyone can access
  ↓
Protected Route → Must be authenticated
  • Check: isAuthenticated
  • Redirect to /login if false
  ↓
Admin Route → Must be admin
  • Check: isAuthenticated && isAdmin
  • Redirect to /dashboard if not admin
```

## Performance Optimizations

### Code Splitting
- React.lazy() for route-based splitting (ready to implement)
- Automatic chunk splitting by Create React App

### Caching
- localStorage for token and user
- Component state for API responses

### Lazy Loading
- Images: Can add lazy loading attribute
- Videos: react-player handles lazy loading

### Bundle Size
- Tree shaking (automatic)
- Production build minification
- Gzip compression (server-side)

## Deployment Architecture

```
┌─────────────────────────────────────────────────────┐
│              CDN / Static Hosting                   │
│           (Netlify, Vercel, S3)                     │
│                                                     │
│  • Serves: build/                                   │
│  • index.html, *.js, *.css, assets/                 │
│  • HTTPS enabled                                    │
│  • Global CDN                                       │
└─────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│              Backend API Server                      │
│          (AWS, Heroku, DigitalOcean)                │
│                                                     │
│  • Spring Boot application                          │
│  • Port: 8080                                       │
│  • Database: PostgreSQL/MySQL                       │
│  • Redis for caching                                │
└─────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│              External Services                       │
│                                                     │
│  • Razorpay (Payments)                              │
│  • Email Service (AWS SES, SendGrid)                │
│  • File Storage (S3, Cloudinary)                    │
└─────────────────────────────────────────────────────┘
```

## Technology Stack Overview

```
┌─────────────────────────────────────────────────────┐
│                  PRESENTATION                       │
│  React 18.2 + TypeScript 4.9                        │
│  • Functional Components                            │
│  • Hooks (useState, useEffect, useContext)          │
│  • Custom Hooks (useAuth)                           │
└─────────────────────────────────────────────────────┘
                        │
┌─────────────────────────────────────────────────────┐
│                    ROUTING                          │
│  React Router 6.20                                  │
│  • BrowserRouter                                    │
│  • Route guards (Protected, Admin)                  │
│  • Dynamic routes (/video/:id)                      │
└─────────────────────────────────────────────────────┘
                        │
┌─────────────────────────────────────────────────────┐
│                 STATE MANAGEMENT                    │
│  React Context API                                  │
│  • AuthContext (global)                             │
│  • Component state (local)                          │
│  • localStorage (persistence)                       │
└─────────────────────────────────────────────────────┘
                        │
┌─────────────────────────────────────────────────────┐
│                  HTTP CLIENT                        │
│  Axios 1.6                                          │
│  • Request interceptors (JWT)                       │
│  • Response interceptors (error handling)           │
│  • Base URL configuration                           │
└─────────────────────────────────────────────────────┘
                        │
┌─────────────────────────────────────────────────────┐
│                   UI LIBRARIES                      │
│  • react-player 2.13 (Video playback)               │
│  • recharts 2.10 (Charts)                           │
│  • Custom CSS (No framework)                        │
└─────────────────────────────────────────────────────┘
                        │
┌─────────────────────────────────────────────────────┐
│                PAYMENT INTEGRATION                  │
│  Razorpay Checkout                                  │
│  • Inline checkout                                  │
│  • Webhook verification                             │
│  • Secure payment flow                              │
└─────────────────────────────────────────────────────┘
```

## Folder Structure Logic

```
src/
├── components/          # Reusable UI components
│   ├── Navbar.tsx      # Used in App.tsx
│   ├── Footer.tsx      # Used in App.tsx
│   ├── CourseCard.tsx  # Used in Courses.tsx
│   ├── DashboardCard.tsx # Used in Dashboard.tsx, AdminDashboard.tsx
│   ├── ProtectedRoute.tsx # Wraps protected routes
│   └── AdminRoute.tsx  # Wraps admin routes
│
├── pages/              # Route components
│   ├── Home.tsx        # Public
│   ├── About.tsx       # Public
│   ├── Courses.tsx     # Public
│   ├── Login.tsx       # Public
│   ├── Register.tsx    # Public
│   ├── Dashboard.tsx   # Protected
│   ├── MyCourses.tsx   # Protected
│   ├── VideoPage.tsx   # Protected
│   ├── Withdrawals.tsx # Protected
│   └── Admin/          # Admin-only pages
│       ├── AdminDashboard.tsx
│       ├── UserManagement.tsx
│       ├── CourseManagement.tsx
│       └── WithdrawalApproval.tsx
│
├── services/           # API integration layer
│   ├── api.ts          # Axios instance
│   ├── authService.ts  # Auth operations
│   ├── courseService.ts # Course operations
│   ├── paymentService.ts # Payment operations
│   ├── dashboardService.ts # Dashboard operations
│   └── withdrawalService.ts # Withdrawal operations
│
├── context/            # React context
│   └── AuthContext.tsx # Authentication state
│
├── types/              # TypeScript definitions
│   └── index.ts        # All interfaces
│
├── styles/             # Global styles
│   └── global.css      # Theme, utilities
│
├── App.tsx             # Main app component
└── index.tsx           # Entry point
```

---

This architecture provides:
- ✅ Scalability
- ✅ Maintainability
- ✅ Type safety
- ✅ Security
- ✅ Performance
- ✅ Developer experience
