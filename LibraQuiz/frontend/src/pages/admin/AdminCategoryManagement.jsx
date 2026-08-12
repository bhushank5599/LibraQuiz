import React, { useState } from 'react';
import { FolderTree, Plus, Edit2, Trash2, FolderPlus, Tag } from 'lucide-react';

const AdminCategoryManagement = () => {
  const [categories, setCategories] = useState([
    { id: 1, name: 'Computer Science', code: 'CS', parentName: 'Root', description: 'Software engineering, algorithms & data structures' },
    { id: 2, name: 'Web Development', code: 'CS-WEB', parentName: 'Computer Science', description: 'React, Spring Boot, microservices & HTML/CSS' },
    { id: 3, name: 'Artificial Intelligence', code: 'CS-AI', parentName: 'Computer Science', description: 'Machine learning, neural networks & NLP' },
    { id: 4, name: 'Mathematics', code: 'MATH', parentName: 'Root', description: 'Calculus, linear algebra, discrete math' },
    { id: 5, name: 'Electrical Engineering', code: 'EE', parentName: 'Root', description: 'Circuits, signal processing, embedded systems' },
  ]);

  const [showModal, setShowModal] = useState(false);
  const [newCategory, setNewCategory] = useState({ name: '', code: '', parentName: 'Root', description: '' });

  const handleAddCategory = (e) => {
    e.preventDefault();
    const created = {
      id: categories.length + 1,
      ...newCategory
    };
    setCategories([...categories, created]);
    setNewCategory({ name: '', code: '', parentName: 'Root', description: '' });
    setShowModal(false);
  };

  return (
    <div>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <h1 style={{ fontSize: '2rem', fontWeight: 800 }}>Category Taxonomy</h1>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', marginTop: '4px' }}>
            Manage category hierarchies for Courses, Digital Library Books, and Question Banks.
          </p>
        </div>
        <button className="btn btn-primary" onClick={() => setShowModal(true)}>
          <Plus size={18} /> Create Category
        </button>
      </div>

      {/* Categories Grid */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '24px' }}>
        {categories.map((cat) => (
          <div key={cat.id} className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
            <div>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '12px' }}>
                <div style={{ background: 'rgba(99, 102, 241, 0.15)', padding: '10px', borderRadius: '10px' }}>
                  <FolderTree color="var(--primary-500)" size={22} />
                </div>
                <span className="badge badge-info">{cat.code}</span>
              </div>
              <h3 style={{ fontSize: '1.2rem', fontWeight: 800, marginBottom: '6px' }}>{cat.name}</h3>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', marginBottom: '16px', lineHeight: 1.5 }}>
                {cat.description}
              </p>
            </div>

            <div style={{ borderTop: '1px solid var(--border-color)', paddingTop: '14px', display: 'flex', justifyContent: 'space-between', alignItems: 'center', fontSize: '0.8rem', color: 'var(--text-muted)' }}>
              <span>Parent: <strong style={{ color: 'var(--text-primary)' }}>{cat.parentName}</strong></span>
              <div style={{ display: 'flex', gap: '8px' }}>
                <button className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.75rem' }}><Edit2 size={12} /></button>
                <button className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.75rem', color: 'var(--rose-500)' }}><Trash2 size={12} /></button>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Add Modal */}
      {showModal && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.7)', backdropFilter: 'blur(8px)', zIndex: 1000, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '480px', padding: '32px' }}>
            <h3 style={{ fontSize: '1.4rem', fontWeight: 800, marginBottom: '20px' }}>Create Category</h3>
            <form onSubmit={handleAddCategory}>
              <div className="form-group">
                <label className="form-label">Category Name</label>
                <input 
                  type="text" 
                  className="form-input" 
                  placeholder="e.g. Data Structures" 
                  value={newCategory.name}
                  onChange={(e) => setNewCategory({ ...newCategory, name: e.target.value })}
                  required 
                />
              </div>

              <div className="form-group">
                <label className="form-label">Category Code</label>
                <input 
                  type="text" 
                  className="form-input" 
                  placeholder="e.g. CS-DS" 
                  value={newCategory.code}
                  onChange={(e) => setNewCategory({ ...newCategory, code: e.target.value })}
                  required 
                />
              </div>

              <div className="form-group">
                <label className="form-label">Parent Category</label>
                <select 
                  className="form-select"
                  value={newCategory.parentName}
                  onChange={(e) => setNewCategory({ ...newCategory, parentName: e.target.value })}
                >
                  <option value="Root">Root Category</option>
                  <option value="Computer Science">Computer Science</option>
                  <option value="Mathematics">Mathematics</option>
                  <option value="Electrical Engineering">Electrical Engineering</option>
                </select>
              </div>

              <div className="form-group" style={{ marginBottom: '24px' }}>
                <label className="form-label">Description</label>
                <textarea 
                  className="form-input" 
                  placeholder="Brief description..."
                  rows={3}
                  value={newCategory.description}
                  onChange={(e) => setNewCategory({ ...newCategory, description: e.target.value })}
                ></textarea>
              </div>

              <div style={{ display: 'flex', gap: '12px', justifyContent: 'flex-end' }}>
                <button type="button" className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancel</button>
                <button type="submit" className="btn btn-primary">Save Category</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default AdminCategoryManagement;
