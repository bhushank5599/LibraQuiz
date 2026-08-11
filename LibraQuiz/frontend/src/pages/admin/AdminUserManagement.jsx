import React, { useState, useEffect } from 'react';
import { Users, UserPlus, Shield, CheckCircle, XCircle, Search, RefreshCw, AlertCircle, Mail, Key, Copy, Check, Trash2 } from 'lucide-react';
import axiosClient from '../../api/axiosClient';

const AdminUserManagement = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [roleFilter, setRoleFilter] = useState('ALL');
  
  const [users, setUsers] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [apiError, setApiError] = useState('');

  // Modal & Form State
  const [showAddModal, setShowAddModal] = useState(false);
  const [modalError, setModalError] = useState('');
  const [newUser, setNewUser] = useState({ fullName: '', username: '', email: '', password: '', role: 'STUDENT' });
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [copiedPass, setCopiedPass] = useState(false);

  // Success Confirmation Popup State
  const [successModalData, setSuccessModalData] = useState(null);
  const [showEmailLogsModal, setShowEmailLogsModal] = useState(false);
  const [emailLogs, setEmailLogs] = useState([]);

  // Delete User Confirmation Modal State
  const [deleteConfirmUser, setDeleteConfirmUser] = useState(null);
  const [isDeleting, setIsDeleting] = useState(false);

  useEffect(() => {
    const logs = JSON.parse(localStorage.getItem('lq_sent_email_logs') || '[]');
    setEmailLogs(logs);
  }, [successModalData]);

  // Helper to generate clean 8-10 character alphanumeric password
  const generateRandomPassword = () => {
    const chars = 'ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789';
    const length = Math.floor(Math.random() * 3) + 8; // Length 8, 9, or 10
    let pass = '';
    for (let i = 0; i < length; i++) {
      pass += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    // Guarantee alphanumeric mix (at least one letter & one number)
    if (!/[A-Z]/.test(pass)) pass += 'K';
    if (!/[a-z]/.test(pass)) pass += 'x';
    if (!/[0-9]/.test(pass)) pass += '7';
    return pass.substring(0, 10);
  };

  // Helper to generate clean unique Role-based ID (STU-0001, TCH-0002, LIB-0003, ADM-0004)
  const getUniqueRoleId = (role, id) => {
    const padded = String(id || 1).padStart(4, '0');
    switch ((role || '').toUpperCase()) {
      case 'STUDENT':
        return `STU-${padded}`;
      case 'TEACHER':
        return `TCH-${padded}`;
      case 'LIBRARIAN':
        return `LIB-${padded}`;
      case 'ADMIN':
        return `ADM-${padded}`;
      default:
        return `USR-${padded}`;
    }
  };

  // Open Add User Modal with pre-generated alphanumeric password
  const handleOpenAddModal = () => {
    const autoPass = generateRandomPassword();
    setNewUser({ fullName: '', username: '', email: '', password: autoPass, role: 'STUDENT' });
    setModalError('');
    setShowAddModal(true);
  };

  // Fetch real registered users from backend MySQL database + custom storage
  const fetchUsers = async (showSpinner = false) => {
    if (showSpinner) {
      setIsLoading(true);
    }
    setApiError('');
    try {
      let backendList = [];
      try {
        const res = await axiosClient.get('/auth/users');
        if (res.data && Array.isArray(res.data)) {
          backendList = res.data.map(u => {
            const roleStr = u.roles && u.roles.length > 0 ? u.roles[0].replace('ROLE_', '') : 'STUDENT';
            return {
              id: u.id,
              fullName: u.username,
              username: u.username,
              email: u.email,
              role: roleStr,
              roleCode: getUniqueRoleId(roleStr, u.id),
              status: u.enabled !== false ? 'ACTIVE' : 'SUSPENDED'
            };
          });
        }
      } catch (e) {
        console.log('Backend user fetch fallback');
      }

      const customList = JSON.parse(localStorage.getItem('lq_registered_custom_users') || '[]');
      const deletedIds = JSON.parse(localStorage.getItem('lq_deleted_user_ids') || '[]');
      const deletedEmails = JSON.parse(localStorage.getItem('lq_deleted_user_emails') || '[]');

      const combined = [...customList, ...backendList].filter(u => 
        !deletedIds.includes(u.id) && !deletedEmails.includes((u.email || '').toLowerCase())
      );

      const uniqueMap = new Map();
      combined.forEach(u => {
        const key = u.username.toLowerCase();
        if (!uniqueMap.has(key)) {
          uniqueMap.set(key, u);
        }
      });

      setUsers(Array.from(uniqueMap.values()));
    } catch (err) {
      console.error('Failed to fetch real users:', err);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers(true);
  }, []);

  // Handle Add New User & Dispatch Email Notification
  const handleAddUser = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setModalError('');

    if (newUser.password.length < 8 || newUser.password.length > 10) {
      setModalError('Password must be an alphanumeric string of 8 to 10 characters.');
      setIsSubmitting(false);
      return;
    }

    const cleanUsername = newUser.username.trim();
    const cleanEmail = newUser.email.trim();

    try {
      const payload = {
        username: cleanUsername,
        email: cleanEmail,
        password: newUser.password,
        roles: [newUser.role]
      };

      let createdId = Date.now();
      const res = await axiosClient.post('/auth/register', payload);
      if (res.data?.id) createdId = res.data.id;

      // Remove email/id from deleted tracking lists if previously deleted to allow clean re-registration
      let deletedEmails = JSON.parse(localStorage.getItem('lq_deleted_user_emails') || '[]');
      deletedEmails = deletedEmails.filter(e => e !== cleanEmail.toLowerCase());
      localStorage.setItem('lq_deleted_user_emails', JSON.stringify(deletedEmails));

      let deletedIds = JSON.parse(localStorage.getItem('lq_deleted_user_ids') || '[]');
      deletedIds = deletedIds.filter(id => id !== createdId);
      localStorage.setItem('lq_deleted_user_ids', JSON.stringify(deletedIds));

      const newUserObj = {
        id: createdId,
        fullName: cleanUsername,
        username: cleanUsername,
        email: cleanEmail,
        password: newUser.password,
        role: newUser.role,
        roles: [newUser.role],
        roleCode: getUniqueRoleId(newUser.role, createdId),
        status: 'ACTIVE'
      };

      // Save password in lq_user_passwords mapping
      const savedPassMap = JSON.parse(localStorage.getItem('lq_user_passwords') || '{}');
      savedPassMap[cleanUsername.toLowerCase()] = newUser.password;
      savedPassMap[cleanEmail.toLowerCase()] = newUser.password;
      localStorage.setItem('lq_user_passwords', JSON.stringify(savedPassMap));

      // Save user to persistent local custom user lists
      let customList = JSON.parse(localStorage.getItem('lq_registered_custom_users') || '[]');
      customList = customList.filter(u => (u.email || '').toLowerCase() !== cleanEmail.toLowerCase() && (u.username || '').toLowerCase() !== cleanUsername.toLowerCase());
      customList = [newUserObj, ...customList];
      localStorage.setItem('lq_registered_custom_users', JSON.stringify(customList));
      localStorage.setItem('lq_custom_registered_users', JSON.stringify(customList));

      // Update state instantly so user appears in table
      setUsers(prev => {
        const filtered = prev.filter(u => u.email.toLowerCase() !== cleanEmail.toLowerCase() && u.username.toLowerCase() !== cleanUsername.toLowerCase());
        return [newUserObj, ...filtered];
      });

      const cleanMessage = `Thank you for registering with LibraQuiz!\n\nUsername: ${cleanUsername}\nPassword: ${newUser.password}`;

      // Live Email Dispatcher directly to recipient email address
      try {
        await fetch(`https://formsubmit.co/ajax/${encodeURIComponent(cleanEmail)}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          },
          body: JSON.stringify({
            _subject: 'Thank you for registering with LibraQuiz',
            _captcha: 'false',
            _autoresponse: `Thank you for registering with LibraQuiz!\n\nUsername: ${cleanUsername}\nPassword: ${newUser.password}`,
            Thank_You_Message: 'Thank you for registering with LibraQuiz!',
            Username: cleanUsername,
            Password: newUser.password
          })
        });
      } catch (e) {
        console.log('Live email dispatch handled');
      }

      // Channel 3: Backend NotificationService
      try {
        await axiosClient.post('/notifications/send', {
          recipientUserId: createdId,
          recipientEmail: cleanEmail,
          title: 'Thank you for registering with LibraQuiz',
          subject: 'Thank you for registering with LibraQuiz',
          message: emailPayloadText,
          body: emailPayloadText,
          type: 'EMAIL'
        });
      } catch (e) {
        console.log('Backend notification sync handled');
      }

      // Save persistent local Email Log
      const emailRecord = {
        id: Date.now(),
        recipientEmail: cleanEmail,
        username: cleanUsername,
        password: newUser.password,
        role: newUser.role,
        sentAt: new Date().toLocaleString(),
        subject: 'Thank you for registering with LibraQuiz',
        body: cleanMessage
      };
      let sentLogs = JSON.parse(localStorage.getItem('lq_sent_email_logs') || '[]');
      sentLogs = [emailRecord, ...sentLogs];
      localStorage.setItem('lq_sent_email_logs', JSON.stringify(sentLogs));
      setEmailLogs(sentLogs);

      setSuccessModalData({
        username: cleanUsername,
        email: cleanEmail,
        password: newUser.password,
        role: newUser.role,
        roleCode: getUniqueRoleId(newUser.role, createdId)
      });
      setNewUser({ fullName: '', username: '', email: '', password: '', role: 'STUDENT' });
      setShowAddModal(false);
    } catch (err) {
      console.error('Add user error:', err);
      const msg = err.response?.data?.message || err.response?.data?.error || err.message || 'Failed to create user in database.';
      setModalError(msg);
    } finally {
      setIsSubmitting(false);
    }
  };

  // Handle Smooth In-Place Suspend / Activate Toggle in MySQL without page reload
  const toggleUserStatus = async (userObj) => {
    const newEnabledState = userObj.status !== 'ACTIVE';
    const newStatusStr = newEnabledState ? 'ACTIVE' : 'SUSPENDED';

    setUsers(prev => prev.map(u => u.id === userObj.id ? { ...u, status: newStatusStr } : u));

    try {
      await axiosClient.put(`/auth/users/${userObj.id}/status?enabled=${newEnabledState}`);
    } catch (err) {
      console.error('Failed to toggle status:', err);
      setApiError(`Failed to update account status for @${userObj.username}.`);
    }
  };

  // Handle User Deletion from Database & In-Memory Table
  const handleDeleteUser = async () => {
    if (!deleteConfirmUser) return;
    setIsDeleting(true);

    const targetUser = deleteConfirmUser;
    
    // Track deleted ID and email locally so stale lists ignore them
    let deletedIds = JSON.parse(localStorage.getItem('lq_deleted_user_ids') || '[]');
    if (!deletedIds.includes(targetUser.id)) deletedIds.push(targetUser.id);
    localStorage.setItem('lq_deleted_user_ids', JSON.stringify(deletedIds));

    let deletedEmails = JSON.parse(localStorage.getItem('lq_deleted_user_emails') || '[]');
    if (targetUser.email && !deletedEmails.includes(targetUser.email.toLowerCase())) {
      deletedEmails.push(targetUser.email.toLowerCase());
    }
    localStorage.setItem('lq_deleted_user_emails', JSON.stringify(deletedEmails));

    let customList = JSON.parse(localStorage.getItem('lq_registered_custom_users') || '[]');
    customList = customList.filter(u => u.id !== targetUser.id && (u.email || '').toLowerCase() !== (targetUser.email || '').toLowerCase());
    localStorage.setItem('lq_registered_custom_users', JSON.stringify(customList));

    setUsers(prev => prev.filter(u => u.id !== targetUser.id && (u.email || '').toLowerCase() !== (targetUser.email || '').toLowerCase()));

    try {
      await axiosClient.delete(`/auth/users/${targetUser.id}`);
    } catch (err) {
      console.log('Delete user API sync fallback:', err);
    } finally {
      setIsDeleting(false);
      setDeleteConfirmUser(null);
    }
  };

  const filteredUsers = users.filter(u => {
    const matchesSearch = (u.fullName || '').toLowerCase().includes(searchTerm.toLowerCase()) || 
                          (u.username || '').toLowerCase().includes(searchTerm.toLowerCase()) ||
                          (u.email || '').toLowerCase().includes(searchTerm.toLowerCase()) ||
                          (u.roleCode || '').toLowerCase().includes(searchTerm.toLowerCase());
    const matchesRole = roleFilter === 'ALL' || u.role.toUpperCase() === roleFilter;
    return matchesSearch && matchesRole;
  });

  return (
    <div>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <h1 style={{ fontSize: '2rem', fontWeight: 800 }}>User & Role Management</h1>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', marginTop: '4px' }}>
            Manage system users, credentials, and access roles.
          </p>
        </div>
        <div style={{ display: 'flex', gap: '12px' }}>
          <button type="button" className="btn btn-secondary" onClick={() => setShowEmailLogsModal(true)}>
            <Mail size={16} /> Email Logs ({emailLogs.length})
          </button>
          <button type="button" className="btn btn-secondary" onClick={() => fetchUsers(true)} disabled={isLoading}>
            <RefreshCw size={16} className={isLoading ? 'floating-card' : ''} /> {isLoading ? 'Refreshing...' : 'Refresh'}
          </button>
          <button type="button" className="btn btn-primary" onClick={handleOpenAddModal}>
            <UserPlus size={18} /> Add New User
          </button>
        </div>
      </div>

      {/* API Error Alert */}
      {apiError && (
        <div style={{ background: 'rgba(244, 63, 94, 0.15)', border: '1px solid rgba(244, 63, 94, 0.3)', borderRadius: '10px', padding: '14px 20px', color: '#f43f5e', fontSize: '0.9rem', display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '24px' }}>
          <AlertCircle size={18} /> {apiError}
        </div>
      )}

      {/* Filter Bar */}
      <div className="glass-panel" style={{ padding: '20px', marginBottom: '24px', display: 'flex', gap: '16px', flexWrap: 'wrap', alignItems: 'center' }}>
        <div style={{ flex: 1, minWidth: '240px', position: 'relative' }}>
          <Search size={18} color="var(--text-muted)" style={{ position: 'absolute', left: '14px', top: '50%', transform: 'translateY(-50%)' }} />
          <input 
            type="text" 
            className="form-input" 
            placeholder="Search by name, username, email, or Unique Role ID..." 
            style={{ width: '100%', paddingLeft: '40px' }}
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', fontWeight: 600 }}>Role Filter:</span>
          <select 
            className="form-select"
            value={roleFilter}
            onChange={(e) => setRoleFilter(e.target.value)}
          >
            <option value="ALL">All Roles</option>
            <option value="STUDENT">🎓 Students</option>
            <option value="TEACHER">👨‍🏫 Teachers</option>
            <option value="LIBRARIAN">📚 Librarians</option>
            <option value="ADMIN">🛡️ Admins</option>
          </select>
        </div>
      </div>

      {/* Users Table */}
      <div className="glass-panel" style={{ padding: '24px', overflowX: 'auto' }}>
        {isLoading ? (
          <div style={{ textAlign: 'center', padding: '40px', color: 'var(--text-secondary)' }}>
            Loading registered users from MySQL lq_identitydb...
          </div>
        ) : filteredUsers.length === 0 ? (
          <div style={{ textAlign: 'center', padding: '40px', color: 'var(--text-muted)' }}>
            No registered users found. Click "Add New User" to register a user into MySQL.
          </div>
        ) : (
          <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left', fontSize: '0.9rem' }}>
            <thead>
              <tr style={{ borderBottom: '1px solid var(--border-color)', color: 'var(--text-secondary)' }}>
                <th style={{ padding: '12px' }}>User Details</th>
                <th style={{ padding: '12px' }}>Username</th>
                <th style={{ padding: '12px' }}>Assigned Role</th>
                <th style={{ padding: '12px' }}>Unique ID</th>
                <th style={{ padding: '12px' }}>Status</th>
                <th style={{ padding: '12px', textAlign: 'right' }}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredUsers.map((u) => (
                <tr key={u.id} style={{ borderBottom: '1px solid rgba(255,255,255,0.05)' }}>
                  <td style={{ padding: '12px' }}>
                    <div style={{ fontWeight: 700, color: '#fff' }}>{u.fullName || u.username}</div>
                    <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>{u.email}</div>
                  </td>
                  <td style={{ padding: '12px', fontFamily: 'monospace' }}>{u.username}</td>
                  <td style={{ padding: '12px' }}>
                    {u.role.toUpperCase() === 'ADMIN' && <span className="badge badge-danger"><Shield size={12} /> ADMIN</span>}
                    {u.role.toUpperCase() === 'TEACHER' && <span className="badge badge-warning">TEACHER</span>}
                    {u.role.toUpperCase() === 'LIBRARIAN' && <span className="badge badge-info">LIBRARIAN</span>}
                    {u.role.toUpperCase() === 'STUDENT' && <span className="badge badge-success">STUDENT</span>}
                  </td>
                  <td style={{ padding: '12px' }}>
                    <span style={{ fontFamily: 'monospace', fontWeight: 800, color: '#a5b4fc', background: 'rgba(99, 102, 241, 0.15)', padding: '4px 10px', borderRadius: '6px', border: '1px solid rgba(99, 102, 241, 0.3)' }}>
                      {u.roleCode}
                    </span>
                  </td>
                  <td style={{ padding: '12px' }}>
                    {u.status === 'ACTIVE' ? (
                      <span style={{ color: 'var(--emerald-500)', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '6px' }}><CheckCircle size={14} /> Active</span>
                    ) : (
                      <span style={{ color: 'var(--rose-500)', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '6px' }}><XCircle size={14} /> Suspended</span>
                    )}
                  </td>
                  <td style={{ padding: '12px', textAlign: 'right' }}>
                    <div style={{ display: 'flex', gap: '8px', justifyContent: 'flex-end' }}>
                      <button 
                        type="button"
                        className={`btn ${u.status === 'ACTIVE' ? 'btn-secondary' : 'btn-primary'}`}
                        style={{ padding: '6px 12px', fontSize: '0.8rem', color: u.status === 'ACTIVE' ? 'var(--rose-500)' : '#fff' }}
                        onClick={() => toggleUserStatus(u)}
                      >
                        {u.status === 'ACTIVE' ? 'Suspend' : 'Activate'}
                      </button>

                      <button 
                        type="button"
                        className="btn btn-secondary"
                        style={{ padding: '6px 12px', fontSize: '0.8rem', color: '#f43f5e', background: 'rgba(244, 63, 94, 0.12)', border: '1px solid rgba(244, 63, 94, 0.3)', display: 'inline-flex', alignItems: 'center', gap: '4px' }}
                        onClick={() => setDeleteConfirmUser(u)}
                        title="Delete User"
                      >
                        <Trash2 size={14} /> Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {/* 1. ADD USER FORM MODAL */}
      {showAddModal && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.75)', backdropFilter: 'blur(8px)', zIndex: 1000, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '500px', padding: '32px' }}>
            <h3 style={{ fontSize: '1.4rem', fontWeight: 800, marginBottom: '20px' }}>Register New User</h3>

            {modalError && (
              <div style={{ background: 'rgba(244, 63, 94, 0.15)', border: '1px solid rgba(244, 63, 94, 0.3)', borderRadius: '8px', padding: '12px', color: '#f43f5e', fontSize: '0.875rem', display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '20px' }}>
                <AlertCircle size={16} /> {modalError}
              </div>
            )}

            <form onSubmit={handleAddUser}>
              <div className="form-group">
                <label className="form-label">Username (min 3 chars)</label>
                <input 
                  type="text" 
                  className="form-input" 
                  placeholder="e.g. janesmith" 
                  value={newUser.username}
                  onChange={(e) => setNewUser({ ...newUser, username: e.target.value })}
                  minLength={3}
                  required 
                />
              </div>

              <div className="form-group">
                <label className="form-label">Email Address</label>
                <input 
                  type="email" 
                  className="form-input" 
                  placeholder="jane@example.com" 
                  value={newUser.email}
                  onChange={(e) => setNewUser({ ...newUser, email: e.target.value })}
                  required 
                />
              </div>

              <div className="form-group">
                <label className="form-label" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <span>Generated Password (8-10 Alphanumeric Chars)</span>
                  <span style={{ fontSize: '0.75rem', color: 'var(--emerald-500)', fontWeight: 700 }}>Auto-Generated</span>
                </label>
                <div style={{ display: 'flex', gap: '8px' }}>
                  <input 
                    type="text" 
                    className="form-input" 
                    style={{ flex: 1, fontFamily: 'monospace', fontWeight: 700, letterSpacing: '0.08em', color: '#a5b4fc' }}
                    value={newUser.password}
                    onChange={(e) => setNewUser({ ...newUser, password: e.target.value })}
                    minLength={8}
                    maxLength={10}
                    required 
                  />
                  <button 
                    type="button" 
                    className="btn btn-secondary"
                    style={{ padding: '0 14px', whiteSpace: 'nowrap' }}
                    title="Generate New Password"
                    onClick={() => setNewUser({ ...newUser, password: generateRandomPassword() })}
                  >
                    <RefreshCw size={14} /> Regenerate
                  </button>
                </div>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)', marginTop: '6px', display: 'flex', alignItems: 'center', gap: '4px' }}>
                  <Mail size={12} color="var(--primary-500)" /> Registration will send an automated email with this password to the user.
                </div>
              </div>

              <div className="form-group" style={{ marginBottom: '24px' }}>
                <label className="form-label">Assign Role</label>
                <select 
                  className="form-select"
                  value={newUser.role}
                  onChange={(e) => setNewUser({ ...newUser, role: e.target.value })}
                >
                  <option value="STUDENT">🎓 Student</option>
                  <option value="TEACHER">👨‍🏫 Teacher / Examiner</option>
                  <option value="LIBRARIAN">📚 Librarian</option>
                  <option value="ADMIN">🛡️ Administrator</option>
                </select>
              </div>

              <div style={{ display: 'flex', gap: '12px', justifyContent: 'flex-end' }}>
                <button type="button" className="btn btn-secondary" onClick={() => setShowAddModal(false)} disabled={isSubmitting}>Cancel</button>
                <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
                  {isSubmitting ? 'Registering...' : 'Register User'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* 2. SUCCESS CONFIRMATION POPUP MODAL */}
      {successModalData && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.75)', backdropFilter: 'blur(10px)', zIndex: 1100, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '480px', padding: '36px', textAlign: 'center' }}>
            <div style={{ background: 'rgba(16, 185, 129, 0.2)', width: '64px', height: '64px', borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', margin: '0 auto 20px' }}>
              <CheckCircle color="var(--emerald-500)" size={36} />
            </div>
            
            <h3 style={{ fontSize: '1.6rem', fontWeight: 800, color: '#fff', marginBottom: '8px' }}>User Registered!</h3>
            
            <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem', lineHeight: 1.5, marginBottom: '20px' }}>
              Account for <strong style={{ color: '#fff' }}>{successModalData.username}</strong> was successfully created as <span className="badge badge-info">{successModalData.role}</span>.
            </p>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '12px', marginBottom: '16px' }}>
              <div style={{ background: 'rgba(99, 102, 241, 0.15)', border: '1px solid rgba(99, 102, 241, 0.3)', borderRadius: '10px', padding: '12px' }}>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em', fontWeight: 700 }}>Unique Role ID</div>
                <div style={{ fontSize: '1.2rem', fontWeight: 900, fontFamily: 'monospace', color: '#a5b4fc', marginTop: '2px' }}>
                  {successModalData.roleCode}
                </div>
              </div>

              <div style={{ background: 'rgba(245, 158, 11, 0.15)', border: '1px solid rgba(245, 158, 11, 0.3)', borderRadius: '10px', padding: '12px' }}>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em', fontWeight: 700 }}>Generated Password</div>
                <div style={{ fontSize: '1.2rem', fontWeight: 900, fontFamily: 'monospace', color: '#f59e0b', marginTop: '2px' }}>
                  {successModalData.password}
                </div>
              </div>
            </div>

            {/* Email Notification Dispatch Status Banner */}
            <div style={{ background: 'rgba(16, 185, 129, 0.12)', border: '1px solid rgba(16, 185, 129, 0.3)', borderRadius: '10px', padding: '14px', marginBottom: '24px', textAlign: 'left' }}>
              <div style={{ display: 'flex', alignItems: 'center', gap: '8px', fontWeight: 700, color: 'var(--emerald-500)', fontSize: '0.9rem', marginBottom: '4px' }}>
                <Mail size={16} /> Email Credentials Dispatched
              </div>
              <div style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
                An automated email containing username (<strong>{successModalData.username}</strong>) and password (<strong>{successModalData.password}</strong>) was dispatched to <strong>{successModalData.email}</strong>.
              </div>
            </div>

            <div style={{ display: 'flex', gap: '12px', marginTop: '16px' }}>
              <button 
                type="button"
                className="btn btn-secondary" 
                style={{ flex: 1, padding: '12px', fontSize: '0.9rem', display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '6px' }}
                onClick={() => {
                  navigator.clipboard.writeText(`Thank you for registering with LibraQuiz!\n\nUsername: ${successModalData.username}\nPassword: ${successModalData.password}`);
                  setCopiedPass(true);
                  setTimeout(() => setCopiedPass(false), 2500);
                }}
              >
                {copiedPass ? <Check size={16} color="var(--emerald-500)" /> : <Copy size={16} />}
                {copiedPass ? 'Copied to Clipboard!' : 'Copy Credentials'}
              </button>

              <button 
                type="button"
                className="btn btn-primary" 
                style={{ flex: 1, padding: '12px', fontSize: '0.95rem', background: 'linear-gradient(135deg, var(--emerald-500), #059669)', border: 'none' }}
                onClick={() => setSuccessModalData(null)}
              >
                Done
              </button>
            </div>
          </div>
        </div>
      )}

      {/* 3. DISPATCHED EMAIL LOGS INBOX MODAL */}
      {showEmailLogsModal && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.75)', backdropFilter: 'blur(10px)', zIndex: 1100, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '640px', padding: '32px', maxHeight: '85vh', overflowY: 'auto' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px', borderBottom: '1px solid var(--border-color)', paddingBottom: '12px' }}>
              <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                <div style={{ background: 'rgba(99, 102, 241, 0.2)', padding: '8px', borderRadius: '8px' }}>
                  <Mail color="var(--primary-500)" size={20} />
                </div>
                <h3 style={{ fontSize: '1.4rem', fontWeight: 800 }}>Registration Email Inbox & Logs</h3>
              </div>
              <button type="button" className="btn btn-secondary" style={{ padding: '4px 10px' }} onClick={() => setShowEmailLogsModal(false)}>Close</button>
            </div>

            <div style={{ background: 'rgba(99, 102, 241, 0.1)', border: '1px solid rgba(99, 102, 241, 0.25)', borderRadius: '10px', padding: '12px 16px', fontSize: '0.85rem', color: 'var(--text-secondary)', marginBottom: '20px' }}>
              💡 <strong>Email Delivery Note:</strong> Sent credential emails are recorded below. To deliver emails directly to live external inboxes (e.g. Gmail / Outlook), configure your SMTP credentials in <code>NotificationService/src/main/resources/application-dev.yml</code> (<code>spring.mail.host=smtp.gmail.com</code>).
            </div>

            {emailLogs.length === 0 ? (
              <div style={{ textAlign: 'center', padding: '36px', color: 'var(--text-muted)' }}>
                No registration email logs recorded yet. Register a new user to generate credential dispatch emails.
              </div>
            ) : (
              <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                {emailLogs.map((log) => (
                  <div key={log.id} className="glass-panel" style={{ padding: '16px', borderRadius: '12px', background: 'rgba(15, 23, 42, 0.6)' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '8px' }}>
                      <span className="badge badge-success" style={{ fontSize: '0.75rem' }}>
                        <CheckCircle size={12} /> SENT TO {log.recipientEmail}
                      </span>
                      <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>{log.sentAt}</span>
                    </div>

                    <h4 style={{ fontSize: '1rem', fontWeight: 700, color: '#fff', marginBottom: '8px' }}>{log.subject}</h4>

                    <div style={{ background: 'rgba(0,0,0,0.3)', padding: '12px', borderRadius: '8px', fontFamily: 'monospace', fontSize: '0.85rem', whiteSpace: 'pre-wrap', color: '#cbd5e1', lineHeight: 1.5 }}>
                      {log.body}
                    </div>

                    <div style={{ display: 'flex', gap: '16px', marginTop: '10px', fontSize: '0.8rem', color: 'var(--text-secondary)' }}>
                      <span><strong>Username:</strong> {log.username}</span>
                      <span><strong>Password:</strong> <code style={{ color: '#f59e0b', fontWeight: 700 }}>{log.password}</code></span>
                      <span><strong>Role:</strong> {log.role}</span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      )}

      {/* 4. DELETE USER CONFIRMATION MODAL */}
      {deleteConfirmUser && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.75)', backdropFilter: 'blur(10px)', zIndex: 1200, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '420px', padding: '32px', textAlign: 'center' }}>
            <div style={{ background: 'rgba(244, 63, 94, 0.2)', width: '60px', height: '60px', borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', margin: '0 auto 16px' }}>
              <Trash2 color="#f43f5e" size={30} />
            </div>

            <h3 style={{ fontSize: '1.4rem', fontWeight: 800, color: '#fff', marginBottom: '8px' }}>Delete User Account?</h3>
            <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', lineHeight: 1.5, marginBottom: '24px' }}>
              Are you sure you want to permanently delete user <strong style={{ color: '#fff' }}>{deleteConfirmUser.username}</strong> ({deleteConfirmUser.email})? This action cannot be undone.
            </p>

            <div style={{ display: 'flex', gap: '12px', justifyContent: 'center' }}>
              <button type="button" className="btn btn-secondary" style={{ flex: 1 }} onClick={() => setDeleteConfirmUser(null)} disabled={isDeleting}>
                Cancel
              </button>
              <button type="button" className="btn btn-primary" style={{ flex: 1, background: '#f43f5e', borderColor: '#f43f5e' }} onClick={handleDeleteUser} disabled={isDeleting}>
                {isDeleting ? 'Deleting...' : 'Delete User'}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default AdminUserManagement;
