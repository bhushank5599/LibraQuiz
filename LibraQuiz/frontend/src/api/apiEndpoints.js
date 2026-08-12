export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: '/auth/login',
    REGISTER: '/auth/register',
    REFRESH: '/auth/refresh',
    LOGOUT: '/auth/logout',
  },
  USERS: {
    PROFILE: '/users',
    IDENTITY: (id) => `/users/identity/${id}`,
  },
  CATEGORIES: {
    BASE: '/categories',
    TREE: '/categories/tree',
  },
  BOOKS: {
    BASE: '/books',
    SEARCH: '/books/search',
    COPIES: '/books/copies',
    COPY_CODE: (code) => `/books/copies/code/${code}`,
  },
  TRANSACTIONS: {
    ISSUE: '/transactions/issue',
    RETURN: '/transactions/return',
    RENEW: (id) => `/transactions/renew/${id}`,
    USER_BORROWS: (userId) => `/transactions/user/${userId}`,
    OVERDUE: '/transactions/overdue',
    FINES: (userId) => `/transactions/fines/user/${userId}`,
    PAY_FINE: (id) => `/transactions/fines/${id}/pay`,
  },
  COURSES: {
    BASE: '/courses',
    MODULES: '/courses/modules',
    LESSONS: '/courses/lessons',
  },
  QUESTIONS: {
    BASE: '/questions',
  },
  QUESTION_MGMT: {
    SUBMIT: (id) => `/question-management/submit/${id}`,
    REVIEW: '/question-management/review',
    PENDING: '/question-management/pending',
  },
  QUIZZES: {
    BASE: '/quizzes',
    PUBLISHED: '/quizzes/published',
    PUBLISH: (id) => `/quizzes/${id}/publish`,
  },
  EXAMS: {
    START: '/exams/start',
    SUBMIT: '/exams/submit',
    ATTEMPT: (id) => `/exams/attempt/${id}`,
    USER_ATTEMPTS: (userId) => `/exams/user/${userId}`,
  },
  RESULTS: {
    ATTEMPT: (id) => `/results/attempt/${id}`,
    USER: (userId) => `/results/user/${userId}`,
  },
  AI: {
    SEARCH: '/ai/search',
    GENERATE_QUESTIONS: '/ai/questions/generate',
    DRAFTS: (userId) => `/ai/questions/drafts/${userId}`,
    RECOMMENDATIONS: (userId) => `/ai/recommendations/${userId}`,
    STUDY_PLAN: '/ai/study-plan',
  },
  NOTIFICATIONS: {
    USER: (userId) => `/notifications/user/${userId}`,
  }
};
