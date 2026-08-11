import React, { useState, useEffect } from 'react';
import { GraduationCap, PlayCircle, FolderTree, BookOpen, Search, CheckCircle, Clock } from 'lucide-react';
import axiosClient from '../../api/axiosClient';

const StudentCoursesPage = () => {
  const [activeCategory, setActiveCategory] = useState('ALL');
  const [searchTerm, setSearchTerm] = useState('');
  const [courses, setCourses] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const fallbackCourses = [
    { id: 1, title: 'Spring Boot 3 & Microservices Platform Engineering', instructor: 'Prof. Sarah Jenkins', modulesCount: 6, lessonsCount: 24, progress: 75, category: 'Web Development' },
    { id: 2, title: 'Full Stack React 18 & Redux Toolkit Masterclass', instructor: 'Prof. Sarah Jenkins', modulesCount: 5, lessonsCount: 20, progress: 90, category: 'Web Development' },
    { id: 3, title: 'Data Structures, Algorithms & Problem Solving', instructor: 'Dr. Alan Turing', modulesCount: 8, lessonsCount: 32, progress: 40, category: 'Computer Science' },
    { id: 4, title: 'Operating Systems & System Architecture', instructor: 'Dr. Alan Turing', modulesCount: 7, lessonsCount: 28, progress: 15, category: 'Computer Science' },
    { id: 5, title: 'AI & Natural Language Processing Fundamentals', instructor: 'Dr. Andrew Ng', modulesCount: 5, lessonsCount: 18, progress: 10, category: 'Artificial Intelligence' },
    { id: 6, title: 'Deep Learning & Neural Network Architecture', instructor: 'Dr. Andrew Ng', modulesCount: 6, lessonsCount: 22, progress: 0, category: 'Artificial Intelligence' },
    { id: 7, title: 'Relational Databases & SQL Query Optimization', instructor: 'Prof. Michael Stone', modulesCount: 4, lessonsCount: 16, progress: 50, category: 'Database Systems' }
  ];

  const loadCourses = async () => {
    setIsLoading(true);
    try {
      const res = await axiosClient.get('/courses');
      if (res.data && Array.isArray(res.data) && res.data.length > 0) {
        setCourses(res.data);
      } else {
        setCourses(fallbackCourses);
      }
    } catch (err) {
      setCourses(fallbackCourses);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    loadCourses();
  }, []);

  // Extract unique categories
  const categoriesList = ['ALL', ...Array.from(new Set(courses.map(c => c.category || 'General')))];

  // Filter courses by search and category
  const filteredCourses = courses.filter(c => {
    const matchesSearch = (c.title || '').toLowerCase().includes(searchTerm.toLowerCase()) || 
                          (c.instructor || '').toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCat = activeCategory === 'ALL' || c.category === activeCategory;
    return matchesSearch && matchesCat;
  });

  // Group filtered courses by category for grouped layout
  const groupedCourses = filteredCourses.reduce((acc, course) => {
    const cat = course.category || 'General Taxonomy';
    if (!acc[cat]) acc[cat] = [];
    acc[cat].push(course);
    return acc;
  }, {});

  return (
    <div>
      {/* Header */}
      <div style={{ marginBottom: '32px' }}>
        <h1 style={{ fontSize: '2rem', fontWeight: 800 }}>Course Catalog & Syllabus</h1>
        <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', marginTop: '4px' }}>
          Explore courses organized under hierarchical subject categories and track your learning progress.
        </p>
      </div>

      {/* Search and Category Filter Bar */}
      <div className="glass-panel" style={{ padding: '20px', marginBottom: '32px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
        
        {/* Search Input */}
        <div style={{ position: 'relative' }}>
          <Search size={18} color="var(--text-muted)" style={{ position: 'absolute', left: '14px', top: '50%', transform: 'translateY(-50%)' }} />
          <input 
            type="text" 
            className="form-input" 
            placeholder="Search courses by title, topic, or instructor..." 
            style={{ width: '100%', paddingLeft: '40px' }}
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        {/* Category Filter Pills */}
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px', flexWrap: 'wrap' }}>
          <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', fontWeight: 600, marginRight: '4px', display: 'flex', alignItems: 'center', gap: '4px' }}>
            <FolderTree size={16} /> Categories:
          </span>
          {categoriesList.map((cat) => (
            <button
              key={cat}
              className={`btn ${activeCategory === cat ? 'btn-primary' : 'btn-secondary'}`}
              style={{ padding: '6px 14px', fontSize: '0.8rem', borderRadius: '999px' }}
              onClick={() => setActiveCategory(cat)}
            >
              {cat === 'ALL' ? 'All Categories' : cat}
            </button>
          ))}
        </div>

      </div>

      {/* Courses Grouped Under Categories */}
      {Object.keys(groupedCourses).length === 0 ? (
        <div className="glass-panel" style={{ padding: '40px', textAlign: 'center', color: 'var(--text-muted)' }}>
          No courses found under the selected category.
        </div>
      ) : (
        Object.entries(groupedCourses).map(([categoryName, courseList]) => (
          <div key={categoryName} style={{ marginBottom: '40px' }}>
            {/* Category Header */}
            <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '20px', borderBottom: '1px solid var(--border-color)', paddingBottom: '10px' }}>
              <div style={{ background: 'rgba(99, 102, 241, 0.2)', padding: '8px', borderRadius: '8px' }}>
                <FolderTree color="var(--primary-500)" size={20} />
              </div>
              <h2 style={{ fontSize: '1.3rem', fontWeight: 800, color: '#fff' }}>{categoryName}</h2>
              <span className="badge badge-info" style={{ marginLeft: 'auto' }}>{courseList.length} Courses</span>
            </div>

            {/* Courses Grid Under Category */}
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '24px' }}>
              {courseList.map((c) => (
                <div key={c.id} className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
                  <div>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
                      <span className="badge badge-info">{c.category}</span>
                      <span style={{ fontSize: '0.8rem', color: 'var(--emerald-500)', fontWeight: 700 }}>{c.progress}% Complete</span>
                    </div>
                    
                    <h3 style={{ fontSize: '1.15rem', fontWeight: 800, color: '#fff', marginBottom: '6px' }}>{c.title}</h3>
                    <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', marginBottom: '16px' }}>Instructor: {c.instructor}</p>

                    {/* Progress Bar */}
                    <div style={{ background: 'rgba(255,255,255,0.08)', borderRadius: '999px', height: '8px', overflow: 'hidden', marginBottom: '20px' }}>
                      <div style={{ width: `${c.progress}%`, background: 'linear-gradient(90deg, var(--primary-500), var(--secondary-500))', height: '100%', borderRadius: '999px' }}></div>
                    </div>
                  </div>

                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderTop: '1px solid var(--border-color)', paddingTop: '16px' }}>
                    <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>{c.modulesCount} Modules • {c.lessonsCount} Lessons</span>
                    <button className="btn btn-primary" style={{ padding: '8px 16px', fontSize: '0.85rem' }}>
                      <PlayCircle size={16} /> Continue
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default StudentCoursesPage;
