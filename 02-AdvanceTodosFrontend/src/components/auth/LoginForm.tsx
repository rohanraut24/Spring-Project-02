import { useState } from "react";
import { useAuth } from "../../contexts/AuthContext";
import { Input } from "../common/Input";
import { Button } from "../common/Button";

export const LoginForm: React.FC<{ onToggle: () => void }> = ({ onToggle }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const { login } = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError('');

    try {
      await login(username, password);
    } catch (error) {
      setError(error instanceof Error ? error.message : 'Login failed');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="w-full max-w-md mx-auto">
      <div className="bg-white dark:bg-gray-800 rounded-xl shadow-2xl p-8">
        <h2 className="text-3xl font-bold text-center mb-8 text-gray-900 dark:text-white">Welcome Back</h2>
        
        {error && (
          <div className="bg-red-100 dark:bg-red-900 border border-red-400 text-red-700 dark:text-red-200 px-4 py-3 rounded mb-4">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Username</label>
            <Input
              value={username}
              onChange={setUsername}
              placeholder="Enter your username"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Password</label>
            <Input
              type="password"
              value={password}
              onChange={setPassword}
              placeholder="Enter your password"
              required
            />
          </div>

          <Button type="submit" disabled={isLoading} className="w-full">
            {isLoading ? 'Signing in...' : 'Sign In'}
          </Button>
        </form>

        <p className="text-center mt-6 text-gray-600 dark:text-gray-400">
          Don't have an account?{' '}
          <button onClick={onToggle} className="text-blue-600 dark:text-blue-400 hover:underline font-medium">
            Sign up
          </button>
        </p>
      </div>
    </div>
  );
};
