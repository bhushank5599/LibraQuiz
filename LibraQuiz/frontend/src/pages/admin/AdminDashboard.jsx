import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { Users, FolderTree, ShieldCheck } from 'lucide-react';
import axiosClient from '../../api/axiosClient';

const AdminDashboard = () => {
  const { user } = useSelector((state) => state.auth);
  const [userCount, setUserCount] = useState(0);
  const [isLoading, setIsLoading] = useState(true);

  // Fetch real registered user count from MySQL lq_identitydb
  useEffect(() => {
    const fetchUserCount = async () => {
      try {
        const res = await axiosClient.get('/auth/users');
        if (res.data && Array.isArray(res.data)) {
          setUserCount(res.data.length);
        }
      } catch (err) {
        console.error('Failed to fetch user count:', err);
      } finally {
        setIsLoading(false);
      }
    };
    fetchUserCount();
  }, []);

  return (
    <div>
      {/* Header */}
      <div style={{ marginBottom: '32px' }}>
        <div className="badge badge-info" style={{ marginBottom: '8px' }}>
          <ShieldCheck size={14} /> System Administrator Console
        </div>
        <h1 style={{ fontSize: '2.2rem', fontWeight: 800 }}>Platform Administration</h1>
        <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem', marginTop: '4px' }}>
          Manage registered users, role permissions, and system taxonomy.
        </p>
      </div>

      {/* Overview Stats Cards Grid */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '24px', marginBottom: '36px' }}>
        {/* Total Real Platform Users Card */}
        <div className="glass-panel" style={{ padding: '24px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
            <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', fontWeight: 600 }}>Total Registered Users</span>
            <div style={{ background: 'rgba(99, 102, 241, 0.15)', padding: '10px', borderRadius: '10px' }}>
              <Users color="var(--primary-500)" size={22} />
            </div>
          </div>
          <h2 style={{ fontSize: '2.4rem', fontWeight: 800, color: '#fff' }}>
            {isLoading ? '...' : userCount}
          </h2>
          <span style={{ fontSize: '0.75rem', color: 'var(--emerald-500)', fontWeight: 700 }}>Active Platform Accounts</span>
        </div>
      </div>

      {/* Quick Action Navigation Grid */}
      <h3 style={{ fontSize: '1.3rem', fontWeight: 800, marginBottom: '20px' }}>Admin Operations</h3>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '24px', marginBottom: '40px' }}>
        
        <Link to="/admin/users" className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '12px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
            <div style={{ background: 'rgba(99, 102, 241, 0.2)', padding: '12px', borderRadius: '12px' }}>
              <Users color="var(--primary-500)" size={24} />
            </div>
            <div>
              <h4 style={{ fontSize: '1.1rem', fontWeight: 700 }}>User & Role Management</h4>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Manage accounts, register users, assign roles (Student, Teacher, Librarian, Admin), and suspend/activate accounts.</p>
            </div>
          </div>
          <div style={{ display: 'flex', alignItems: 'center', gap: '6px', color: 'var(--primary-500)', fontWeight: 600, fontSize: '0.875rem', marginTop: '8px' }}>
            Open User Management &rarr;
          </div>
        </Link>

      </div>
    </div>
  );
};

export default AdminDashboard;
