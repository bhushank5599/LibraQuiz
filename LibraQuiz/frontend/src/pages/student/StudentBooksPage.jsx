import React, { useState, useEffect } from 'react';
import { Search, RefreshCw } from 'lucide-react';
import axiosClient from '../../api/axiosClient';
import { API_ENDPOINTS } from '../../api/apiEndpoints';

const StudentBooksPage = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [books, setBooks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const fallbackBooks = [
    { id: 1, title: 'Effective Java', isbn: '978-0134685991', author: 'Joshua Bloch', authorName: 'Joshua Bloch', category: 'Java Programming', totalCopies: 5, availableCopies: 5, publicationYear: 2018 },
    { id: 2, title: 'Spring Boot in Action', isbn: '978-1617292545', author: 'Craig Walls', authorName: 'Craig Walls', category: 'Web Development', totalCopies: 3, availableCopies: 3, publicationYear: 2016 },
    { id: 3, title: 'Python Crash Course', isbn: '978-1593279288', author: 'Eric Matthes', authorName: 'Eric Matthes', category: 'Python', totalCopies: 6, availableCopies: 6, publicationYear: 2019 },
    { id: 4, title: 'Clean Code: A Handbook of Agile Software Craftsmanship', isbn: '978-0132350884', author: 'Robert C. Martin', authorName: 'Robert C. Martin', category: 'Software Engineering', totalCopies: 4, availableCopies: 4, publicationYear: 2008 },
    { id: 5, title: 'Designing Data-Intensive Applications', isbn: '978-1449373320', author: 'Martin Kleppmann', authorName: 'Martin Kleppmann', category: 'Distributed Systems', totalCopies: 7, availableCopies: 7, publicationYear: 2017 },
    { id: 6, title: 'Introduction to Algorithms (CLRS)', isbn: '978-0262033848', author: 'Thomas H. Cormen', authorName: 'Thomas H. Cormen', category: 'Algorithms & Data Structures', totalCopies: 8, availableCopies: 8, publicationYear: 2009 },
    { id: 7, title: "System Design Interview – An Insider's Guide", isbn: '978-1736049105', author: 'Alex Xu', authorName: 'Alex Xu', category: 'System Design', totalCopies: 5, availableCopies: 5, publicationYear: 2020 },
    { id: 8, title: 'Head First Design Patterns', isbn: '978-0596007126', author: 'Eric Freeman', authorName: 'Eric Freeman', category: 'Software Architecture', totalCopies: 4, availableCopies: 4, publicationYear: 2004 },
    { id: 9, title: 'Computer Networking: A Top-Down Approach', isbn: '978-0133594140', author: 'James Kurose', authorName: 'James Kurose', category: 'Networking', totalCopies: 6, availableCopies: 6, publicationYear: 2017 },
    { id: 10, title: 'Modern Operating Systems', isbn: '978-0133591620', author: 'Andrew S. Tanenbaum', authorName: 'Andrew S. Tanenbaum', category: 'Operating Systems', totalCopies: 3, availableCopies: 3, publicationYear: 2014 },
    { id: 11, title: 'Database System Concepts', isbn: '978-0078022159', author: 'Abraham Silberschatz', authorName: 'Abraham Silberschatz', category: 'Database Systems', totalCopies: 5, availableCopies: 5, publicationYear: 2019 },
    { id: 12, title: 'Artificial Intelligence: A Modern Approach', isbn: '978-0134610993', author: 'Stuart Russell', authorName: 'Stuart Russell', category: 'Artificial Intelligence', totalCopies: 4, availableCopies: 4, publicationYear: 2020 },
    { id: 13, title: 'Hands-On Machine Learning with Scikit-Learn, Keras, and TensorFlow', isbn: '978-1492032649', author: 'Aurélien Géron', authorName: 'Aurélien Géron', category: 'Machine Learning', totalCopies: 7, availableCopies: 7, publicationYear: 2019 },
    { id: 14, title: 'The Pragmatic Programmer: Your Journey to Mastery', isbn: '978-0135957059', author: 'David Thomas', authorName: 'David Thomas', category: 'Software Engineering', totalCopies: 3, availableCopies: 3, publicationYear: 2019 },
    { id: 15, title: 'DevOps Handbook', isbn: '978-1942788003', author: 'Gene Kim', authorName: 'Gene Kim', category: 'DevOps & Cloud', totalCopies: 5, availableCopies: 5, publicationYear: 2016 },
    { id: 16, title: 'Site Reliability Engineering', isbn: '978-1491929124', author: 'Niall Richard Murphy', authorName: 'Niall Richard Murphy', category: 'DevOps & Cloud', totalCopies: 4, availableCopies: 4, publicationYear: 2016 },
    { id: 17, title: 'Real-World Bug Hunting: A Field Guide to Web Hacking', isbn: '978-1593278618', author: 'Peter Yaworski', authorName: 'Peter Yaworski', category: 'Cybersecurity', totalCopies: 6, availableCopies: 6, publicationYear: 2019 },
    { id: 18, title: 'C# in Depth', isbn: '978-1617294532', author: 'Jon Skeet', authorName: 'Jon Skeet', category: '.NET Programming', totalCopies: 2, availableCopies: 2, publicationYear: 2019 },
    { id: 19, title: 'Pro React 16', isbn: '978-1484244500', author: 'Adam Freeman', authorName: 'Adam Freeman', category: 'Web Development', totalCopies: 4, availableCopies: 4, publicationYear: 2019 },
    { id: 20, title: 'Learning SQL: Generate, Manipulate, and Retrieve Data', isbn: '978-1492057611', author: 'Alan Beaulieu', authorName: 'Alan Beaulieu', category: 'Database Systems', totalCopies: 8, availableCopies: 8, publicationYear: 2020 }
  ];

  const loadBooks = async () => {
    setIsLoading(true);
    try {
      let apiBooks = [];
      try {
        const res = await axiosClient.get(API_ENDPOINTS.BOOKS.BASE);
        if (res.data && Array.isArray(res.data) && res.data.length > 0) {
          apiBooks = res.data;
        }
      } catch (e) {}

      // Fetch active transactions to calculate borrowed count per book
      let activeTxns = [];
      try {
        const txnRes = await axiosClient.get('/transactions');
        if (txnRes.data && Array.isArray(txnRes.data)) {
          activeTxns = txnRes.data.filter(t => (t.status || '').toUpperCase() === 'ISSUED' || (t.status || '').toUpperCase() === 'BORROWED');
        }
      } catch (e) {}

      const localTxns = JSON.parse(localStorage.getItem('lq_issued_transactions') || '[]')
        .filter(t => (t.status || '').toUpperCase() === 'ISSUED' || (t.status || '').toUpperCase() === 'BORROWED');

      const allActiveTxns = [...activeTxns, ...localTxns];

      const customAdded = JSON.parse(localStorage.getItem('lq_custom_added_books') || '[]');
      const combined = [...fallbackBooks, ...customAdded, ...apiBooks];

      const isTxnForBook = (t, b) => {
        if (!t || !b) return false;
        const status = (t.status || '').toUpperCase();
        if (status !== 'ISSUED' && status !== 'BORROWED') return false;

        if (t.bookIsbn && b.isbn && t.bookIsbn.replace(/[^0-9]/g, '') === b.isbn.replace(/[^0-9]/g, '')) return true;
        if (t.bookTitle && b.title && t.bookTitle.toLowerCase().trim() === b.title.toLowerCase().trim()) return true;
        if (t.bookId && b.id && String(t.bookId) === String(b.id)) return true;

        const cc = (t.copyCode || '').toLowerCase();
        const isbnDigits = (b.isbn || '').replace(/[^0-9]/g, '');
        const title = (b.title || '').toLowerCase();
        const bId = String(b.id || '');

        if (isbnDigits && cc.includes(isbnDigits)) return true;
        if (bId && (cc.includes(`-${bId}-`) || cc.startsWith(`bc-${bId}-`) || cc.includes(`-${Number(bId) + 100}-`))) return true;

        if (title.includes('effective java') && (cc.includes('101') || cc.includes('effective') || cc.includes('java'))) return true;
        if (title.includes('spring boot') && (cc.includes('102') || cc.includes('spring'))) return true;
        if (title.includes('python') && (cc.includes('103') || cc.includes('python'))) return true;
        if (title.includes('clean code') && (cc.includes('104') || cc.includes('clean'))) return true;
        if (title.includes('data-intensive') && (cc.includes('105') || cc.includes('data'))) return true;

        return false;
      };

      const uniqueMap = new Map();
      combined.forEach(b => {
        const key = b.isbn || b.title;
        if (key && !uniqueMap.has(key)) {
          const borrowedCount = allActiveTxns.filter(t => isTxnForBook(t, b)).length;
          const total = b.totalCopies || b.availableCopies || 5;
          const avail = Math.max(0, total - borrowedCount);

          uniqueMap.set(key, {
            ...b,
            totalCopies: total,
            availableCopies: avail
          });
        }
      });

      setBooks(Array.from(uniqueMap.values()));
    } catch (e) {
      setBooks(fallbackBooks.map(b => ({ ...b, totalCopies: b.totalCopies || 5, availableCopies: b.totalCopies || 5 })));
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    loadBooks();
  }, []);

  const filtered = books.filter(b => 
    (b.title || '').toLowerCase().includes(searchTerm.toLowerCase()) || 
    (b.author || b.authorName || '').toLowerCase().includes(searchTerm.toLowerCase()) ||
    (b.category || '').toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <h1 style={{ fontSize: '2rem', fontWeight: 800 }}>Digital Library & Borrow Catalog</h1>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', marginTop: '4px' }}>
            Browse available books and explore digital catalog items.
          </p>
        </div>
        <button className="btn btn-secondary" onClick={loadBooks} disabled={isLoading}>
          <RefreshCw size={16} className={isLoading ? 'floating-card' : ''} /> {isLoading ? 'Refreshing...' : 'Refresh'}
        </button>
      </div>

      {/* Search Bar */}
      <div className="glass-panel" style={{ padding: '16px', marginBottom: '24px', display: 'flex', alignItems: 'center', gap: '12px' }}>
        <Search size={18} color="var(--text-muted)" />
        <input 
          type="text" 
          className="form-input" 
          placeholder="Search books by title, author, or topic..." 
          style={{ width: '100%', border: 'none', background: 'transparent' }}
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {/* Grid */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '24px' }}>
        {filtered.map((b) => (
          <div key={b.id || b.isbn || b.title} className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
            <div>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
                <span className="badge badge-info">{b.category || 'General'}</span>
                {b.availableCopies > 0 ? (
                  <span className="badge badge-success">
                    Available ({b.availableCopies} / {b.totalCopies} Copies)
                  </span>
                ) : (
                  <span className="badge badge-danger" style={{ background: 'rgba(244, 63, 94, 0.15)', color: '#f43f5e', border: '1px solid rgba(244, 63, 94, 0.3)' }}>
                    Out of Stock (0 / {b.totalCopies} Copies)
                  </span>
                )}
              </div>
              <h3 style={{ fontSize: '1.15rem', fontWeight: 800, color: '#fff', marginBottom: '6px' }}>{b.title}</h3>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>By {b.author || b.authorName || 'Author'}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default StudentBooksPage;
