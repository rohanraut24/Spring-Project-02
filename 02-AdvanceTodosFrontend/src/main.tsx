import React from 'react';
import ReactDOM from 'react-dom/client';
import { Search, Plus, Edit2, Trash2, Check, X, User, Settings, LogOut, Moon, Sun, Filter, BarChart3 } from 'lucide-react';
import './index.css';

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