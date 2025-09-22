import { Check, LogOut, Moon, Sun,} from "lucide-react";
import { useTheme } from "../../contexts/ThemeContext";
import type { User } from "../../types/auth.types";



export const Header: React.FC<{
  user: User;
  onLogout: () => void;
}> = ({ user, onLogout }) => {
  const { isDark, toggleTheme } = useTheme();

  return (
    <header className="bg-white dark:bg-gray-800 shadow-sm border-b border-gray-200 dark:border-gray-700">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center">
              <Check className="text-white" size={20} />
            </div>
            <h1 className="text-xl font-bold text-gray-900 dark:text-white">TodoApp</h1>
          </div>
          
          <div className="flex items-center gap-4">
            <button
              onClick={toggleTheme}
              className="p-2 text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200 transition-colors"
            >
              {isDark ? <Sun size={20} /> : <Moon size={20} />}
            </button>
            
            <div className="flex items-center gap-3">
              <div className="w-8 h-8 bg-blue-600 rounded-full flex items-center justify-center">
                <User className="text-white" size={16} />
              </div>
              <span className="text-sm font-medium text-gray-700 dark:text-gray-300">
                {user.username}
              </span>
              <button
                onClick={onLogout}
                className="p-2 text-gray-500 hover:text-red-600 dark:text-gray-400 dark:hover:text-red-400 transition-colors"
                title="Logout"
              >
                <LogOut size={16} />
              </button>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
};