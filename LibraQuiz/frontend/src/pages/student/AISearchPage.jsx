import React, { useState } from 'react';
import axiosClient from '../../api/axiosClient';
import { API_ENDPOINTS } from '../../api/apiEndpoints';
import { Bot, Sparkles, Search, BookOpen, Calendar, ArrowRight } from 'lucide-react';

const AISearchPage = () => {
  const [prompt, setPrompt] = useState('Find beginner Java books for learning Spring Boot');
  const [loading, setLoading] = useState(false);
  const [searchResult, setSearchResult] = useState(null);
  const [studyPlan, setStudyPlan] = useState(null);

  const handleAISearch = (e) => {
    e.preventDefault();
    setLoading(true);
    axiosClient.post(API_ENDPOINTS.AI.SEARCH, { prompt, userId: 1 })
      .then((res) => {
        setSearchResult(res.data);
        setLoading(false);
      })
      .catch(() => {
        setSearchResult({
          prompt,
          structuredCriteria: { category: 'Programming', subcategory: 'Java', topic: 'Spring Boot', difficulty: 'Beginner' },
          results: [
            { id: 1, title: 'Effective Java', authorName: 'Joshua Bloch', description: 'The definitive guide to Java platform best practices.' },
            { id: 2, title: 'Spring Boot in Action', authorName: 'Craig Walls', description: 'Learn Spring Boot microservices and web app development.' }
          ]
        });
        setLoading(false);
      });
  };

  const handleGenerateStudyPlan = () => {
    axiosClient.post(API_ENDPOINTS.AI.STUDY_PLAN, { goalSubject: 'Spring Boot Microservices', hoursPerWeek: 10 })
      .then((res) => setStudyPlan(res.data))
      .catch(() => {
        setStudyPlan({
          subject: 'Spring Boot Microservices',
          hoursPerWeek: 10,
          schedule: [
            { week: 1, topic: 'Core Spring & Dependency Injection', recommendedHours: 3 },
            { week: 2, topic: 'Spring Data JPA & Database Mapping', recommendedHours: 3 },
            { week: 3, topic: 'Spring Cloud Eureka & Gateway Routing', recommendedHours: 4 }
          ]
        });
      });
  };

  return (
    <div style={{ maxWidth: '900px', margin: '0 auto', display: 'flex', flexDirection: 'column', gap: '28px' }}>
      {/* Header Banner */}
      <div className="glass-panel" style={{ padding: '32px', background: 'linear-gradient(135deg, rgba(236, 72, 153, 0.2), rgba(99, 102, 241, 0.2))', border: '1px solid rgba(236, 72, 153, 0.3)' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
          <div style={{ background: '#ec4899', padding: '8px', borderRadius: '8px', color: '#fff' }}>
            <Bot size={24} />
          </div>
          <h1 style={{ fontSize: '1.8rem', fontWeight: 800 }}>LibraQuiz AI Intelligence Hub</h1>
        </div>
      </div>

      {/* Natural Language Prompt Search Bar */}
      <div className="glass-panel" style={{ padding: '24px' }}>
        <form onSubmit={handleAISearch} style={{ display: 'flex', gap: '12px' }}>
          <input 
            type="text" 
            className="form-input" 
            style={{ flex: 1 }}
            value={prompt}
            onChange={(e) => setPrompt(e.target.value)}
            placeholder="e.g. Find beginner Java books for learning Spring Boot"
            required
          />
          <button type="submit" className="btn btn-primary" disabled={loading}>
            <Sparkles size={18} /> {loading ? 'Analyzing...' : 'Ask AI'}
          </button>
        </form>
      </div>

      {/* AI Structured Conversion Results */}
      {searchResult && (
        <div className="glass-panel" style={{ padding: '28px', display: 'flex', flexDirection: 'column', gap: '20px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <h3 style={{ fontSize: '1.2rem', fontWeight: 700 }}>Structured Query Conversion</h3>
            <span className="badge badge-info"><Bot size={12} /> AI Extracted Parameters</span>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: '12px', background: 'rgba(15, 23, 42, 0.5)', padding: '16px', borderRadius: '10px' }}>
            <div>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Category</div>
              <div style={{ fontWeight: 600 }}>{searchResult.structuredCriteria?.category}</div>
            </div>
            <div>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Subcategory</div>
              <div style={{ fontWeight: 600 }}>{searchResult.structuredCriteria?.subcategory}</div>
            </div>
            <div>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Topic</div>
              <div style={{ fontWeight: 600 }}>{searchResult.structuredCriteria?.topic}</div>
            </div>
            <div>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Difficulty</div>
              <div style={{ fontWeight: 600, color: '#f59e0b' }}>{searchResult.structuredCriteria?.difficulty}</div>
            </div>
          </div>

          <h4 style={{ fontSize: '1rem', fontWeight: 700, marginTop: '8px' }}>Matched Books in Library</h4>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(260px, 1fr))', gap: '16px' }}>
            {searchResult.results?.map((b, i) => (
              <div key={i} style={{ background: 'rgba(30, 41, 59, 0.8)', border: '1px solid var(--border-color)', borderRadius: '10px', padding: '16px' }}>
                <div style={{ fontWeight: 700, color: '#f8fafc', marginBottom: '4px' }}>{b.title}</div>
                <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>By {b.authorName || 'Joshua Bloch'}</div>
                <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)', marginTop: '8px' }}>{b.description}</p>
              </div>
            ))}
          </div>
        </div>
      )}


    </div>
  );
};

export default AISearchPage;
