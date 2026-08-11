import { configureStore } from '@reduxjs/toolkit';
import authReducer from '../features/auth/authSlice';
import bookReducer from '../features/books/bookSlice';
import courseReducer from '../features/courses/courseSlice';
import quizReducer from '../features/quizzes/quizSlice';
import aiReducer from '../features/ai/aiSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    books: bookReducer,
    courses: courseReducer,
    quizzes: quizReducer,
    ai: aiReducer,
  },
});
