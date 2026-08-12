import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { fetchBooks } from '../../features/books/bookSlice';
import { fetchPublishedQuizzes } from '../../features/quizzes/quizSlice';
import { Book, HelpCircle, ArrowRight, Key } from 'lucide-react';
import { Link } from 'react-router-dom';
import ChangePasswordModal from '../../components/common/ChangePasswordModal';

const StudentDashboard = () => {
  const dispatch = useDispatch();
  const { user } = useSelector((state) => state.auth);
  const { books } = useSelector((state) => state.books);
  const { publishedQuizzes } = useSelector((state) => state.quizzes);
  const [showChangePassModal, setShowChangePassModal] = useState(false);

  useEffect(() => {
    dispatch(fetchBooks());
    dispatch(fetchPublishedQuizzes());
  }, [dispatch]);

  // Calculate total books in library catalog accurately
  const customAdded = JSON.parse(localStorage.getItem('lq_custom_added_books') || '[]');
  const fallbackCount = 20;
  const totalLibraryBooks = Math.max(fallbackCount + customAdded.length, books?.length || 0);

  // Calculate total active published quizzes dynamically
  const getActiveQuizzesCount = () => {
    try {
      let stored = JSON.parse(localStorage.getItem('lq_quizzes') || '[]');

      // Purge default mock seed quiz if present
      stored = stored.filter(q => q && q.id !== 1 && q.id !== 'java-assessment-1' && q.title !== 'Java Fundamentals Assessment');
      localStorage.setItem('lq_quizzes', JSON.stringify(stored));

      const deletedList = JSON.parse(localStorage.getItem('lq_deleted_quizzes') || '[]');

      const isDeleted = (q) => {
        if (!q) return true;
        const idStr = String(q.id || '');
        return deletedList.some(d => d !== null && d !== undefined && d !== '' && String(d) === idStr);
      };

      const isPublished = (q) => {
        if (!q) return false;
        return q.isPublished === true || q.isPublished === 'true' || q.published === true || q.status === 'PUBLISHED';
      };

      let activeStored = stored.filter(q => isPublished(q) && !isDeleted(q));

      const apiPublished = (publishedQuizzes || []).filter(q => !isDeleted(q));
      const combined = [...activeStored, ...apiPublished];

      const uniqueKeys = new Set();
      combined.forEach(q => {
        const key = q.id || q.title;
        if (key) uniqueKeys.add(key);
      });

      return uniqueKeys.size;
    } catch (e) {
      return (publishedQuizzes || []).length || 1;
    }
  };

  const totalQuizzesCount = getActiveQuizzesCount();

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '28px' }}>
      {/* Header Banner */}
      <div className="glass-panel" style={{ padding: '32px', background: 'linear-gradient(135deg, rgba(99, 102, 241, 0.2), rgba(236, 72, 153, 0.2))', border: '1px solid rgba(99, 102, 241, 0.3)' }}>
        <h1 style={{ fontSize: '2rem', fontWeight: 800 }}>Welcome back, {user?.username || 'Student'}! 👋</h1>
        <p style={{ color: 'var(--text-secondary)', marginTop: '8px', maxWidth: '600px' }}>
          Explore digital books in the Library catalog or attempt online quizzes & examinations.
        </p>
      </div>

      {/* Overview Stat Cards */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(260px, 1fr))', gap: '20px' }}>
        <Link to="/student/books" style={{ textDecoration: 'none', color: 'inherit' }}>
          <div className="glass-panel" style={{ padding: '24px', display: 'flex', alignItems: 'center', gap: '16px', cursor: 'pointer' }}>
            <div style={{ background: 'rgba(99, 102, 241, 0.2)', padding: '14px', borderRadius: '12px', color: '#6366f1' }}>
              <Book size={28} />
            </div>
            <div>
              <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', fontWeight: 600 }}>Library Catalog</div>
              <div style={{ fontSize: '1.8rem', fontWeight: 800, color: '#fff' }}>{totalLibraryBooks} Books</div>
            </div>
          </div>
        </Link>

        <Link to="/student/quizzes" style={{ textDecoration: 'none', color: 'inherit' }}>
          <div className="glass-panel" style={{ padding: '24px', display: 'flex', alignItems: 'center', gap: '16px', cursor: 'pointer' }}>
            <div style={{ background: 'rgba(245, 158, 11, 0.2)', padding: '14px', borderRadius: '12px', color: '#f59e0b' }}>
              <HelpCircle size={28} />
            </div>
            <div>
              <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', fontWeight: 600 }}>Available Quizzes</div>
              <div style={{ fontSize: '1.8rem', fontWeight: 800, color: '#fff' }}>
                {totalQuizzesCount} {totalQuizzesCount === 1 ? 'Quiz' : 'Quizzes'}
              </div>
            </div>
          </div>
        </Link>
      </div>
    </div>
  );
};

export default StudentDashboard;
