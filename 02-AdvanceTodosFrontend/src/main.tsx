import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { ThemeProvider } from './contexts/ThemeContext';
import { AuthProvider } from './contexts/AuthContext';
import { App } from './App';

// Root Component with Providers
const TodoApp: React.FC = () => {
  return (
    <ThemeProvider>
      <AuthProvider>
        <div className="dark:bg-gray-900 transition-colors duration-200">
          <App />
        </div>
      </AuthProvider>
    </ThemeProvider>
  );
};

// Render the application
ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <TodoApp />
  </React.StrictMode>
);

export default TodoApp;