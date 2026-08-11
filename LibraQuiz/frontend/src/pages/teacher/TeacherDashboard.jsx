import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Layers, HelpCircle, Award, Users, Key } from 'lucide-react';
import { useSelector } from 'react-redux';
import ChangePasswordModal from '../../components/common/ChangePasswordModal';

const TeacherDashboard = () => {
  const { user } = useSelector((state) => state.auth);
  const [submissions, setSubmissions] = useState([]);
  const [teacherQuizzesCount, setTeacherQuizzesCount] = useState(0);
  const [showChangePassModal, setShowChangePassModal] = useState(false);

  const getCurrentTeacherKey = () => {
    try {
      const userStr = localStorage.getItem('user');
      const u = userStr ? JSON.parse(userStr) : null;
      return u?.username || u?.email || u?.id || 'default_teacher';
    } catch (e) {
      return 'default_teacher';
    }
  };

  useEffect(() => {
    const currentTeacherKey = getCurrentTeacherKey();

    // Load teacher quizzes count
    const stored = JSON.parse(localStorage.getItem('lq_quizzes') || '[]');
    const myQuizzes = stored.filter(q => !q.createdByTeacher || q.createdByTeacher === currentTeacherKey);
    setTeacherQuizzesCount(myQuizzes.length);

    // Load student submissions for this teacher
    const allSubmissions = JSON.parse(localStorage.getItem('lq_teacher_student_submissions') || '[]');
    const mySubmissions = allSubmissions.filter(s => !s.createdByTeacher || s.createdByTeacher === currentTeacherKey);
    setSubmissions(mySubmissions);
  }, []);

  const totalSubmissions = submissions.length;
  const passedSubmissions = submissions.filter(s => s.isPassed).length;
  const passRate = totalSubmissions > 0 ? Math.round((passedSubmissions / totalSubmissions) * 100) : 100;

  return (
    <div>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <div className="badge badge-warning" style={{ marginBottom: '8px' }}>
            👨‍🏫 Teacher & Examiner Workspace
          </div>
          <h1 style={{ fontSize: '2.2rem', fontWeight: 800 }}>Teacher Dashboard</h1>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem', marginTop: '4px' }}>
            Manage Question Bank, construct custom quizzes, and evaluate student attempt results.
          </p>
        </div>

      </div>

      {/* Metrics Row */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))', gap: '20px', marginBottom: '36px' }}>
        <div className="glass-panel" style={{ padding: '24px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
            <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', fontWeight: 600 }}>My Published Quizzes</span>
            <div style={{ background: 'rgba(236, 72, 153, 0.15)', padding: '10px', borderRadius: '10px' }}>
              <HelpCircle color="var(--secondary-500)" size={22} />
            </div>
          </div>
          <h2 style={{ fontSize: '2rem', fontWeight: 800, color: '#fff' }}>{teacherQuizzesCount}</h2>
          <span style={{ fontSize: '0.75rem', color: 'var(--emerald-500)', fontWeight: 700 }}>Active in Quiz Builder</span>
        </div>

        <div className="glass-panel" style={{ padding: '24px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
            <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', fontWeight: 600 }}>Total Student Submissions</span>
            <div style={{ background: 'rgba(99, 102, 241, 0.15)', padding: '10px', borderRadius: '10px' }}>
              <Users color="var(--primary-500)" size={22} />
            </div>
          </div>
          <h2 style={{ fontSize: '2rem', fontWeight: 800, color: '#fff' }}>{totalSubmissions}</h2>
          <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Student Exam Attempts</span>
        </div>


      </div>

      {/* Teacher Action Grid */}
      <h3 style={{ fontSize: '1.3rem', fontWeight: 800, marginBottom: '20px' }}>Quick Educator Tools</h3>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '24px', marginBottom: '40px' }}>
        
        <Link to="/teacher/question-bank" className="glass-panel" style={{ padding: '24px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '12px' }}>
            <div style={{ background: 'rgba(99, 102, 241, 0.2)', padding: '12px', borderRadius: '12px' }}>
              <Layers color="var(--primary-500)" size={24} />
            </div>
            <div>
              <h4 style={{ fontSize: '1.1rem', fontWeight: 700 }}>Question Bank Studio</h4>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Add new questions, manage versions, and configure difficulty levels.</p>
            </div>
          </div>
          <span style={{ color: 'var(--primary-500)', fontWeight: 600, fontSize: '0.875rem' }}>Manage Questions &rarr;</span>
        </Link>

        <Link to="/teacher/quiz-builder" className="glass-panel" style={{ padding: '24px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '12px' }}>
            <div style={{ background: 'rgba(236, 72, 153, 0.2)', padding: '12px', borderRadius: '12px' }}>
              <HelpCircle color="var(--secondary-500)" size={24} />
            </div>
            <div>
              <h4 style={{ fontSize: '1.1rem', fontWeight: 700 }}>Quiz & Exam Builder</h4>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Assemble quizzes from questions, set retake limits, and view student results.</p>
            </div>
          </div>
          <span style={{ color: 'var(--secondary-500)', fontWeight: 600, fontSize: '0.875rem' }}>Build New Quiz &rarr;</span>
        </Link>

      </div>

      {/* Student Submissions Performance Table */}
      <h3 style={{ fontSize: '1.3rem', fontWeight: 800, marginBottom: '16px' }}>Student Exam Submissions & Results</h3>
      <div className="glass-panel" style={{ padding: '24px' }}>
        {submissions.length === 0 ? (
          <div style={{ textAlign: 'center', padding: '36px', color: 'var(--text-secondary)' }}>
            <Users size={48} style={{ margin: '0 auto 12px', opacity: 0.4, color: 'var(--primary-500)' }} />
            <h4 style={{ fontSize: '1.1rem', fontWeight: 800, color: '#fff', marginBottom: '4px' }}>No Student Submissions Yet</h4>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>
              When a student attempts a quiz published by your profile, their performance and test score will appear here.
            </p>
          </div>
        ) : (
          <div style={{ overflowX: 'auto' }}>
            <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left', fontSize: '0.9rem' }}>
              <thead>
                <tr style={{ borderBottom: '1px solid rgba(255,255,255,0.1)', color: 'var(--text-muted)' }}>
                  <th style={{ padding: '12px' }}>Student Username</th>
                  <th style={{ padding: '12px' }}>Quiz Title</th>
                  <th style={{ padding: '12px' }}>Subject Topic</th>
                  <th style={{ padding: '12px' }}>Score</th>
                  <th style={{ padding: '12px' }}>Result</th>
                  <th style={{ padding: '12px' }}>Attempt</th>
                  <th style={{ padding: '12px' }}>Submitted At</th>
                </tr>
              </thead>
              <tbody>
                {submissions.map((sub, idx) => (
                  <tr key={idx} style={{ borderBottom: '1px solid rgba(255,255,255,0.04)' }}>
                    <td style={{ padding: '14px 12px', fontWeight: 700, color: '#fff' }}>👤 {sub.studentUsername}</td>
                    <td style={{ padding: '14px 12px', color: '#fff' }}>{sub.quizTitle}</td>
                    <td style={{ padding: '14px 12px' }}><span className="badge badge-info">{sub.topic}</span></td>
                    <td style={{ padding: '14px 12px', fontWeight: 800, color: sub.isPassed ? '#10b981' : '#f43f5e' }}>{sub.scorePercentage}%</td>
                    <td style={{ padding: '14px 12px' }}>
                      <span className={`badge ${sub.isPassed ? 'badge-success' : 'badge-danger'}`}>
                        {sub.isPassed ? 'PASSED' : 'FAILED'}
                      </span>
                    </td>
                    <td style={{ padding: '14px 12px', color: 'var(--text-secondary)' }}>Attempt {sub.attemptCount}/{sub.maxAttempts || 3}</td>
                    <td style={{ padding: '14px 12px', fontSize: '0.8rem', color: 'var(--text-secondary)' }}>{new Date(sub.submittedAt).toLocaleString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default TeacherDashboard;
