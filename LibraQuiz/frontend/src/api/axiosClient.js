import axios from 'axios';

const axiosClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

axiosClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

axiosClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      const token = localStorage.getItem('token');
      if (token && token.startsWith('mock_')) {
        return Promise.reject(error);
      }
      const refreshToken = localStorage.getItem('refreshToken');
      if (refreshToken && !refreshToken.startsWith('mock_')) {
        try {
          const res = await axios.post(`${import.meta.env.VITE_API_BASE_URL || '/api'}/auth/refresh`, { refreshToken });
          if (res.status === 200) {
            localStorage.setItem('token', res.data.accessToken);
            axiosClient.defaults.headers.common['Authorization'] = `Bearer ${res.data.accessToken}`;
            return axiosClient(originalRequest);
          }
        } catch (e) {
          localStorage.removeItem('token');
          localStorage.removeItem('refreshToken');
          localStorage.removeItem('user');
          window.location.href = '/login';
        }
      }
    }
    return Promise.reject(error);
  }
);

export default axiosClient;
