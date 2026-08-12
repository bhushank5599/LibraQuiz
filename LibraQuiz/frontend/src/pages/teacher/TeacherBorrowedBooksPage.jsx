import React, { useState, useEffect } from 'react';
import { BookOpen, Clock, Calendar, CheckCircle2, AlertCircle, RefreshCw, BookmarkCheck, Shield, GraduationCap } from 'lucide-react';
import { useSelector } from 'react-redux';
import axiosClient from '../../api/axiosClient';

const TeacherBorrowedBooksPage = () => {
  const { user } = useSelector((state) => state.auth);
  const [borrowedBooks, setBorrowedBooks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [renewingId, setRenewingId] = useState(null);

  // Normalize code digits for matching
  const normalizeDigits = (str) => (str || '').replace(/[^0-9]/g, '');

  const fetchBorrowedBooks = async () => {
    setIsLoading(true);
    try {
      const teacherId = user?.id;
      const teacherUsername = (user?.username || '').toUpperCase();
      const teacherRoleCode = (user?.uniqueId || '').toUpperCase();

      // 1. Fetch real books catalog from BookService
      let allBooks = [];
      try {
        const booksRes = await axiosClient.get('/books');
        if (booksRes.data && Array.isArray(booksRes.data) && booksRes.data.length > 0) {
          allBooks = booksRes.data;
        }
      } catch (e) {
        console.log('BookService fetch check');
      }

      // 2. Fetch active transactions from backend microservice
      let activeTxns = [];
      if (teacherId) {
        try {
          const res = await axiosClient.get(`/transactions/user/${teacherId}/active`);
          if (res.data && Array.isArray(res.data) && res.data.length > 0) {
            activeTxns = res.data;
          }
        } catch (e) {}
      }

      // 3. Read shared circulation storage
      const storedTxns = JSON.parse(localStorage.getItem('lq_issued_transactions') || '[]');

      // Filter active non-returned transactions matching THIS teacher
      const activeStoredTxns = storedTxns.filter(t => t.status === 'ISSUED' || t.status === 'OVERDUE');

      const myTransactions = activeStoredTxns.filter(t => {
        const tUser = String(t.userId || '');
        const tCode = (t.studentCode || '').toUpperCase().trim();

        if (!tCode && !tUser) return false;

        // Match by Teacher Username (e.g. "AVINASH1965", "MAHESH2003")
        const matchUsername = teacherUsername && (tCode === teacherUsername || tCode.includes(teacherUsername) || teacherUsername.includes(tCode));

        // Match by Teacher Unique ID (e.g. "TCH-0009", "TCH-0011")
        const matchRoleCode = teacherRoleCode && (tCode === teacherRoleCode || tCode.includes(teacherRoleCode));

        // Match by User ID (e.g. 9 or 11)
        const matchId = teacherId && (tUser === String(teacherId) || (tCode.startsWith('TCH-') && parseInt(tCode.replace(/[^0-9]/g, ''), 10) === Number(teacherId)));

        return Boolean(matchUsername || matchRoleCode || matchId);
      });

      const combined = [...activeTxns, ...myTransactions];
      
      const uniqueMap = new Map();
      combined.forEach(t => {
        if (t.copyCode && !uniqueMap.has(t.copyCode)) {
          uniqueMap.set(t.copyCode, t);
        }
      });

      const finalRecords = Array.from(uniqueMap.values()).map((t, idx) => {
        const cDigits = normalizeDigits(t.copyCode);
        
        const matchedBook = allBooks.find(b => {
          const bDigits = normalizeDigits(b.isbn);
          return bDigits && cDigits.includes(bDigits);
        }) || (allBooks.length > 0 ? allBooks[idx % allBooks.length] : null);

        return {
          id: t.id || idx + 1,
          copyCode: t.copyCode,
          bookTitle: matchedBook ? matchedBook.title : (t.bookTitle || 'Faculty Reference Book'),
          authorName: matchedBook ? matchedBook.authorName : (t.authorName || 'Academic Author'),
          issueDate: t.issueDate || new Date().toISOString().split('T')[0],
          dueDate: t.dueDate || '2026-08-15',
          status: t.status || 'ISSUED',
          fineAmount: 0.00
        };
      });

      setBorrowedBooks(finalRecords);
    } catch (err) {
      console.error('Error loading teacher borrowed books:', err);
      setBorrowedBooks([]);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchBorrowedBooks();
  }, [user]);

  // Calculate days remaining until due date
  const getDaysRemaining = (dueDateStr) => {
    if (!dueDateStr) return 7;
    const due = new Date(dueDateStr);
    const today = new Date();
    const diffTime = due.getTime() - today.getTime();
    return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  };

  // Renew loan by 7 days
  const handleRenewLoan = async (borrowId) => {
    setRenewingId(borrowId);
    try {
      await axiosClient.post(`/transactions/renew/${borrowId}`);
    } catch (e) {
      console.log('Local renew fallback');
    }

    setBorrowedBooks(prev => prev.map(b => {
      if (b.id === borrowId) {
        const currentDue = new Date(b.dueDate || Date.now());
        currentDue.setDate(currentDue.getDate() + 7);
        const updatedDueStr = currentDue.toISOString().split('T')[0];

        const storedTxns = JSON.parse(localStorage.getItem('lq_issued_transactions') || '[]');
        const updatedTxns = storedTxns.map(t => t.copyCode === b.copyCode ? { ...t, dueDate: updatedDueStr } : t);
        localStorage.setItem('lq_issued_transactions', JSON.stringify(updatedTxns));

        return { ...b, dueDate: updatedDueStr };
      }
      return b;
    }));

    setRenewingId(null);
  };

  const activeCount = borrowedBooks.filter(b => b.status !== 'RETURNED').length;

  return (
    <div>
      {/* Page Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <div className="badge badge-warning" style={{ marginBottom: '8px' }}>
            👨‍🏫 Faculty Library Desk
          </div>
          <h1 style={{ fontSize: '2rem', fontWeight: 800 }}>Teacher Books Borrowed</h1>
        </div>
        <button className="btn btn-secondary" onClick={fetchBorrowedBooks} disabled={isLoading}>
          <RefreshCw size={16} className={isLoading ? 'floating-card' : ''} /> {isLoading ? 'Refreshing...' : 'Refresh'}
        </button>
      </div>

      {/* Overview Stat Cards */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))', gap: '20px', marginBottom: '32px' }}>
        <div className="glass-panel" style={{ padding: '20px', display: 'flex', alignItems: 'center', gap: '16px' }}>
          <div style={{ background: 'rgba(245, 158, 11, 0.2)', padding: '12px', borderRadius: '12px', color: 'var(--amber-500)' }}>
            <BookmarkCheck size={28} />
          </div>
          <div>
            <div style={{ fontSize: '1.6rem', fontWeight: 800, color: '#fff' }}>{activeCount}</div>
            <div style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Books Currently Borrowed</div>
          </div>
        </div>

        <div className="glass-panel" style={{ padding: '20px', display: 'flex', alignItems: 'center', gap: '16px' }}>
          <div style={{ background: 'rgba(99, 102, 241, 0.2)', padding: '12px', borderRadius: '12px', color: 'var(--primary-500)' }}>
            <Shield size={28} />
          </div>
          <div>
            <div style={{ fontSize: '1.6rem', fontWeight: 800, color: '#fff' }}>₹0.00</div>
            <div style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Overdue Fines</div>
          </div>
        </div>
      </div>

      {/* Borrowed Books Section */}
      <h3 style={{ fontSize: '1.25rem', fontWeight: 800, marginBottom: '20px', color: '#fff' }}>Currently Issued Faculty Books ({borrowedBooks.length})</h3>

      {isLoading ? (
        <div style={{ textAlign: 'center', padding: '40px', color: 'var(--text-secondary)' }}>
          Syncing your faculty borrowed book records from Librarian Desk...
        </div>
      ) : borrowedBooks.length === 0 ? (
        <div className="glass-panel" style={{ padding: '48px', textAlign: 'center', color: 'var(--text-secondary)' }}>
          <BookOpen size={52} style={{ margin: '0 auto 16px', opacity: 0.4, color: 'var(--amber-500)' }} />
          <h4 style={{ fontSize: '1.2rem', fontWeight: 800, color: '#fff' }}>No Active Faculty Book Loans</h4>
        </div>
      ) : (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(340px, 1fr))', gap: '24px' }}>
          {borrowedBooks.map((b) => {
            const daysLeft = getDaysRemaining(b.dueDate);
            const isOverdue = daysLeft < 0;

            return (
              <div key={b.id} className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', justifyContent: 'space-between', border: isOverdue ? '1px solid rgba(244, 63, 94, 0.4)' : '1px solid var(--border-color)' }}>
                <div>
                  {/* Top Barcode Badge & Status */}
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '14px' }}>
                    <span style={{ fontFamily: 'monospace', fontWeight: 800, fontSize: '0.85rem', color: '#fcd34d', background: 'rgba(245, 158, 11, 0.15)', padding: '4px 10px', borderRadius: '6px', border: '1px solid rgba(245, 158, 11, 0.3)' }}>
                      {b.copyCode}
                    </span>
                    
                    {isOverdue ? (
                      <span className="badge badge-danger"><AlertCircle size={12} /> OVERDUE</span>
                    ) : (
                      <span className="badge badge-success"><CheckCircle2 size={12} /> ACTIVE LOAN</span>
                    )}
                  </div>

                  {/* Title & Author */}
                  <h3 style={{ fontSize: '1.2rem', fontWeight: 800, color: '#fff', marginBottom: '6px' }}>{b.bookTitle}</h3>
                  <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', marginBottom: '20px' }}>Author: {b.authorName}</p>

                  {/* Dates & Timeline Box */}
                  <div style={{ background: 'rgba(255,255,255,0.03)', borderRadius: '10px', padding: '14px', marginBottom: '20px', border: '1px solid rgba(255,255,255,0.05)' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.825rem', marginBottom: '8px' }}>
                      <span style={{ color: 'var(--text-muted)' }}>Issued Date:</span>
                      <span style={{ color: '#fff', fontWeight: 600 }}>{b.issueDate}</span>
                    </div>

                    <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.825rem', marginBottom: '10px' }}>
                      <span style={{ color: 'var(--text-muted)' }}>Return Due Date:</span>
                      <span style={{ color: isOverdue ? '#f43f5e' : '#10b981', fontWeight: 800 }}>{b.dueDate}</span>
                    </div>

                    <div style={{ fontSize: '0.78rem', padding: '6px 10px', borderRadius: '6px', background: isOverdue ? 'rgba(244, 63, 94, 0.15)' : 'rgba(16, 185, 129, 0.15)', color: isOverdue ? '#f43f5e' : '#10b981', fontWeight: 700, textAlign: 'center' }}>
                      {isOverdue ? `Overdue by ${Math.abs(daysLeft)} Days` : `${daysLeft} Days Remaining to Return`}
                    </div>
                  </div>
                </div>

                {/* Footer Action */}
                <div style={{ borderTop: '1px solid var(--border-color)', paddingTop: '14px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Issued by Librarian Desk</span>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default TeacherBorrowedBooksPage;
