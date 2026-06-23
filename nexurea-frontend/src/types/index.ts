export interface User {
  id: number;
  username: string;
  email: string;
  fullName: string;
  phone: string;
  role: 'USER' | 'ADMIN';
  referralCode: string;
  referredBy?: string;
  isActive: boolean;
  createdAt: string;
}

export interface CoursePackage {
  id: number;
  name: string;
  description: string;
  price: number;
  commissionRate: number;
  features: string[];
  duration: string;
  videoCount: number;
  isActive: boolean;
  createdAt: string;
}

export interface CourseVideo {
  id: number;
  packageId: number;
  title: string;
  description: string;
  videoUrl: string;
  duration: number;
  orderIndex: number;
  isActive: boolean;
}

export interface Purchase {
  id: number;
  userId: number;
  packageId: number;
  amount: number;
  orderId: string;
  paymentId: string;
  status: 'PENDING' | 'COMPLETED' | 'FAILED';
  purchaseDate: string;
  coursePackage?: CoursePackage;
}

export interface Commission {
  id: number;
  referrerId: number;
  referredUserId: number;
  purchaseId: number;
  amount: number;
  level: number;
  status: 'PENDING' | 'PAID';
  earnedDate: string;
  referredUser?: User;
  purchase?: Purchase;
}

export interface Withdrawal {
  id: number;
  userId: number;
  amount: number;
  accountHolderName: string;
  accountNumber: string;
  ifscCode: string;
  upiId?: string;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  transactionId?: string;
  requestDate: string;
  processedDate?: string;
  user?: User;
}

export interface DashboardData {
  totalSales: number;
  totalCommission: number;
  referralCount: number;
  availableBalance: number;
  withdrawnAmount: number;
  recentCommissions: Commission[];
}

export interface AdminDashboardData {
  totalUsers: number;
  activeUsers: number;
  totalSales: number;
  totalRevenue: number;
  pendingWithdrawals: number;
  revenueByMonth: {
    month: string;
    revenue: number;
  }[];
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  fullName: string;
  phone: string;
  password: string;
  referralCode?: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface PaymentOrder {
  orderId: string;
  amount: number;
  currency: string;
}

export interface PaymentVerification {
  razorpayOrderId: string;
  razorpayPaymentId: string;
  razorpaySignature: string;
}
