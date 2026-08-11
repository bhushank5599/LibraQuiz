import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { HelpCircle, Play, Award, Clock, CheckCircle2, XCircle, BookOpen, RefreshCw, Sparkles } from 'lucide-react';
import axiosClient from '../../api/axiosClient';

const StudentQuizzesPage = () => {
  const [quizzes, setQuizzes] = useState([]);
  const [studentResults, setStudentResults] = useState({});
  const [isLoading, setIsLoading] = useState(true);

  const fetchQuizzes = async () => {
    setIsLoading(true);
    try {
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

      let stored = JSON.parse(localStorage.getItem('lq_quizzes') || '[]');

      // Purge default mock seed quiz if present
      stored = stored.filter(q => q && q.id !== 1 && q.id !== 'java-assessment-1' && q.title !== 'Java Fundamentals Assessment');
      localStorage.setItem('lq_quizzes', JSON.stringify(stored));

      let activeStored = stored.filter(q => isPublished(q) && !isDeleted(q));

      let publishedApi = [];
      try {
        const res = await axiosClient.get('/quizzes/published');
        if (res.data && Array.isArray(res.data)) {
          publishedApi = res.data.filter(q => !isDeleted(q));
        }
      } catch (e) {
        console.log('QuizService published fetch check');
      }

      const combined = [...activeStored, ...publishedApi];

      const uniqueMap = new Map();
      combined.forEach(q => {
        const key = q.id || q.title;
        if (key && !uniqueMap.has(key)) {
          uniqueMap.set(key, q);
        }
      });

      const resultList = Array.from(uniqueMap.values());
      setQuizzes(resultList);

      // Load logged-in student's scores history only
      const userStr = localStorage.getItem('user');
      const user = userStr ? JSON.parse(userStr) : null;
      const studentKey = user?.username || user?.id || 'default_student';

      const storageKey = `lq_student_results_${studentKey}`;
      const savedResults = JSON.parse(localStorage.getItem(storageKey) || '{}');
      setStudentResults(savedResults);
    } catch (err) {
      console.error('Error fetching published quizzes:', err);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchQuizzes();
  }, []);

  return (
    <div>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <div className="badge badge-info" style={{ marginBottom: '8px' }}>
            🎓 Student Examination Desk
          </div>
          <h1 style={{ fontSize: '2rem', fontWeight: 800 }}>Examinations & Quizzes</h1>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', marginTop: '4px' }}>
            Take published teacher quizzes, view live timer limits, and attempt assessments.
          </p>
        </div>
        <button className="btn btn-secondary" onClick={fetchQuizzes} disabled={isLoading}>
          <RefreshCw size={16} className={isLoading ? 'floating-card' : ''} /> {isLoading ? 'Refreshing...' : 'Refresh'}
        </button>
      </div>

      {/* Quizzes List */}
      {isLoading ? (
        <div style={{ textAlign: 'center', padding: '40px', color: 'var(--text-secondary)' }}>
          Loading published teacher quizzes...
        </div>
      ) : quizzes.length === 0 ? (
        <div className="glass-panel" style={{ padding: '48px', textAlign: 'center', color: 'var(--text-secondary)' }}>
          <BookOpen size={52} style={{ margin: '0 auto 16px', opacity: 0.4, color: 'var(--primary-500)' }} />
          <h4 style={{ fontSize: '1.2rem', fontWeight: 800, color: '#fff', marginBottom: '6px' }}>No Quizzes Published Yet</h4>
          <p style={{ fontSize: '0.9rem', color: 'var(--text-muted)', maxWidth: '440px', margin: '0 auto' }}>
            When a teacher publishes a quiz, it will automatically appear here for you to take.
          </p>
        </div>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          {quizzes.map((q) => {
            const studentResult = studentResults[q.id] || (q.id ? null : studentResults[q.title]);
            const maxAttempts = Number(q.maxAttempts) || 3;
            const attemptsCount = studentResult?.attemptCount || (studentResult ? 1 : 0);
            const hasReachedLimit = attemptsCount >= maxAttempts;

            return (
              <div key={q.id} className="glass-panel" style={{ padding: '24px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '8px' }}>
                    <span className="badge badge-info" style={{ fontWeight: 800 }}>{q.topic || 'Java'}</span>
                    <span className="badge badge-warning">⏱️ {q.durationMinutes || 30} Mins</span>
                    <span className="badge badge-secondary" style={{ background: 'rgba(255,255,255,0.06)' }}>
                      {(q.questions && q.questions.length > 0) ? q.questions.length : (q.questionsCount || q.questionsLimit || 10)} Questions
                    </span>
                    <span className="badge badge-success">Pass Score: {q.passScore || 70}%</span>
                    <span className="badge badge-secondary" style={{ background: 'rgba(251, 191, 36, 0.12)', color: '#fbbf24', border: '1px solid rgba(251, 191, 36, 0.25)' }}>
                      🔁 Attempts: {attemptsCount} / {maxAttempts}
                    </span>

                    {/* Result Badge if student took exam */}
                    {studentResult && (
                      <span className={`badge ${studentResult.isPassed ? 'badge-success' : 'badge-danger'}`} style={{ padding: '4px 10px' }}>
                        {studentResult.isPassed ? <CheckCircle2 size={12} style={{ marginRight: '4px' }} /> : <XCircle size={12} style={{ marginRight: '4px' }} />}
                        Score: {studentResult.scorePercentage}% ({studentResult.isPassed ? 'PASSED' : 'FAILED'})
                      </span>
                    )}
                  </div>

                  <h3 style={{ fontSize: '1.25rem', fontWeight: 800, color: '#fff', marginBottom: '6px' }}>{q.title}</h3>
                  <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
                    Questions selected randomly from <strong>{q.topic || 'Java'}</strong> Question Bank pool.
                  </p>
                </div>

                  <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                    {studentResult && (
                      <Link 
                        to={`/exam/take/${q.id || q.title}?review=true`} 
                        className="btn btn-secondary" 
                        style={{ padding: '8px 14px', fontSize: '0.85rem', background: 'rgba(99, 102, 241, 0.15)', color: '#a5b4fc', border: '1px solid rgba(99, 102, 241, 0.3)' }}
                      >
                        <BookOpen size={14} /> Review Answers
                      </Link>
                    )}

                    {hasReachedLimit ? (
                      <button 
                        className="btn btn-secondary" 
                        disabled 
                        style={{ padding: '8px 14px', fontSize: '0.85rem', opacity: 0.5, cursor: 'not-allowed' }}
                      >
                        <XCircle size={14} /> Limit Reached ({attemptsCount}/{maxAttempts})
                      </button>
                    ) : studentResult ? (
                      <Link 
                        to={`/exam/take/${q.id || q.title}`} 
                        className="btn btn-primary" 
                        style={{ padding: '8px 14px', fontSize: '0.85rem' }}
                      >
                        <RefreshCw size={14} /> Retake Exam ({maxAttempts - attemptsCount} Left)
                      </Link>
                    ) : (
                      <Link 
                        to={`/exam/take/${q.id || q.title}`} 
                        className="btn btn-primary" 
                        style={{ padding: '10px 20px', fontSize: '0.9rem' }}
                      >
                        <Play size={16} /> Take Exam Now
                      </Link>
                    )}
                  </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default StudentQuizzesPage;
