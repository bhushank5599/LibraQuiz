import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Library, BookOpen, History, Key } from 'lucide-react';
import { useSelector } from 'react-redux';
import axiosClient from '../../api/axiosClient';
import ChangePasswordModal from '../../components/common/ChangePasswordModal';

const LibrarianDashboard = () => {
  const { user } = useSelector((state) => state.auth);
  const [bookCount, setBookCount] = useState(20);
  const [activeIssuedCount, setActiveIssuedCount] = useState(0);
  const [showChangePassModal, setShowChangePassModal] = useState(false);

  const isLegacySeedTxn = (t) => {
    if (!t) return false;
    const idNum = Number(t.id);
    return idNum === 101 || idNum === 102 || idNum === 103 || idNum === 104;
  };

  const fetchDashboardStats = async () => {
    try {
      const res = await axiosClient.get('/books');
      if (res.data && Array.isArray(res.data) && res.data.length > 0) {
        setBookCount(res.data.length);
      }
    } catch (err) {}

    // Calculate real-time active issued loans across all students & teachers
    const savedTxns = JSON.parse(localStorage.getItem('lq_issued_transactions') || '[]');
    const realTxns = savedTxns.filter(t => !isLegacySeedTxn(t));
    if (realTxns.length !== savedTxns.length) {
      localStorage.setItem('lq_issued_transactions', JSON.stringify(realTxns));
    }
    const activeCount = realTxns.filter(t => (t.status || '').toUpperCase() === 'ISSUED' || (t.status || '').toUpperCase() === 'BORROWED').length;
    setActiveIssuedCount(activeCount);
  };

  useEffect(() => {
    fetchDashboardStats();
    window.addEventListener('lq_circulation_updated', fetchDashboardStats);
    window.addEventListener('storage', fetchDashboardStats);
    return () => {
      window.removeEventListener('lq_circulation_updated', fetchDashboardStats);
      window.removeEventListener('storage', fetchDashboardStats);
    };
  }, []);

  // Calculate real-time total catalog books count (20 fallback + custom added)
  const customAdded = JSON.parse(localStorage.getItem('lq_custom_added_books') || '[]');
  const fallbackCount = 20;
  const catalogBooksCount = Math.max(fallbackCount + customAdded.length, bookCount || 0);
  const totalPhysicalCopies = catalogBooksCount * 5;

  return (
    <div>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <div className="badge badge-info" style={{ marginBottom: '8px' }}>
            📚 Librarian Circulation Desk
          </div>
          <h1 style={{ fontSize: '2.2rem', fontWeight: 800 }}>Librarian Dashboard</h1>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem', marginTop: '4px' }}>
            Monitor active book loans, process issue and return transactions, and manage physical book copies.
          </p>
        </div>

      </div>

      {/* Metrics Row */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(260px, 1fr))', gap: '20px', marginBottom: '36px' }}>
        <Link to="/librarian/borrow-history" style={{ textDecoration: 'none', color: 'inherit' }}>
          <div className="glass-panel" style={{ padding: '24px', cursor: 'pointer' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
              <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', fontWeight: 600 }}>Active Book Loans</span>
              <div style={{ background: 'rgba(99, 102, 241, 0.15)', padding: '10px', borderRadius: '10px' }}>
                <Library color="var(--primary-500)" size={22} />
              </div>
            </div>
            <h2 style={{ fontSize: '2rem', fontWeight: 800, color: '#fff' }}>{activeIssuedCount}</h2>
            <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Overall Student & Teacher Loans</span>
          </div>
        </Link>

        <Link to="/librarian/books/manage" style={{ textDecoration: 'none', color: 'inherit' }}>
          <div className="glass-panel" style={{ padding: '24px', cursor: 'pointer' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
              <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', fontWeight: 600 }}>Total Book Catalog</span>
              <div style={{ background: 'rgba(16, 185, 129, 0.15)', padding: '10px', borderRadius: '10px' }}>
                <BookOpen color="var(--emerald-500)" size={22} />
              </div>
            </div>
            <h2 style={{ fontSize: '2rem', fontWeight: 800, color: '#fff' }}>{catalogBooksCount} Books</h2>
          </div>
        </Link>
      </div>

      {/* Quick Action Navigation Grid */}
      <h3 style={{ fontSize: '1.3rem', fontWeight: 800, marginBottom: '20px' }}>Librarian Circulation Operations</h3>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '24px', marginBottom: '40px' }}>
        
        <Link to="/librarian/issue-return" className="glass-panel" style={{ padding: '24px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '12px' }}>
            <div style={{ background: 'rgba(99, 102, 241, 0.2)', padding: '12px', borderRadius: '12px' }}>
              <Library color="var(--primary-500)" size={24} />
            </div>
            <div>
              <h4 style={{ fontSize: '1.1rem', fontWeight: 700 }}>Issue & Return Counter</h4>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Check out books to students, scan copy codes, and record returns.</p>
            </div>
          </div>
          <span style={{ color: 'var(--primary-500)', fontWeight: 600, fontSize: '0.875rem' }}>Open Circulation Desk &rarr;</span>
        </Link>

        <Link to="/librarian/borrow-history" className="glass-panel" style={{ padding: '24px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '12px' }}>
            <div style={{ background: 'rgba(245, 158, 11, 0.2)', padding: '12px', borderRadius: '12px' }}>
              <History color="var(--amber-500)" size={24} />
            </div>
            <div>
              <h4 style={{ fontSize: '1.1rem', fontWeight: 700 }}>Borrowed History Logs</h4>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>View complete audit logs of book loans for students and teachers, with return dates.</p>
            </div>
          </div>
          <span style={{ color: 'var(--amber-500)', fontWeight: 600, fontSize: '0.875rem' }}>View Borrow History &rarr;</span>
        </Link>

        <Link to="/librarian/books/manage" className="glass-panel" style={{ padding: '24px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '12px' }}>
            <div style={{ background: 'rgba(16, 185, 129, 0.2)', padding: '12px', borderRadius: '12px' }}>
              <BookOpen color="var(--emerald-500)" size={24} />
            </div>
            <div>
              <h4 style={{ fontSize: '1.1rem', fontWeight: 700 }}>Book & Copy Inventory</h4>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Add new books, manage barcodes, update copy conditions and status.</p>
            </div>
          </div>
          <span style={{ color: 'var(--emerald-500)', fontWeight: 600, fontSize: '0.875rem' }}>Manage Book Copies &rarr;</span>
        </Link>

      </div>
    </div>
  );
};

export default LibrarianDashboard;
