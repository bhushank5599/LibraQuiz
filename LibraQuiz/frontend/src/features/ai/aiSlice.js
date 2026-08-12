import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axiosClient from '../../api/axiosClient';
import { API_ENDPOINTS } from '../../api/apiEndpoints';

export const performAISearch = createAsyncThunk('ai/search', async (promptData, { rejectWithValue }) => {
  try {
    const response = await axiosClient.post(API_ENDPOINTS.AI.SEARCH, promptData);
    return response.data;
  } catch (err) {
    return rejectWithValue(err.response?.data?.message || 'AI Search failed');
  }
});

const aiSlice = createSlice({
  name: 'ai',
  initialState: { searchResults: null, isLoading: false, error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(performAISearch.pending, (state) => { state.isLoading = true; })
      .addCase(performAISearch.fulfilled, (state, action) => {
        state.isLoading = false;
        state.searchResults = action.payload;
      })
      .addCase(performAISearch.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      });
  },
});

export default aiSlice.reducer;
