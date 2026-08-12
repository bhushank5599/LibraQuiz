import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axiosClient from '../../api/axiosClient';
import { API_ENDPOINTS } from '../../api/apiEndpoints';

export const fetchPublishedQuizzes = createAsyncThunk('quizzes/fetchPublished', async (_, { rejectWithValue }) => {
  try {
    const response = await axiosClient.get(API_ENDPOINTS.QUIZZES.PUBLISHED);
    return response.data;
  } catch (err) {
    return rejectWithValue(err.response?.data?.message || 'Failed to fetch quizzes');
  }
});

const quizSlice = createSlice({
  name: 'quizzes',
  initialState: { publishedQuizzes: [], isLoading: false, error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchPublishedQuizzes.pending, (state) => { state.isLoading = true; })
      .addCase(fetchPublishedQuizzes.fulfilled, (state, action) => {
        state.isLoading = false;
        state.publishedQuizzes = action.payload;
      })
      .addCase(fetchPublishedQuizzes.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      });
  },
});

export default quizSlice.reducer;
