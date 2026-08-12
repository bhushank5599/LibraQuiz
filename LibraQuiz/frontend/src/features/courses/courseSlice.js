import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axiosClient from '../../api/axiosClient';
import { API_ENDPOINTS } from '../../api/apiEndpoints';

export const fetchCourses = createAsyncThunk('courses/fetchAll', async (_, { rejectWithValue }) => {
  try {
    const response = await axiosClient.get(API_ENDPOINTS.COURSES.BASE);
    return response.data;
  } catch (err) {
    return rejectWithValue(err.response?.data?.message || 'Failed to fetch courses');
  }
});

const courseSlice = createSlice({
  name: 'courses',
  initialState: { courses: [], isLoading: false, error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCourses.pending, (state) => { state.isLoading = true; })
      .addCase(fetchCourses.fulfilled, (state, action) => {
        state.isLoading = false;
        state.courses = action.payload;
      })
      .addCase(fetchCourses.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      });
  },
});

export default courseSlice.reducer;
