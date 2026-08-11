import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { logout } from '../../features/auth/authSlice';
import { BookOpen, LogOut, Key } from 'lucide-react';
import ChangePasswordModal from './ChangePasswordModal';

const Navbar = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { user } = useSelector((state) => state.auth);
  const [showChangePassModal, setShowChangePassModal] = useState(false);

  const handleLogout = () => {
    dispatch(logout());
    navigate('/', { replace: true });
  };

  return (
    <>
      <header className="glass-panel" style={{ borderRadius: 0, padding: '16px 32px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '12px', cursor: 'pointer' }} onClick={() => navigate('/dashboard')}>
          <div style={{ background: 'linear-gradient(135deg, #6366f1, #ec4899)', width: '38px', height: '38px', borderRadius: '10px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <BookOpen color="#fff" size={22} />
          </div>
          <h2 style={{ fontSize: '1.4rem', fontWeight: 800 }} className="gradient-text">
            LibraQuiz
          </h2>
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
          {user ? (
            <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
              <div style={{ textAlign: 'right' }}>
                <div style={{ fontWeight: 700, fontSize: '0.95rem', color: '#fff' }}>{user.fullName || user.username}</div>
                <div style={{ fontSize: '0.75rem', color: 'var(--primary-500)', fontWeight: 600 }}>
                  {user.roles ? user.roles.join(', ') : (user.role || 'STUDENT')}
                </div>
              </div>

              <button 
                className="btn btn-secondary" 
                onClick={() => setShowChangePassModal(true)} 
                title="Change Password" 
                style={{ padding: '8px 14px', fontSize: '0.85rem', display: 'flex', alignItems: 'center', gap: '6px' }}
              >
                <Key size={15} color="#a5b4fc" /> Change Password
              </button>

              <button className="btn btn-secondary" onClick={handleLogout} title="Logout" style={{ padding: '8px 14px', fontSize: '0.85rem', display: 'flex', alignItems: 'center', gap: '6px' }}>
                <LogOut size={16} /> Logout
              </button>
            </div>
          ) : (
            <button className="btn btn-primary" onClick={() => navigate('/login')}>Sign In</button>
          )}
        </div>
      </header>

      <ChangePasswordModal 
        isOpen={showChangePassModal} 
        onClose={() => setShowChangePassModal(false)} 
        user={user} 
      />
    </>
  );
};

export default Navbar;
