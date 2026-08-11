import React, { useState, useEffect } from 'react';
import { History, Search, BookOpen, Clock, CheckCircle2, AlertCircle, RefreshCw, User, Shield } from 'lucide-react';
import axiosClient from '../../api/axiosClient';

const LibrarianBorrowHistory = () => {
  const [historyList, setHistoryList] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterTab, setFilterTab] = useState('ALL'); // 'ALL' | 'ISSUED' | 'RETURNED'
  const [isLoading, setIsLoading] = useState(true);



  const isLegacySeedTxn = (t) => {
    if (!t) return false;
    const idNum = Number(t.id);
    return idNum === 101 || idNum === 102 || idNum === 103 || idNum === 104;
  };

  const fetchBorrowHistory = async () => {
    setIsLoading(true);
    try {
      // Purge legacy seed records from persistent localStorage
      const rawStored = JSON.parse(localStorage.getItem('lq_issued_transactions') || '[]');
      const realStored = rawStored.filter(t => !isLegacySeedTxn(t));
      if (realStored.length !== rawStored.length) {
        localStorage.setItem('lq_issued_transactions', JSON.stringify(realStored));
      }

      // Fetch backend transaction history if available
      let backendTxns = [];
      try {
        const res = await axiosClient.get('/transactions/all');
        if (res.data && Array.isArray(res.data) && res.data.length > 0) {
          backendTxns = res.data.filter(t => !isLegacySeedTxn(t));
        }
      } catch (e) {
        console.log('Backend history fetch check');
      }

      const combinedRaw = [...realStored, ...backendTxns];

      // Clean & normalize records
      const uniqueMap = new Map();
      combinedRaw.forEach(t => {
        if (!t) return;
        const key = t.id || `${t.copyCode}_${t.issueDate}_${t.studentCode || t.userId || t.borrowerName}`;
        if (!uniqueMap.has(key)) {
          const rawCode = (t.studentCode || t.student || t.borrowerRole || 'USER').toUpperCase();
          const isTeacher = rawCode.includes('TCH') || rawCode.includes('TEACHER') || t.borrowerRole === 'Teacher';
          
          let cleanUsername = t.borrowerName || t.studentCode || 'User';
          cleanUsername = cleanUsername.replace(/^Student\s*\(/i, '').replace(/^Teacher\s*\(/i, '').replace(/\)$/, '');

          const todayStr = new Date().toISOString().split('T')[0];
          const resolvedReturnDate = t.status === 'RETURNED'
            ? (t.returnDate && t.returnDate !== t.dueDate ? t.returnDate : (t.issueDate || todayStr))
            : null;

          uniqueMap.set(key, {
            id: t.id || Math.random(),
            copyCode: t.copyCode || 'BC-101-1',
            bookTitle: t.bookTitle || 'Library Reference Book',
            authorName: t.authorName || 'Academic Author',
            borrowerName: cleanUsername,
            borrowerRole: isTeacher ? 'Teacher' : 'Student',
            issueDate: t.issueDate || todayStr,
            dueDate: t.dueDate || todayStr,
            returnDate: resolvedReturnDate,
            status: t.status || 'ISSUED'
          });
        }
      });

      setHistoryList(Array.from(uniqueMap.values()));
    } catch (err) {
      console.error('Error fetching borrow history:', err);
      setHistoryList([]);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchBorrowHistory();
    window.addEventListener('lq_circulation_updated', fetchBorrowHistory);
    window.addEventListener('storage', fetchBorrowHistory);
    return () => {
      window.removeEventListener('lq_circulation_updated', fetchBorrowHistory);
      window.removeEventListener('storage', fetchBorrowHistory);
    };
  }, []);

  // Filter list by search term & tab
  const filteredList = historyList.filter(item => {
    const matchesSearch = 
      (item.borrowerName || '').toLowerCase().includes(searchTerm.toLowerCase()) ||
      (item.bookTitle || '').toLowerCase().includes(searchTerm.toLowerCase()) ||
      (item.copyCode || '').toLowerCase().includes(searchTerm.toLowerCase()) ||
      (item.authorName || '').toLowerCase().includes(searchTerm.toLowerCase());

    if (!matchesSearch) return false;

    if (filterTab === 'ISSUED') return item.status === 'ISSUED';
    if (filterTab === 'RETURNED') return item.status === 'RETURNED';
    return true;
  });

  const totalCount = historyList.length;
  const activeLoansCount = historyList.filter(i => i.status === 'ISSUED').length;
  const returnedCount = historyList.filter(i => i.status === 'RETURNED').length;

  return (
    <div>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <div className="badge badge-info" style={{ marginBottom: '8px' }}>
            📚 Circulation Audit Trail
          </div>
          <h1 style={{ fontSize: '2rem', fontWeight: 800 }}>Borrowed History</h1>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', marginTop: '4px' }}>
            Complete circulation history log showing book loans, borrower profiles (Students & Teachers), issue dates, return due dates, and actual return dates.
          </p>
        </div>

        <button className="btn btn-secondary" onClick={fetchBorrowHistory} disabled={isLoading}>
          <RefreshCw size={16} className={isLoading ? 'floating-card' : ''} /> {isLoading ? 'Refreshing...' : 'Refresh'}
        </button>
      </div>

      {/* Metrics Row */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))', gap: '20px', marginBottom: '32px' }}>


        <div className="glass-panel" style={{ padding: '20px', display: 'flex', alignItems: 'center', gap: '16px' }}>
          <div style={{ background: 'rgba(245, 158, 11, 0.2)', padding: '12px', borderRadius: '12px', color: 'var(--amber-500)' }}>
            <Clock size={26} />
          </div>
          <div>
            <div style={{ fontSize: '1.6rem', fontWeight: 800, color: '#fff' }}>{activeLoansCount}</div>
            <div style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Currently Borrowed Books</div>
          </div>
        </div>

        <div className="glass-panel" style={{ padding: '20px', display: 'flex', alignItems: 'center', gap: '16px' }}>
          <div style={{ background: 'rgba(16, 185, 129, 0.2)', padding: '12px', borderRadius: '12px', color: 'var(--emerald-500)' }}>
            <CheckCircle2 size={26} />
          </div>
          <div>
            <div style={{ fontSize: '1.6rem', fontWeight: 800, color: '#fff' }}>{returnedCount}</div>
            <div style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Successfully Returned</div>
          </div>
        </div>
      </div>

      {/* Search & Filter Bar */}
      <div className="glass-panel" style={{ padding: '20px', marginBottom: '24px', display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '16px' }}>
        {/* Search */}
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px', flex: 1, minWidth: '280px', background: 'rgba(15,23,42,0.6)', padding: '8px 16px', borderRadius: '10px', border: '1px solid var(--border-color)' }}>
          <Search size={18} color="var(--text-muted)" />
          <input 
            type="text" 
            className="form-input" 
            placeholder="Search by student/teacher username, book title, or barcode copy code (e.g. BC-101-1)..." 
            style={{ width: '100%', border: 'none', background: 'transparent' }}
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        {/* Filter Tabs */}
        <div style={{ display: 'flex', gap: '8px', background: 'rgba(15,23,42,0.6)', padding: '4px', borderRadius: '10px', border: '1px solid var(--border-color)' }}>
          <button 
            className={`btn ${filterTab === 'ALL' ? 'btn-primary' : 'btn-secondary'}`} 
            style={{ padding: '6px 14px', fontSize: '0.825rem' }}
            onClick={() => setFilterTab('ALL')}
          >
            All History ({totalCount})
          </button>
          <button 
            className={`btn ${filterTab === 'ISSUED' ? 'btn-primary' : 'btn-secondary'}`} 
            style={{ padding: '6px 14px', fontSize: '0.825rem' }}
            onClick={() => setFilterTab('ISSUED')}
          >
            Currently Borrowed ({activeLoansCount})
          </button>
          <button 
            className={`btn ${filterTab === 'RETURNED' ? 'btn-primary' : 'btn-secondary'}`} 
            style={{ padding: '6px 14px', fontSize: '0.825rem' }}
            onClick={() => setFilterTab('RETURNED')}
          >
            Returned ({returnedCount})
          </button>
        </div>
      </div>

      {/* Circulation History Table */}
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.2rem', fontWeight: 800, color: '#fff', marginBottom: '18px' }}>
          Borrowed Audit Records ({filteredList.length})
        </h3>

        {isLoading ? (
          <div style={{ textAlign: 'center', padding: '40px', color: 'var(--text-secondary)' }}>
            Loading borrowing history records...
          </div>
        ) : filteredList.length === 0 ? (
          <div style={{ textAlign: 'center', padding: '48px', color: 'var(--text-secondary)' }}>
            <History size={48} style={{ margin: '0 auto 12px', opacity: 0.4, color: 'var(--primary-500)' }} />
            <h4 style={{ fontSize: '1.1rem', fontWeight: 800, color: '#fff', marginBottom: '4px' }}>No Borrow History Found</h4>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>
              No loan records match your current filter search.
            </p>
          </div>
        ) : (
          <div style={{ overflowX: 'auto' }}>
            <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left', fontSize: '0.875rem' }}>
              <thead>
                <tr style={{ borderBottom: '1px solid rgba(255,255,255,0.1)', color: 'var(--text-muted)' }}>
                  <th style={{ padding: '14px 12px' }}>Borrower Username & Role</th>
                  <th style={{ padding: '14px 12px' }}>Book Title & Barcode Copy</th>
                  <th style={{ padding: '14px 12px' }}>Issue Date</th>
                  <th style={{ padding: '14px 12px' }}>Due Date</th>
                  <th style={{ padding: '14px 12px' }}>Return Date</th>
                  <th style={{ padding: '14px 12px' }}>Circulation Status</th>
                </tr>
              </thead>
              <tbody>
                {filteredList.map((row) => {
                  const isReturned = row.status === 'RETURNED';
                  const isTeacher = row.borrowerRole === 'Teacher';

                  return (
                    <tr key={row.id} style={{ borderBottom: '1px solid rgba(255,255,255,0.04)' }}>
                      {/* Borrower Name & Role */}
                      <td style={{ padding: '16px 12px' }}>
                        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                          <span style={{ fontWeight: 700, color: '#fff', fontSize: '0.925rem' }}>👤 {row.borrowerName}</span>
                          <span className={`badge ${isTeacher ? 'badge-warning' : 'badge-info'}`} style={{ fontSize: '0.725rem' }}>
                            {isTeacher ? 'TEACHER' : 'STUDENT'}
                          </span>
                        </div>
                      </td>

                      {/* Book Title & Copy Code */}
                      <td style={{ padding: '16px 12px' }}>
                        <div style={{ fontWeight: 700, color: '#fff', marginBottom: '3px' }}>{row.bookTitle}</div>
                        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                          <span style={{ fontSize: '0.78rem', color: 'var(--text-secondary)' }}>By {row.authorName}</span>
                          <span style={{ fontFamily: 'monospace', fontSize: '0.75rem', color: '#a5b4fc', background: 'rgba(99,102,241,0.15)', padding: '2px 8px', borderRadius: '4px' }}>
                            {row.copyCode}
                          </span>
                        </div>
                      </td>

                      {/* Issue Date */}
                      <td style={{ padding: '16px 12px', color: '#fff', fontWeight: 600 }}>
                        {row.issueDate}
                      </td>

                      {/* Due Date */}
                      <td style={{ padding: '16px 12px', color: isReturned ? 'var(--text-secondary)' : '#f43f5e', fontWeight: 700 }}>
                        {row.dueDate}
                      </td>

                      {/* Return Date */}
                      <td style={{ padding: '16px 12px' }}>
                        {isReturned ? (
                          <span style={{ color: '#10b981', fontWeight: 700, display: 'flex', alignItems: 'center', gap: '4px' }}>
                            <CheckCircle2 size={14} /> {row.returnDate || row.dueDate}
                          </span>
                        ) : (
                          <span className="badge badge-warning" style={{ fontSize: '0.75rem' }}>
                            Pending Return
                          </span>
                        )}
                      </td>

                      {/* Circulation Status */}
                      <td style={{ padding: '16px 12px' }}>
                        <span className={`badge ${isReturned ? 'badge-success' : 'badge-warning'}`}>
                          {isReturned ? 'RETURNED' : 'ACTIVE LOAN'}
                        </span>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default LibrarianBorrowHistory;
