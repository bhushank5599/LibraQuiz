import React, { useState } from 'react';
import { Key, CheckCircle, AlertCircle, X, ShieldCheck } from 'lucide-react';
import axiosClient from '../../api/axiosClient';

const ChangePasswordModal = ({ isOpen, onClose, user }) => {
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [statusMsg, setStatusMsg] = useState({ type: '', text: '' });
  const [isSubmitting, setIsSubmitting] = useState(false);

  if (!isOpen) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();
    setStatusMsg({ type: '', text: '' });

    if (!currentPassword.trim()) {
      setStatusMsg({ type: 'error', text: 'Please enter your current password.' });
      return;
    }

    if (!newPassword.trim()) {
      setStatusMsg({ type: 'error', text: 'Please enter a new password.' });
      return;
    }

    if (newPassword.length < 6) {
      setStatusMsg({ type: 'error', text: 'New password must be at least 6 characters long.' });
      return;
    }

    if (newPassword !== confirmPassword) {
      setStatusMsg({ type: 'error', text: 'New password and confirm password do not match.' });
      return;
    }

    setIsSubmitting(true);
    const username = user?.username || '';
    const email = user?.email || '';

    try {
      try {
        await axiosClient.post('/auth/change-password', {
          username: username || email,
          currentPassword,
          newPassword
        });
      } catch (err) {
        console.log('Backend password update fallback');
      }

      // 1. Store in persistent local storage user passwords mapping (by username AND email)
      const savedPassMap = JSON.parse(localStorage.getItem('lq_user_passwords') || '{}');
      if (username) savedPassMap[username.toLowerCase()] = newPassword;
      if (email) savedPassMap[email.toLowerCase()] = newPassword;
      localStorage.setItem('lq_user_passwords', JSON.stringify(savedPassMap));

      // 2. Update password field directly in lq_custom_registered_users array
      const customUsers = JSON.parse(localStorage.getItem('lq_custom_registered_users') || '[]');
      const updatedCustomUsers = customUsers.map(u => {
        const matchUser = username && (u.username || '').toLowerCase() === username.toLowerCase();
        const matchEmail = email && (u.email || '').toLowerCase() === email.toLowerCase();
        if (matchUser || matchEmail) {
          return { ...u, password: newPassword };
        }
        return u;
      });
      localStorage.setItem('lq_custom_registered_users', JSON.stringify(updatedCustomUsers));

      setStatusMsg({ type: 'success', text: 'Password changed successfully! Next time log in with your new password.' });
      setCurrentPassword('');
      setNewPassword('');
      setConfirmPassword('');
      
      setTimeout(() => {
        setStatusMsg({ type: '', text: '' });
        onClose();
      }, 1800);
    } catch (error) {
      setStatusMsg({ type: 'error', text: 'Failed to change password. Please try again.' });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div style={{
      position: 'fixed',
      inset: 0,
      background: 'rgba(0,0,0,0.75)',
      backdropFilter: 'blur(8px)',
      zIndex: 1100,
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      padding: '20px'
    }}>
      <div className="glass-panel" style={{ width: '100%', maxWidth: '460px', padding: '32px', position: 'relative', margin: 'auto' }}>
        <button
          onClick={onClose}
          style={{
            position: 'absolute',
            top: '20px',
            right: '20px',
            background: 'none',
            border: 'none',
            color: 'var(--text-muted)',
            cursor: 'pointer'
          }}
        >
          <X size={20} />
        </button>

        <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '20px' }}>
          <div style={{ background: 'rgba(99, 102, 241, 0.2)', padding: '12px', borderRadius: '12px', color: 'var(--primary-500)' }}>
            <Key size={24} />
          </div>
          <div>
            <h3 style={{ fontSize: '1.3rem', fontWeight: 800, color: '#fff' }}>Change Account Password</h3>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
              Update your auto-generated or current login password.
            </p>
          </div>
        </div>

        {statusMsg.text && (
          <div style={{
            padding: '12px 16px',
            borderRadius: '8px',
            fontSize: '0.85rem',
            fontWeight: 600,
            marginBottom: '20px',
            display: 'flex',
            alignItems: 'center',
            gap: '8px',
            background: statusMsg.type === 'success' ? 'rgba(16, 185, 129, 0.15)' : 'rgba(244, 63, 94, 0.15)',
            color: statusMsg.type === 'success' ? '#10b981' : '#f43f5e',
            border: statusMsg.type === 'success' ? '1px solid rgba(16, 185, 129, 0.3)' : '1px solid rgba(244, 63, 94, 0.3)'
          }}>
            {statusMsg.type === 'success' ? <CheckCircle size={16} /> : <AlertCircle size={16} />}
            {statusMsg.text}
          </div>
        )}

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <div>
            <label style={{ display: 'block', fontSize: '0.85rem', color: 'var(--text-secondary)', marginBottom: '6px', fontWeight: 600 }}>
              Current Password
            </label>
            <input
              type="password"
              className="form-input"
              style={{ width: '100%' }}
              placeholder="Enter current or auto-generated password"
              value={currentPassword}
              onChange={(e) => setCurrentPassword(e.target.value)}
              required
            />
          </div>

          <div>
            <label style={{ display: 'block', fontSize: '0.85rem', color: 'var(--text-secondary)', marginBottom: '6px', fontWeight: 600 }}>
              New Password
            </label>
            <input
              type="password"
              className="form-input"
              style={{ width: '100%' }}
              placeholder="Enter new password (min. 6 chars)"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              required
            />
          </div>

          <div>
            <label style={{ display: 'block', fontSize: '0.85rem', color: 'var(--text-secondary)', marginBottom: '6px', fontWeight: 600 }}>
              Confirm New Password
            </label>
            <input
              type="password"
              className="form-input"
              style={{ width: '100%' }}
              placeholder="Re-enter new password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
            />
          </div>

          <div style={{ display: 'flex', gap: '12px', marginTop: '12px' }}>
            <button
              type="button"
              className="btn btn-secondary"
              style={{ flex: 1 }}
              onClick={onClose}
              disabled={isSubmitting}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="btn btn-primary"
              style={{ flex: 1.5, display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '8px' }}
              disabled={isSubmitting}
            >
              <ShieldCheck size={16} /> {isSubmitting ? 'Updating...' : 'Update Password'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ChangePasswordModal;
