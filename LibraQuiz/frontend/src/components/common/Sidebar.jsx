import React from 'react';
import { NavLink } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { 
  LayoutDashboard, 
  Book, 
  Library, 
  GraduationCap, 
  HelpCircle, 
  CheckSquare, 
  Bot, 
  Users, 
  Layers,
  FolderTree,
  RotateCcw,
  BookOpen,
  BookmarkCheck,
  History
} from 'lucide-react';

const Sidebar = () => {
  const { user } = useSelector((state) => state.auth);
  const roles = user?.roles || (user?.role ? [user.role] : []);

  const isAdmin = roles.includes('ADMIN') || roles.includes('ROLE_ADMIN');
  const isLibrarian = roles.includes('LIBRARIAN') || roles.includes('ROLE_LIBRARIAN');
  const isTeacher = roles.includes('TEACHER') || roles.includes('ROLE_TEACHER');

  return (
    <aside className="glass-panel" style={{ width: '260px', borderRadius: 0, borderTop: 0, padding: '24px 16px', display: 'flex', flexDirection: 'column', gap: '8px' }}>
      
      {/* Admin Menu Section */}
      {isAdmin && (
        <>
          <div style={{ padding: '8px 12px 4px', fontSize: '0.75rem', fontWeight: 700, color: 'var(--rose-500)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
            🛡️ Admin Console
          </div>
          <NavLink to="/admin/dashboard" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <LayoutDashboard size={18} /> Admin Overview
          </NavLink>
          <NavLink to="/admin/users" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <Users size={18} /> User Management
          </NavLink>
        </>
      )}

      {/* Teacher Menu Section */}
      {isTeacher && (
        <>
          <div style={{ padding: '16px 12px 4px', fontSize: '0.75rem', fontWeight: 700, color: 'var(--amber-500)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
            👨‍🏫 Teacher Studio
          </div>
          <NavLink to="/teacher/dashboard" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <LayoutDashboard size={18} /> Teacher Dashboard
          </NavLink>
          <NavLink to="/teacher/books" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <Book size={18} /> Library
          </NavLink>
          <NavLink to="/teacher/question-bank" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <Layers size={18} /> Question Bank
          </NavLink>
          <NavLink to="/teacher/quiz-builder" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <HelpCircle size={18} /> Quiz Builder
          </NavLink>
          <NavLink to="/teacher/borrowed-books" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <BookmarkCheck size={18} /> Books Borrowed
          </NavLink>
        </>
      )}

      {/* Librarian Menu Section */}
      {isLibrarian && (
        <>
          <div style={{ padding: '16px 12px 4px', fontSize: '0.75rem', fontWeight: 700, color: 'var(--emerald-500)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
            📚 Librarian Desk
          </div>
          <NavLink to="/librarian/dashboard" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <LayoutDashboard size={18} /> Librarian Dashboard
          </NavLink>
          <NavLink to="/librarian/issue-return" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <RotateCcw size={18} /> Issue & Return Desk
          </NavLink>
          <NavLink to="/librarian/borrow-history" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <History size={18} /> Borrowed History
          </NavLink>
          <NavLink to="/librarian/books/manage" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <BookOpen size={18} /> Book Inventory
          </NavLink>
        </>
      )}

      {/* Student Navigation Section */}
      {!isAdmin && !isTeacher && !isLibrarian && (
        <>
          <div style={{ padding: '16px 12px 4px', fontSize: '0.75rem', fontWeight: 700, color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
            🎓 Student Hub
          </div>

          <NavLink to="/student/dashboard" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <LayoutDashboard size={18} /> Student Dashboard
          </NavLink>

          <NavLink to="/student/books" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <Book size={18} /> Library
          </NavLink>

          <NavLink to="/student/borrowed-books" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <BookmarkCheck size={18} /> Books Borrowed
          </NavLink>

          <NavLink to="/student/quizzes" className={({ isActive }) => `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`} style={{ justifyContent: 'flex-start' }}>
            <HelpCircle size={18} /> Quizzes & Exams
          </NavLink>
        </>
      )}

    </aside>
  );
};

export default Sidebar;
