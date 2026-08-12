import React, { useState, useEffect } from 'react';
import { BookOpen, Plus, Search, Tag, CheckCircle, AlertCircle, Barcode, RefreshCw, Trash2, Layers, X } from 'lucide-react';
import axiosClient from '../../api/axiosClient';

const LibrarianBookInventory = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [books, setBooks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [apiError, setApiError] = useState('');

  // Add Book Modal State
  const [showModal, setShowModal] = useState(false);
  const [newBook, setNewBook] = useState({
    title: '',
    authorName: '',
    isbn: '',
    categoryName: 'Software Engineering',
    publicationYear: 2026,
    initialCopiesCount: 3,
    categoryId: 4
  });
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Delete Confirmation Modal State
  const [deleteConfirmBook, setDeleteConfirmBook] = useState(null);
  const [isDeleting, setIsDeleting] = useState(false);

  // Copies Inspection Modal State
  const [viewCopiesBook, setViewCopiesBook] = useState(null);
  const [bookCopiesList, setBookCopiesList] = useState([]);
  const [loadingCopies, setLoadingCopies] = useState(false);

  // Helper to generate clean unique Copy Codes for physical copies
  const generateCopyCode = (isbn, copyIndex) => {
    const cleanIsbn = (isbn || '1000000000000').replace(/[^0-9]/g, '');
    const paddedIndex = String(copyIndex).padStart(3, '0');
    return `BC-${cleanIsbn}-${paddedIndex}`;
  };

  // Helper to normalize copy codes for comparison (strips non-digits so COPY-9780134685991-001 and BC-9780134685991-001 match!)
  const normalizeCode = (c) => (c || '').replace(/[^0-9]/g, '');

  // Helper to map category names dynamically
  const getCategoryDisplay = (book) => {
    if (book.isbn) {
      const savedCat = localStorage.getItem(`lq_book_cat_${book.isbn}`);
      if (savedCat) return savedCat;
    }

    if (book.categoryName && !book.categoryName.startsWith('Category #') && book.categoryName.toUpperCase() !== 'JAVA') {
      return book.categoryName;
    }

    if (book.category && !book.category.startsWith('Category #') && book.category.toUpperCase() !== 'JAVA') {
      return book.category;
    }

    const t = (book.title || '').toLowerCase();
    if (t.includes('spring')) return 'Web Development';
    if (t.includes('python')) return 'Python';
    if (t.includes('states')) return 'Literature & Fiction';
    if (t.includes('effective java')) return 'Java Programming';
    if (t.includes('clean') || t.includes('code') || t.includes('pattern')) return 'Software Engineering';
    if (t.includes('algorithm') || t.includes('data')) return 'Computer Science';
    
    return 'General Academic';
  };

  // Default seed dataset of 20 distinct books with different authors and copy counts
  const fallbackBooks = [
    { id: 1, title: 'Effective Java', isbn: '978-0134685991', authorName: 'Joshua Bloch', categoryName: 'Java Programming', totalCopies: 5, availableCopies: 5, publicationYear: 2018 },
    { id: 2, title: 'Spring Boot in Action', isbn: '978-1617292545', authorName: 'Craig Walls', categoryName: 'Web Development', totalCopies: 3, availableCopies: 3, publicationYear: 2016 },
    { id: 3, title: 'Python Crash Course', isbn: '978-1593279288', authorName: 'Eric Matthes', categoryName: 'Python', totalCopies: 6, availableCopies: 6, publicationYear: 2019 },
    { id: 4, title: 'Clean Code: A Handbook of Agile Software Craftsmanship', isbn: '978-0132350884', authorName: 'Robert C. Martin', categoryName: 'Software Engineering', totalCopies: 4, availableCopies: 4, publicationYear: 2008 },
    { id: 5, title: 'Designing Data-Intensive Applications', isbn: '978-1449373320', authorName: 'Martin Kleppmann', categoryName: 'Distributed Systems', totalCopies: 7, availableCopies: 7, publicationYear: 2017 },
    { id: 6, title: 'Introduction to Algorithms (CLRS)', isbn: '978-0262033848', authorName: 'Thomas H. Cormen', categoryName: 'Algorithms & Data Structures', totalCopies: 8, availableCopies: 8, publicationYear: 2009 },
    { id: 7, title: "System Design Interview – An Insider's Guide", isbn: '978-1736049105', authorName: 'Alex Xu', categoryName: 'System Design', totalCopies: 5, availableCopies: 5, publicationYear: 2020 },
    { id: 8, title: 'Head First Design Patterns', isbn: '978-0596007126', authorName: 'Eric Freeman', categoryName: 'Software Architecture', totalCopies: 4, availableCopies: 4, publicationYear: 2004 },
    { id: 9, title: 'Computer Networking: A Top-Down Approach', isbn: '978-0133594140', authorName: 'James Kurose', categoryName: 'Networking', totalCopies: 6, availableCopies: 6, publicationYear: 2017 },
    { id: 10, title: 'Modern Operating Systems', isbn: '978-0133591620', authorName: 'Andrew S. Tanenbaum', categoryName: 'Operating Systems', totalCopies: 3, availableCopies: 3, publicationYear: 2014 },
    { id: 11, title: 'Database System Concepts', isbn: '978-0078022159', authorName: 'Abraham Silberschatz', categoryName: 'Database Systems', totalCopies: 5, availableCopies: 5, publicationYear: 2019 },
    { id: 12, title: 'Artificial Intelligence: A Modern Approach', isbn: '978-0134610993', authorName: 'Stuart Russell', categoryName: 'Artificial Intelligence', totalCopies: 4, availableCopies: 4, publicationYear: 2020 },
    { id: 13, title: 'Hands-On Machine Learning with Scikit-Learn, Keras, and TensorFlow', isbn: '978-1492032649', authorName: 'Aurélien Géron', categoryName: 'Machine Learning', totalCopies: 7, availableCopies: 7, publicationYear: 2019 },
    { id: 14, title: 'The Pragmatic Programmer: Your Journey to Mastery', isbn: '978-0135957059', authorName: 'David Thomas', categoryName: 'Software Engineering', totalCopies: 3, availableCopies: 3, publicationYear: 2019 },
    { id: 15, title: 'DevOps Handbook', isbn: '978-1942788003', authorName: 'Gene Kim', categoryName: 'DevOps & Cloud', totalCopies: 5, availableCopies: 5, publicationYear: 2016 },
    { id: 16, title: 'Site Reliability Engineering', isbn: '978-1491929124', authorName: 'Niall Richard Murphy', categoryName: 'DevOps & Cloud', totalCopies: 4, availableCopies: 4, publicationYear: 2016 },
    { id: 17, title: 'Real-World Bug Hunting: A Field Guide to Web Hacking', isbn: '978-1593278618', authorName: 'Peter Yaworski', categoryName: 'Cybersecurity', totalCopies: 6, availableCopies: 6, publicationYear: 2019 },
    { id: 18, title: 'C# in Depth', isbn: '978-1617294532', authorName: 'Jon Skeet', categoryName: '.NET Programming', totalCopies: 2, availableCopies: 2, publicationYear: 2019 },
    { id: 19, title: 'Pro React 16', isbn: '978-1484244500', authorName: 'Adam Freeman', categoryName: 'Web Development', totalCopies: 4, availableCopies: 4, publicationYear: 2019 },
    { id: 20, title: 'Learning SQL: Generate, Manipulate, and Retrieve Data', isbn: '978-1492057611', authorName: 'Alan Beaulieu', categoryName: 'Database Systems', totalCopies: 8, availableCopies: 8, publicationYear: 2020 }
  ];

  const fetchBooks = async () => {
    setIsLoading(true);
    setApiError('');
    try {
      let apiBooks = [];
      try {
        const res = await axiosClient.get('/books');
        if (res.data && Array.isArray(res.data) && res.data.length > 0) {
          apiBooks = res.data;
        }
      } catch (e) {
        console.log('BookService fetch fallback check');
      }

      const customAdded = JSON.parse(localStorage.getItem('lq_custom_added_books') || '[]');
      const combined = [...fallbackBooks, ...customAdded, ...apiBooks];

      const uniqueMap = new Map();
      combined.forEach(b => {
        const key = b.isbn || b.title;
        if (key && !uniqueMap.has(key)) {
          uniqueMap.set(key, b);
        }
      });

      setBooks(Array.from(uniqueMap.values()));
    } catch (err) {
      console.error('Failed to fetch books from BookService:', err);
      setBooks(fallbackBooks);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  // Open Copies Modal & Fetch/Generate Copies with Unique Copy Codes & Issued Status
  const handleOpenCopiesModal = async (book) => {
    setViewCopiesBook(book);
    setLoadingCopies(true);
    
    const issuedList = JSON.parse(localStorage.getItem('lq_issued_copies') || '[]');
    const totalCount = book.totalCopies ?? book.totalCopiesCount ?? book.initialCopiesCount ?? 3;

    const isCodeIssued = (copyCode) => {
      const targetDigits = normalizeCode(copyCode);
      return issuedList.some(issuedCode => normalizeCode(issuedCode) === targetDigits);
    };

    try {
      const res = await axiosClient.get(`/books/copies/book/${book.id}`);
      if (res.data && Array.isArray(res.data) && res.data.length > 0) {
        setBookCopiesList(res.data.map((c, idx) => {
          const code = c.copyCode || generateCopyCode(book.isbn, idx + 1);
          const isIssued = isCodeIssued(code) || c.status === 'ISSUED' || c.status === 'UNAVAILABLE';
          return {
            id: c.id || idx + 1,
            copyCode: code,
            status: isIssued ? 'UNAVAILABLE' : (c.status || 'AVAILABLE'),
            rackLocation: c.rackLocation || `Rack ${String.fromCharCode(65 + (idx % 4))}-${(idx % 8) + 1}`,
            conditionNote: c.conditionNote || 'Good'
          };
        }));
      } else {
        const generated = Array.from({ length: totalCount }, (_, i) => {
          const code = generateCopyCode(book.isbn, i + 1);
          const isIssued = isCodeIssued(code);
          return {
            id: i + 1,
            copyCode: code,
            status: isIssued ? 'UNAVAILABLE' : 'AVAILABLE',
            rackLocation: `Rack ${String.fromCharCode(65 + (i % 4))}-${(i % 8) + 1}`,
            conditionNote: 'Good'
          };
        });
        setBookCopiesList(generated);
      }
    } catch (e) {
      const generated = Array.from({ length: totalCount }, (_, i) => {
        const code = generateCopyCode(book.isbn, i + 1);
        const isIssued = isCodeIssued(code);
        return {
          id: i + 1,
          copyCode: code,
          status: isIssued ? 'UNAVAILABLE' : 'AVAILABLE',
          rackLocation: `Rack ${String.fromCharCode(65 + (i % 4))}-${(i % 8) + 1}`,
          conditionNote: 'Good'
        };
      });
      setBookCopiesList(generated);
    } finally {
      setLoadingCopies(false);
    }
  };

  // Handle Cataloging New Book into MySQL
  const handleAddBook = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    try {
      const copiesNum = Number(newBook.initialCopiesCount) || 1;
      const userCategory = newBook.categoryName.trim();
      const cleanIsbn = newBook.isbn.trim();

      if (cleanIsbn && userCategory) {
        localStorage.setItem(`lq_book_cat_${cleanIsbn}`, userCategory);
      }

      const payload = {
        title: newBook.title.trim(),
        authorName: newBook.authorName.trim(),
        isbn: cleanIsbn,
        publisherName: 'Main Press',
        categoryName: userCategory,
        publicationYear: Number(newBook.publicationYear),
        initialCopiesCount: copiesNum,
        categoryId: Number(newBook.categoryId)
      };

      await axiosClient.post('/books', payload);

      setNewBook({
        title: '',
        authorName: '',
        isbn: '',
        categoryName: 'Software Engineering',
        publicationYear: 2026,
        initialCopiesCount: 3,
        categoryId: 4
      });
      setShowModal(false);
      fetchBooks();
    } catch (err) {
      console.error('Failed to add book:', err);
      const copiesNum = Number(newBook.initialCopiesCount) || 1;
      const created = {
        id: books.length + 1,
        title: newBook.title.trim(),
        authorName: newBook.authorName.trim(),
        isbn: newBook.isbn.trim(),
        categoryName: newBook.categoryName.trim(),
        totalCopies: copiesNum,
        availableCopies: copiesNum,
        initialCopiesCount: copiesNum
      };
      setBooks([...books, created]);
      setShowModal(false);
    } finally {
      setIsSubmitting(false);
    }
  };

  // Handle Deleting Book
  const confirmDeleteBook = async () => {
    if (!deleteConfirmBook) return;
    setIsDeleting(true);
    try {
      await axiosClient.delete(`/books/${deleteConfirmBook.id}`);
      if (deleteConfirmBook.isbn) {
        localStorage.removeItem(`lq_book_cat_${deleteConfirmBook.isbn}`);
      }
      setDeleteConfirmBook(null);
      fetchBooks();
    } catch (err) {
      console.error('Failed to delete book from database:', err);
      setBooks(prev => prev.filter(b => b.id !== deleteConfirmBook.id));
      setDeleteConfirmBook(null);
    } finally {
      setIsDeleting(false);
    }
  };

  const filteredBooks = books.filter(b => 
    (b.title || '').toLowerCase().includes(searchTerm.toLowerCase()) || 
    (b.authorName || '').toLowerCase().includes(searchTerm.toLowerCase()) ||
    (b.isbn || '').toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <h1 style={{ fontSize: '2rem', fontWeight: 800 }}>Book & Copy Inventory</h1>
        </div>
        <div style={{ display: 'flex', gap: '12px' }}>
          <button className="btn btn-secondary" onClick={fetchBooks} disabled={isLoading}>
            <RefreshCw size={16} className={isLoading ? 'floating-card' : ''} /> Refresh
          </button>
          <button className="btn btn-primary" onClick={() => setShowModal(true)}>
            <Plus size={18} /> Catalog New Book
          </button>
        </div>
      </div>

      {/* Api Alert */}
      {apiError && (
        <div style={{ background: 'rgba(244, 63, 94, 0.15)', border: '1px solid rgba(244, 63, 94, 0.3)', borderRadius: '10px', padding: '12px 18px', color: '#f43f5e', fontSize: '0.85rem', marginBottom: '24px', display: 'flex', alignItems: 'center', gap: '8px' }}>
          <AlertCircle size={16} /> {apiError}
        </div>
      )}

      {/* Search Bar */}
      <div className="glass-panel" style={{ padding: '16px', marginBottom: '24px', display: 'flex', alignItems: 'center', gap: '12px' }}>
        <Search size={18} color="var(--text-muted)" />
        <input 
          type="text" 
          className="form-input" 
          placeholder="Search inventory by title, author, or ISBN..." 
          style={{ width: '100%', border: 'none', background: 'transparent' }}
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {/* Book Catalog Grid */}
      {isLoading ? (
        <div style={{ textAlign: 'center', padding: '40px', color: 'var(--text-secondary)' }}>
          Loading book catalog from database...
        </div>
      ) : (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '24px' }}>
          {filteredBooks.map((b) => {
            const issuedList = JSON.parse(localStorage.getItem('lq_issued_copies') || '[]');
            const cleanIsbnDigits = normalizeCode(b.isbn);
            
            // Count how many copies of this book are issued (matching by ISBN numbers)
            const issuedForThisBook = issuedList.filter(cCode => normalizeCode(cCode).includes(cleanIsbnDigits)).length;

            const totalCount = b.totalCopies ?? b.totalCopiesCount ?? b.initialCopiesCount ?? 3;
            const rawAvail = b.availableCopies ?? b.availableCopiesCount ?? b.initialCopiesCount ?? totalCount;
            const availCount = Math.max(0, rawAvail - issuedForThisBook);

            return (
              <div key={b.id} className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
                <div>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '12px' }}>
                    <span className="badge badge-info">{getCategoryDisplay(b)}</span>
                    <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)', fontFamily: 'monospace' }}>ISBN: {b.isbn}</span>
                  </div>

                  <h3 style={{ fontSize: '1.15rem', fontWeight: 800, color: '#fff', marginBottom: '6px' }}>{b.title}</h3>
                  <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', marginBottom: '16px' }}>By {b.authorName}</p>
                </div>

                <div style={{ borderTop: '1px solid var(--border-color)', paddingTop: '14px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
                    Copies: <strong style={{ color: 'var(--emerald-500)' }}>{availCount} available</strong> / {totalCount} total
                  </span>
                  
                  <div style={{ display: 'flex', gap: '8px' }}>
                    <button 
                      type="button"
                      className="btn btn-secondary" 
                      style={{ padding: '6px 12px', fontSize: '0.8rem' }}
                      onClick={() => handleOpenCopiesModal(b)}
                    >
                      <Barcode size={14} /> Copies ({totalCount})
                    </button>
                    <button 
                      type="button"
                      className="btn btn-secondary" 
                      style={{ padding: '6px 12px', fontSize: '0.8rem', color: 'var(--rose-500)', border: '1px solid rgba(244, 63, 94, 0.3)' }}
                      onClick={() => setDeleteConfirmBook(b)}
                    >
                      <Trash2 size={14} /> Delete
                    </button>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}

      {/* 1. PHYSICAL COPIES INSPECTION MODAL */}
      {viewCopiesBook && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.8)', backdropFilter: 'blur(10px)', zIndex: 1150, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '620px', padding: '32px', maxHeight: '85vh', overflowY: 'auto' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '20px' }}>
              <div>
                <div className="badge badge-info" style={{ marginBottom: '6px' }}>
                  Barcode Inventory
                </div>
                <h3 style={{ fontSize: '1.4rem', fontWeight: 800, color: '#fff' }}>{viewCopiesBook.title}</h3>
                <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)', fontFamily: 'monospace', marginTop: '2px' }}>
                  ISBN: {viewCopiesBook.isbn}
                </p>
              </div>
              <button 
                className="btn btn-secondary" 
                style={{ padding: '6px', borderRadius: '50%' }}
                onClick={() => setViewCopiesBook(null)}
              >
                <X size={18} />
              </button>
            </div>

            {/* Copies Header */}
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '16px', background: 'rgba(255,255,255,0.03)', padding: '12px 16px', borderRadius: '10px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.9rem', fontWeight: 700, color: '#fff' }}>
                Physical Copies List ({bookCopiesList.length})
              </span>
            </div>

            {/* Copies List */}
            {loadingCopies ? (
              <div style={{ textAlign: 'center', padding: '30px', color: 'var(--text-secondary)' }}>
                Loading physical copy barcodes...
              </div>
            ) : (
              <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
                {bookCopiesList.map((copy, index) => (
                  <div key={copy.id || index} style={{ background: 'rgba(15, 23, 42, 0.7)', border: '1px solid var(--border-color)', borderRadius: '12px', padding: '16px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <div>
                      <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '4px' }}>
                        <span style={{ fontFamily: 'monospace', fontWeight: 800, fontSize: '1rem', color: '#a5b4fc', background: 'rgba(99, 102, 241, 0.15)', padding: '4px 10px', borderRadius: '6px', border: '1px solid rgba(99, 102, 241, 0.3)' }}>
                          {copy.copyCode}
                        </span>
                        <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Copy #{index + 1}</span>
                      </div>
                      <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)', marginTop: '4px' }}>
                        Location: <strong>{copy.rackLocation || 'Rack A-01'}</strong> • Condition: <strong>{copy.conditionNote || 'Good'}</strong>
                      </div>
                    </div>

                    <div>
                      {copy.status === 'AVAILABLE' ? (
                        <span className="badge badge-success"><CheckCircle size={12} /> AVAILABLE</span>
                      ) : (
                        <span className="badge badge-danger" style={{ background: 'rgba(244, 63, 94, 0.15)', color: '#f43f5e', border: '1px solid rgba(244, 63, 94, 0.3)' }}>
                          <AlertCircle size={12} /> UNAVAILABLE
                        </span>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            )}

            <div style={{ marginTop: '24px', textAlign: 'right' }}>
              <button className="btn btn-secondary" onClick={() => setViewCopiesBook(null)}>
                Close
              </button>
            </div>
          </div>
        </div>
      )}

      {/* 2. CATALOG NEW BOOK MODAL */}
      {showModal && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.75)', backdropFilter: 'blur(8px)', zIndex: 1000, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '480px', padding: '32px' }}>
            <h3 style={{ fontSize: '1.4rem', fontWeight: 800, marginBottom: '20px' }}>Catalog New Book Title</h3>
            <form onSubmit={handleAddBook}>
              <div className="form-group">
                <label className="form-label">Book Title</label>
                <input 
                  type="text" 
                  className="form-input" 
                  placeholder="e.g. Microservice Patterns" 
                  value={newBook.title}
                  onChange={(e) => setNewBook({ ...newBook, title: e.target.value })}
                  required 
                />
              </div>

              <div className="form-group">
                <label className="form-label">Author Name</label>
                <input 
                  type="text" 
                  className="form-input" 
                  placeholder="e.g. Chris Richardson" 
                  value={newBook.authorName}
                  onChange={(e) => setNewBook({ ...newBook, authorName: e.target.value })}
                  required 
                />
              </div>

              <div className="form-group">
                <label className="form-label">Category / Subject</label>
                <input 
                  type="text" 
                  className="form-input" 
                  placeholder="e.g. Literature & Fiction, Software Engineering, Web Development" 
                  value={newBook.categoryName}
                  onChange={(e) => setNewBook({ ...newBook, categoryName: e.target.value })}
                  required 
                />
              </div>

              <div className="form-group">
                <label className="form-label">ISBN Number</label>
                <input 
                  type="text" 
                  className="form-input" 
                  placeholder="978-1617293726" 
                  value={newBook.isbn}
                  onChange={(e) => setNewBook({ ...newBook, isbn: e.target.value })}
                  required 
                />
              </div>

              <div className="form-group" style={{ marginBottom: '24px' }}>
                <label className="form-label">Number of Physical Copies</label>
                <input 
                  type="number" 
                  className="form-input" 
                  min={1}
                  value={newBook.initialCopiesCount}
                  onChange={(e) => setNewBook({ ...newBook, initialCopiesCount: e.target.value })}
                  required 
                />
              </div>

              <div style={{ display: 'flex', gap: '12px', justifyContent: 'flex-end' }}>
                <button type="button" className="btn btn-secondary" onClick={() => setShowModal(false)} disabled={isSubmitting}>Cancel</button>
                <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
                  {isSubmitting ? 'Saving...' : 'Catalog Book'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* 3. DELETE CONFIRMATION MODAL */}
      {deleteConfirmBook && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.75)', backdropFilter: 'blur(10px)', zIndex: 1100, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '440px', padding: '36px', textAlign: 'center' }}>
            <div style={{ background: 'rgba(244, 63, 94, 0.2)', width: '64px', height: '64px', borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', margin: '0 auto 20px' }}>
              <Trash2 color="var(--rose-500)" size={32} />
            </div>
            
            <h3 style={{ fontSize: '1.5rem', fontWeight: 800, color: '#fff', marginBottom: '10px' }}>Delete Book Title?</h3>
            
            <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem', lineHeight: 1.5, marginBottom: '24px' }}>
              Are you sure you want to delete <strong style={{ color: '#fff' }}>"{deleteConfirmBook.title}"</strong> (ISBN: {deleteConfirmBook.isbn})? This will permanently remove the book and all its physical copies from MySQL database <strong>lq_bookdb</strong>.
            </p>

            <div style={{ display: 'flex', gap: '12px', justifyContent: 'center' }}>
              <button 
                type="button" 
                className="btn btn-secondary" 
                style={{ flex: 1 }} 
                onClick={() => setDeleteConfirmBook(null)}
                disabled={isDeleting}
              >
                Cancel
              </button>
              <button 
                type="button" 
                className="btn btn-primary" 
                style={{ flex: 1, background: 'var(--rose-500)', borderColor: 'var(--rose-500)' }} 
                onClick={confirmDeleteBook}
                disabled={isDeleting}
              >
                {isDeleting ? 'Deleting...' : 'Delete Permanently'}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default LibrarianBookInventory;
