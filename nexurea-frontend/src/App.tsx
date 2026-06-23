import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import ProtectedRoute from './components/ProtectedRoute';
import AdminRoute from './components/AdminRoute';
import Home from './pages/Home';
import About from './pages/About';
import Courses from './pages/Courses';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import MyCourses from './pages/MyCourses';
import VideoPage from './pages/VideoPage';
import Withdrawals from './pages/Withdrawals';
import AdminDashboard from './pages/Admin/AdminDashboard';
import UserManagement from './pages/Admin/UserManagement';
import CourseManagement from './pages/Admin/CourseManagement';
import WithdrawalApproval from './pages/Admin/WithdrawalApproval';
import './styles/global.css';

const App: React.FC = () => {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Navbar />
          <main>
            <Routes>
              {/* Public Routes */}
              <Route path="/" element={<Home />} />
              <Route path="/about" element={<About />} />
              <Route path="/courses" element={<Courses />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />

              {/* Protected Routes */}
              <Route
                path="/dashboard"
                element={
                  <ProtectedRoute>
                    <Dashboard />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/my-courses"
                element={
                  <ProtectedRoute>
                    <MyCourses />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/video/:packageId"
                element={
                  <ProtectedRoute>
                    <VideoPage />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/withdrawals"
                element={
                  <ProtectedRoute>
                    <Withdrawals />
                  </ProtectedRoute>
                }
              />

              {/* Admin Routes */}
              <Route
                path="/admin"
                element={
                  <AdminRoute>
                    <AdminDashboard />
                  </AdminRoute>
                }
              />
              <Route
                path="/admin/users"
                element={
                  <AdminRoute>
                    <UserManagement />
                  </AdminRoute>
                }
              />
              <Route
                path="/admin/courses"
                element={
                  <AdminRoute>
                    <CourseManagement />
                  </AdminRoute>
                }
              />
              <Route
                path="/admin/withdrawals"
                element={
                  <AdminRoute>
                    <WithdrawalApproval />
                  </AdminRoute>
                }
              />
            </Routes>
          </main>
          <Footer />
        </div>
      </Router>
    </AuthProvider>
  );
};

export default App;
