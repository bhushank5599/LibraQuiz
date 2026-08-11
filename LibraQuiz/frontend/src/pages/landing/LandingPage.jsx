import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser, clearAuthError } from '../../features/auth/authSlice';
import { useNavigate } from 'react-router-dom';
import {
  BookOpen,
  LogIn,
  KeyRound,
  Mail,
  Lock,
  User,
  ShieldCheck,
  Sparkles,
  BookMarked,
  Award,
  BrainCircuit,
  AlertCircle,
  CheckCircle,
  RefreshCw
} from 'lucide-react';

const LandingPage = ({ initialForm = 'login' }) => {
  const [activeForm, setActiveForm] = useState(initialForm); // 'login' | 'forgot' | 'reset'

  // Login state
  const [loginInput, setLoginInput] = useState({ usernameOrEmail: '', password: '' });
  
  // Forgot password state
  const [forgotEmail, setForgotEmail] = useState('');
  const [forgotSuccess, setForgotSuccess] = useState(false);
  
  // Reset password state
  const [resetData, setResetData] = useState({ token: '', newPassword: '', confirmPassword: '' });
  const [resetSuccess, setResetSuccess] = useState(false);
  const [resetError, setResetError] = useState('');

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { user, isLoading, error } = useSelector((state) => state.auth);

  // Helper for dynamic role routing after successful login
  const navigateByRole = (userObj) => {
    if (!userObj) return navigate('/student/dashboard');
    const roles = userObj.roles || (userObj.role ? [userObj.role] : []);
    const rStr = JSON.stringify(roles).toUpperCase();

    if (rStr.includes('ADMIN')) {
      navigate('/admin/dashboard');
    } else if (rStr.includes('TEACHER')) {
      navigate('/teacher/dashboard');
    } else if (rStr.includes('LIBRARIAN')) {
      navigate('/librarian/dashboard');
    } else {
      navigate('/student/dashboard');
    }
  };

  // If user is already logged in when visiting LandingPage, redirect to their role dashboard
  useEffect(() => {
    if (user && user.accessToken) {
      navigateByRole(user);
    }
  }, [user]);

  // Clear auth error when switching forms
  const switchFormTab = (tab) => {
    dispatch(clearAuthError());
    setActiveForm(tab);
    setForgotSuccess(false);
    setResetSuccess(false);
  };

  // Handle Login Submit
  const handleLoginSubmit = (e) => {
    e.preventDefault();
    dispatch(loginUser(loginInput)).then((res) => {
      if (!res.error && res.payload) {
        navigateByRole(res.payload);
      }
    });
  };

  // Handle Forgot Password Submit
  const handleForgotSubmit = (e) => {
    e.preventDefault();
    if (forgotEmail) {
      setForgotSuccess(true);
    }
  };

  // Handle Reset Password Submit
  const handleResetSubmit = (e) => {
    e.preventDefault();
    setResetError('');
    if (resetData.newPassword !== resetData.confirmPassword) {
      setResetError('Passwords do not match.');
      return;
    }
    if (resetData.token && resetData.newPassword) {
      setResetSuccess(true);
    }
  };

  return (
    <div style={{
      minHeight: '100vh',
      display: 'flex',
      flexDirection: 'column',
      backgroundImage: `linear-gradient(to bottom, rgba(9, 13, 22, 0.35), rgba(9, 13, 22, 0.52)), url('/assets/login_backgrounds/bg4.jpg')`,
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      backgroundRepeat: 'no-repeat',
      backgroundAttachment: 'fixed'
    }}>
      {/* 1. TOP NAVBAR */}
      <nav style={{
        position: 'sticky',
        top: 0,
        zIndex: 100,
        background: 'rgba(9, 13, 22, 0.85)',
        backdropFilter: 'blur(16px)',
        borderBottom: '1px solid var(--border-color)',
        padding: '16px 40px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between'
      }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '12px', cursor: 'pointer' }} onClick={() => switchFormTab('login')}>
          <div style={{
            background: 'linear-gradient(135deg, var(--primary-500), var(--secondary-500))',
            width: '42px',
            height: '42px',
            borderRadius: '12px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            boxShadow: '0 4px 15px rgba(99, 102, 241, 0.4)'
          }}>
            <BookOpen color="#fff" size={24} />
          </div>
          <div>
            <h2 style={{ fontSize: '1.4rem', fontWeight: 800 }} className="gradient-text">LibraQuiz</h2>
          </div>
        </div>



        {/* Quick Auth Action */}
        <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
        </div>
      </nav>

      {/* 2. CENTERED AUTH SECTION */}
      <main style={{ flex: 1, padding: '50px 20px', maxWidth: '540px', width: '100%', margin: '0 auto', display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'stretch' }}>
        
        {/* DYNAMIC INTERACTIVE AUTH PANEL */}
        <div style={{ position: 'relative', width: '100%' }}>
          <div style={{
            position: 'absolute',
            top: '-20px',
            right: '-20px',
            width: '360px',
            height: '360px',
            background: 'radial-gradient(circle, rgba(99, 102, 241, 0.3) 0%, transparent 70%)',
            filter: 'blur(45px)',
            zIndex: 0
          }}></div>

          <div className="glass-panel" style={{ position: 'relative', zIndex: 1, padding: '46px 40px', borderRadius: '20px' }}>

            {/* Auth Error Alert */}
            {error && (
              <div style={{ background: 'rgba(244, 63, 94, 0.15)', border: '1px solid rgba(244, 63, 94, 0.3)', borderRadius: '10px', padding: '14px', color: '#f43f5e', fontSize: '0.9rem', display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '24px' }}>
                <AlertCircle size={18} /> {error}
              </div>
            )}

            {/* FORM 1: LOGIN FORM */}
            {activeForm === 'login' && (
              <div>
                <div style={{ marginBottom: '28px' }}>
                  <h3 style={{ fontSize: '1.85rem', fontWeight: 800, color: '#fff' }}>Log in</h3>
                  <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem', marginTop: '6px', lineHeight: 1.5 }}>
                    Access your personalized Student, Teacher, Librarian, or Admin portal.
                  </p>
                </div>

                <form onSubmit={handleLoginSubmit}>
                  <div className="form-group" style={{ marginBottom: '20px' }}>
                    <label className="form-label" style={{ fontSize: '0.9rem', marginBottom: '8px', display: 'block' }}>
                      <User size={15} style={{ display: 'inline', marginRight: '6px' }} /> Username or Email
                    </label>
                    <input 
                      type="text" 
                      className="form-input" 
                      style={{ padding: '14px 16px', fontSize: '0.95rem', borderRadius: '10px' }}
                      placeholder="e.g. admin, student, teacher, or librarian" 
                      value={loginInput.usernameOrEmail}
                      onChange={(e) => setLoginInput({ ...loginInput, usernameOrEmail: e.target.value })}
                      required 
                    />
                  </div>

                  <div className="form-group" style={{ marginBottom: '22px' }}>
                    <label className="form-label" style={{ fontSize: '0.9rem', marginBottom: '8px', display: 'block' }}>
                      <Lock size={15} style={{ display: 'inline', marginRight: '6px' }} /> Password
                    </label>
                    <input 
                      type="password" 
                      className="form-input" 
                      style={{ padding: '14px 16px', fontSize: '0.95rem', borderRadius: '10px' }}
                      placeholder="••••••••" 
                      value={loginInput.password}
                      onChange={(e) => setLoginInput({ ...loginInput, password: e.target.value })}
                      required 
                    />
                  </div>

                  <button type="submit" className="btn btn-primary" style={{ width: '100%', padding: '15px', fontSize: '1rem', fontWeight: 700, borderRadius: '12px', marginTop: '8px' }} disabled={isLoading}>
                    <LogIn size={20} /> {isLoading ? 'Signing In...' : 'Sign In to Portal'}
                  </button>
                </form>
              </div>
            )}

            {/* FORM 2: FORGOT PASSWORD FORM */}
            {activeForm === 'forgot' && (
              <div>
                <div style={{ marginBottom: '24px' }}>
                  <h3 style={{ fontSize: '1.5rem', fontWeight: 800, display: 'flex', alignItems: 'center', gap: '8px' }}>
                    <KeyRound color="var(--primary-500)" size={24} /> Forgot Password
                  </h3>
                  <p style={{ color: 'var(--text-secondary)', fontSize: '0.875rem', marginTop: '4px' }}>
                    Enter your account email to receive a secure password reset link / token.
                  </p>
                </div>

                {forgotSuccess ? (
                  <div style={{ background: 'rgba(16, 185, 129, 0.15)', border: '1px solid rgba(16, 185, 129, 0.3)', borderRadius: '10px', padding: '16px', color: '#10b981', fontSize: '0.9rem', marginBottom: '20px' }}>
                    <div style={{ fontWeight: 700, display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '6px' }}>
                      <CheckCircle size={18} /> Instructions Sent!
                    </div>
                    If an account exists for <strong>{forgotEmail}</strong>, password reset instructions have been dispatched.
                    <div style={{ marginTop: '16px', display: 'flex', gap: '10px' }}>
                      <button className="btn btn-secondary" style={{ width: '100%', fontSize: '0.85rem' }} onClick={() => switchFormTab('reset')}>
                        Enter Reset Token
                      </button>
                    </div>
                  </div>
                ) : (
                  <form onSubmit={handleForgotSubmit}>
                    <div className="form-group" style={{ marginBottom: '20px' }}>
                      <label className="form-label"><Mail size={14} style={{ display: 'inline', marginRight: '4px' }} /> Email Address</label>
                      <input 
                        type="email" 
                        className="form-input" 
                        placeholder="your-email@example.com" 
                        value={forgotEmail}
                        onChange={(e) => setForgotEmail(e.target.value)}
                        required 
                      />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{ width: '100%', padding: '12px' }}>
                      <Mail size={18} /> Send Reset Link
                    </button>
                  </form>
                )}

                <div style={{ marginTop: '20px', textAlign: 'center', fontSize: '0.875rem', color: 'var(--text-secondary)' }}>
                  Remembered your password?{' '}
                  <button 
                    style={{ background: 'none', color: 'var(--primary-500)', fontWeight: 700 }}
                    onClick={() => switchFormTab('login')}
                  >
                    Back to Sign In
                  </button>
                </div>
              </div>
            )}

            {/* FORM 3: NEW PASSWORD FORM */}
            {activeForm === 'reset' && (
              <div>
                <div style={{ marginBottom: '24px' }}>
                  <h3 style={{ fontSize: '1.5rem', fontWeight: 800, display: 'flex', alignItems: 'center', gap: '8px' }}>
                    <RefreshCw color="var(--primary-500)" size={24} /> Set New Password
                  </h3>
                  <p style={{ color: 'var(--text-secondary)', fontSize: '0.875rem', marginTop: '4px' }}>
                    Enter your reset token and your new account password below.
                  </p>
                </div>

                {resetError && (
                  <div style={{ background: 'rgba(244, 63, 94, 0.15)', border: '1px solid rgba(244, 63, 94, 0.3)', borderRadius: '8px', padding: '12px', color: '#f43f5e', fontSize: '0.875rem', display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '20px' }}>
                    <AlertCircle size={16} /> {resetError}
                  </div>
                )}

                {resetSuccess ? (
                  <div style={{ background: 'rgba(16, 185, 129, 0.15)', border: '1px solid rgba(16, 185, 129, 0.3)', borderRadius: '10px', padding: '16px', color: '#10b981', fontSize: '0.9rem', marginBottom: '20px' }}>
                    <div style={{ fontWeight: 700, display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '6px' }}>
                      <CheckCircle size={18} /> Password Updated!
                    </div>
                    Your password has been updated. You can now log in.
                    <button className="btn btn-primary" style={{ width: '100%', marginTop: '14px' }} onClick={() => switchFormTab('login')}>
                      Sign In Now
                    </button>
                  </div>
                ) : (
                  <form onSubmit={handleResetSubmit}>
                    <div className="form-group">
                      <label className="form-label"><KeyRound size={14} style={{ display: 'inline', marginRight: '4px' }} /> Reset Token / OTP</label>
                      <input 
                        type="text" 
                        className="form-input" 
                        placeholder="Paste reset token here" 
                        value={resetData.token}
                        onChange={(e) => setResetData({ ...resetData, token: e.target.value })}
                        required 
                      />
                    </div>

                    <div className="form-group">
                      <label className="form-label"><Lock size={14} style={{ display: 'inline', marginRight: '4px' }} /> New Password</label>
                      <input 
                        type="password" 
                        className="form-input" 
                        placeholder="••••••••" 
                        value={resetData.newPassword}
                        onChange={(e) => setResetData({ ...resetData, newPassword: e.target.value })}
                        required 
                      />
                    </div>

                    <div className="form-group" style={{ marginBottom: '20px' }}>
                      <label className="form-label"><Lock size={14} style={{ display: 'inline', marginRight: '4px' }} /> Confirm New Password</label>
                      <input 
                        type="password" 
                        className="form-input" 
                        placeholder="••••••••" 
                        value={resetData.confirmPassword}
                        onChange={(e) => setResetData({ ...resetData, confirmPassword: e.target.value })}
                        required 
                      />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{ width: '100%', padding: '12px' }}>
                      Update Password
                    </button>
                  </form>
                )}

                <div style={{ marginTop: '20px', textAlign: 'center', fontSize: '0.875rem', color: 'var(--text-secondary)' }}>
                  <button 
                    style={{ background: 'none', color: 'var(--primary-500)', fontWeight: 700 }}
                    onClick={() => switchFormTab('login')}
                  >
                    Back to Sign In
                  </button>
                </div>
              </div>
            )}

          </div>
        </div>
      </main>

    </div>
  );
};

export default LandingPage;
