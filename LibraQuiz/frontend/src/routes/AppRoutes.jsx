import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

// Landing Page & Auth
import LandingPage from '../pages/landing/LandingPage';

// Layout
import DashboardLayout from '../layouts/DashboardLayout';

// Student Pages
import StudentDashboard from '../pages/student/StudentDashboard';
import StudentCoursesPage from '../pages/student/StudentCoursesPage';
import StudentBooksPage from '../pages/student/StudentBooksPage';
import StudentBorrowedBooksPage from '../pages/student/StudentBorrowedBooksPage';
import StudentQuizzesPage from '../pages/student/StudentQuizzesPage';
import TakeQuizPage from '../pages/student/TakeQuizPage';
import AISearchPage from '../pages/student/AISearchPage';

// Admin Pages
import AdminDashboard from '../pages/admin/AdminDashboard';
import AdminUserManagement from '../pages/admin/AdminUserManagement';
import AdminCategoryManagement from '../pages/admin/AdminCategoryManagement';

// Teacher Pages
import TeacherDashboard from '../pages/teacher/TeacherDashboard';
import TeacherQuestionBank from '../pages/teacher/TeacherQuestionBank';
import TeacherQuizBuilder from '../pages/teacher/TeacherQuizBuilder';
import TeacherBorrowedBooksPage from '../pages/teacher/TeacherBorrowedBooksPage';

// Librarian Pages
import LibrarianDashboard from '../pages/librarian/LibrarianDashboard';
import LibrarianIssueReturn from '../pages/librarian/LibrarianIssueReturn';
import LibrarianBorrowHistory from '../pages/librarian/LibrarianBorrowHistory';
import LibrarianBookInventory from '../pages/librarian/LibrarianBookInventory';

// Dynamic Role-Based Redirect for '/dashboard'
const RoleDashboardRedirect = () => {
  const { user } = useSelector((state) => state.auth);
  if (!user) return <Navigate to="/login" replace />;
  const roles = user.roles || (user.role ? [user.role] : []);
  const rStr = JSON.stringify(roles).toUpperCase();

  if (rStr.includes('ADMIN')) return <Navigate to="/admin/dashboard" replace />;
  if (rStr.includes('TEACHER')) return <Navigate to="/teacher/dashboard" replace />;
  if (rStr.includes('LIBRARIAN')) return <Navigate to="/librarian/dashboard" replace />;
  return <Navigate to="/student/dashboard" replace />;
};

const AppRoutes = () => {
  return (
    <Routes>
      {/* Landing Page & Auth Routes */}
      <Route path="/" element={<LandingPage initialForm="login" />} />
      <Route path="/login" element={<LandingPage initialForm="login" />} />
      <Route path="/register" element={<LandingPage initialForm="register" />} />
      <Route path="/forgot-password" element={<LandingPage initialForm="forgot" />} />
      <Route path="/reset-password" element={<LandingPage initialForm="reset" />} />

      {/* Protected Dashboards & Role Portals */}
      <Route element={<DashboardLayout />}>
        {/* Dynamic Dashboard Route */}
        <Route path="/dashboard" element={<RoleDashboardRedirect />} />

        {/* Student Portal */}
        <Route path="/student/dashboard" element={<StudentDashboard />} />
        <Route path="/student/courses" element={<StudentCoursesPage />} />
        <Route path="/student/books" element={<StudentBooksPage />} />
        <Route path="/student/borrowed-books" element={<StudentBorrowedBooksPage />} />
        <Route path="/student/quizzes" element={<StudentQuizzesPage />} />
        <Route path="/student/ai-search" element={<AISearchPage />} />
        <Route path="/exam/take/:quizId" element={<TakeQuizPage />} />

        {/* Backward Compatibility Aliases */}
        <Route path="/books" element={<StudentBooksPage />} />
        <Route path="/courses" element={<StudentCoursesPage />} />
        <Route path="/quizzes" element={<StudentQuizzesPage />} />
        <Route path="/ai-search" element={<AISearchPage />} />

        {/* Admin Console */}
        <Route path="/admin/dashboard" element={<AdminDashboard />} />
        <Route path="/admin/users" element={<AdminUserManagement />} />
        <Route path="/admin/categories" element={<AdminCategoryManagement />} />

        {/* Teacher Studio */}
        <Route path="/teacher/dashboard" element={<TeacherDashboard />} />
        <Route path="/teacher/books" element={<StudentBooksPage />} />
        <Route path="/teacher/question-bank" element={<TeacherQuestionBank />} />
        <Route path="/teacher/quiz-builder" element={<TeacherQuizBuilder />} />
        <Route path="/teacher/borrowed-books" element={<TeacherBorrowedBooksPage />} />

        {/* Librarian Desk */}
        <Route path="/librarian/dashboard" element={<LibrarianDashboard />} />
        <Route path="/librarian/issue-return" element={<LibrarianIssueReturn />} />
        <Route path="/librarian/borrow-history" element={<LibrarianBorrowHistory />} />
        <Route path="/librarian/books/manage" element={<LibrarianBookInventory />} />
      </Route>

      {/* Fallback Route */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default AppRoutes;
