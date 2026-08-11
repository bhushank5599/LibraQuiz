import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axiosClient from '../../api/axiosClient';
import { API_ENDPOINTS } from '../../api/apiEndpoints';

export const loginUser = createAsyncThunk('auth/login', async (credentials, { rejectWithValue }) => {
  const inputUser = (credentials.usernameOrEmail || '').trim();
  const inputUserLower = inputUser.toLowerCase();
  const inputPass = credentials.password;

  const customPassMap = JSON.parse(localStorage.getItem('lq_user_passwords') || '{}');
  const list1 = JSON.parse(localStorage.getItem('lq_registered_custom_users') || '[]');
  const list2 = JSON.parse(localStorage.getItem('lq_custom_registered_users') || '[]');
  const customUsers = [...list1, ...list2];

  const foundLocalUser = customUsers.find(u => 
    (u.username || '').toLowerCase() === inputUserLower || (u.email || '').toLowerCase() === inputUserLower
  );

  const rawRole = (foundLocalUser?.role || (foundLocalUser?.roles && foundLocalUser.roles[0]) || '').toUpperCase().replace('ROLE_', '');
  const roleStr = (rawRole === 'TEACHER' || rawRole === 'LIBRARIAN' || rawRole === 'ADMIN' || rawRole === 'STUDENT') 
    ? rawRole 
    : 'STUDENT';

  // Check expected password (custom updated password takes precedence over original auto-generated password)
  let expectedPass = customPassMap[inputUserLower];
  if (!expectedPass && foundLocalUser) {
    const uName = (foundLocalUser.username || '').toLowerCase();
    const uEmail = (foundLocalUser.email || '').toLowerCase();
    expectedPass = customPassMap[uName] || customPassMap[uEmail] || foundLocalUser.password;
  }

  // If password match is found
  if (expectedPass && expectedPass === inputPass) {
    const mockUserData = {
      id: foundLocalUser ? (foundLocalUser.id || 101) : 101,
      username: foundLocalUser ? foundLocalUser.username : inputUser,
      email: foundLocalUser ? foundLocalUser.email : (inputUser.includes('@') ? inputUser : `${inputUser}@libraquiz.com`),
      fullName: foundLocalUser ? (foundLocalUser.fullName || foundLocalUser.username) : inputUser,
      roles: [roleStr],
      role: roleStr,
      accessToken: 'mock_stable_token_' + (foundLocalUser ? foundLocalUser.id : '101'),
      refreshToken: 'mock_stable_refresh_' + (foundLocalUser ? foundLocalUser.id : '101')
    };

    localStorage.setItem('token', mockUserData.accessToken);
    localStorage.setItem('refreshToken', mockUserData.refreshToken);
    localStorage.setItem('user', JSON.stringify(mockUserData));
    return mockUserData;
  }

  // If user exists locally and password typed is incorrect
  if (expectedPass && expectedPass !== inputPass) {
    return rejectWithValue('Invalid username/email or password');
  }

  // Fallback to backend API endpoint
  try {
    const response = await axiosClient.post(API_ENDPOINTS.AUTH.LOGIN, {
      usernameOrEmail: inputUser,
      password: inputPass,
    });
    localStorage.setItem('token', response.data.accessToken);
    localStorage.setItem('refreshToken', response.data.refreshToken);
    localStorage.setItem('user', JSON.stringify(response.data));
    return response.data;
  } catch (err) {
    console.error('Login error:', err.response?.data || err.message);
    const message = err.response?.data?.message || err.response?.data?.error || err.message || 'Invalid username/email or password';
    return rejectWithValue(message);
  }
});

export const registerUser = createAsyncThunk('auth/register', async (userData, { rejectWithValue }) => {
  try {
    const response = await axiosClient.post(API_ENDPOINTS.AUTH.REGISTER, {
      ...userData,
      username: userData.username?.trim(),
      email: userData.email?.trim(),
    });
    return response.data;
  } catch (err) {
    console.error('Registration error:', err.response?.data || err.message);
    const message = err.response?.data?.message || err.response?.data?.error || err.message || 'Registration failed';
    return rejectWithValue(message);
  }
});

const initialUser = JSON.parse(localStorage.getItem('user') || 'null');

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    user: initialUser,
    token: localStorage.getItem('token') || null,
    isLoading: false,
    error: null,
    registrationSuccess: false,
  },
  reducers: {
    logout: (state) => {
      localStorage.removeItem('token');
      localStorage.removeItem('refreshToken');
      localStorage.removeItem('user');
      state.user = null;
      state.token = null;
      state.error = null;
      state.registrationSuccess = false;
    },
    clearRegistrationSuccess: (state) => {
      state.registrationSuccess = false;
    },
    clearAuthError: (state) => {
      state.error = null;
    }
  },
  extraReducers: (builder) => {
    builder
      // LOGIN
      .addCase(loginUser.pending, (state) => { state.isLoading = true; state.error = null; })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.isLoading = false;
        state.user = action.payload;
        state.token = action.payload.accessToken;
        state.error = null;
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      // REGISTER
      .addCase(registerUser.pending, (state) => { state.isLoading = true; state.error = null; state.registrationSuccess = false; })
      .addCase(registerUser.fulfilled, (state) => {
        state.isLoading = false;
        state.registrationSuccess = true;
        state.error = null;
      })
      .addCase(registerUser.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
        state.registrationSuccess = false;
      });
  },
});

export const { logout, clearRegistrationSuccess, clearAuthError } = authSlice.actions;
export default authSlice.reducer;
