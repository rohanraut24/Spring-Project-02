import React, { useState, useEffect} from 'react';
import { Search, Plus, Trash2, Check, X, BarChart3 } from 'lucide-react';
import { Button } from '../components/common/Button';
import { Toast } from '../components/common/Toast';
import type { Todo, TodoStats } from '../types/todo.types';
import { useAuth } from '../contexts/AuthContext';
import { apiService } from '../services/api.service';
import { StatsCard } from '../components/dashboard/StatsCard';
import { Header } from '../components/layout/Header';
import { Input } from '../components/common/Input';
import { TodoItem } from '../components/dashboard/TodoItem';
import { CreateTodoModal } from '../components/dashboard/CreateTodoModel';

export const Dashboard: React.FC = () => {
  const { user, logout } = useAuth();
  const [todos, setTodos] = useState<Todo[]>([]);
  const [stats, setStats] = useState<TodoStats>({ totalTodos: 0, completedTodos: 0, pendingTodos: 0 });
  const [filteredTodos, setFilteredTodos] = useState<Todo[]>([]);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [editTodo, setEditTodo] = useState<Todo | null>(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [filter, setFilter] = useState<'all' | 'pending' | 'completed'>('all');
  const [toast, setToast] = useState<{ message: string; type: 'success' | 'error' } | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    loadTodos();
    loadStats();
  }, []);

  useEffect(() => {
    filterTodos();
  }, [todos, searchQuery, filter]);

  const loadTodos = async () => {
    try {
      setIsLoading(true);
      const todosData = await apiService.getTodos();
      setTodos(todosData);
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      showToast('Failed to load todos', 'error');
    } finally {
      setIsLoading(false);
    }
  };

  const loadStats = async () => {
    try {
      const statsData = await apiService.getTodoStats();
      setStats(statsData);
    } catch (error) {
      console.error('Failed to load stats:', error);
    }
  };

  const filterTodos = () => {
    let filtered = todos;

    // Apply status filter
    if (filter === 'pending') {
      filtered = filtered.filter(todo => !todo.completed);
    } else if (filter === 'completed') {
      filtered = filtered.filter(todo => todo.completed);
    }

    // Apply search filter
    if (searchQuery.trim()) {
      filtered = filtered.filter(todo =>
        todo.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
        todo.description.toLowerCase().includes(searchQuery.toLowerCase())
      );
    }

    setFilteredTodos(filtered);
  };

  const showToast = (message: string, type: 'success' | 'error') => {
    setToast({ message, type });
  };

  const handleCreateTodo = async (title: string, description: string) => {
    try {
      const newTodo = await apiService.createTodo(title, description);
      setTodos(prev => [newTodo, ...prev]);
      loadStats();
      showToast('Todo created successfully!', 'success');
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      showToast('Failed to create todo', 'error');
    }
  };

  const handleUpdateTodo = async (title: string, description: string) => {
    if (!editTodo) return;

    try {
      const updatedTodo = await apiService.updateTodo(editTodo.id, { 
        title, 
        description,
        completed: editTodo.completed 
      });
      setTodos(prev => prev.map(todo => todo.id === editTodo.id ? updatedTodo : todo));
      setEditTodo(null);
      showToast('Todo updated successfully!', 'success');
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      showToast('Failed to update todo', 'error');
    }
  };

  const handleToggleTodo = async (id: number) => {
    try {
      await apiService.toggleTodo(id);
      setTodos(prev => prev.map(todo => 
        todo.id === id ? { ...todo, completed: !todo.completed } : todo
      ));
      loadStats();
      showToast('Todo updated!', 'success');
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      showToast('Failed to toggle todo', 'error');
    }
  };

  const handleDeleteTodo = async (id: number) => {
    if (!confirm('Are you sure you want to delete this todo?')) return;

    try {
      await apiService.deleteTodo(id);
      setTodos(prev => prev.filter(todo => todo.id !== id));
      loadStats();
      showToast('Todo deleted successfully!', 'success');
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      showToast('Failed to delete todo', 'error');
    }
  };

  const handleDeleteCompleted = async () => {
    if (!confirm('Are you sure you want to delete all completed todos?')) return;

    try {
      await apiService.deleteCompletedTodos();
      setTodos(prev => prev.filter(todo => !todo.completed));
      loadStats();
      showToast('Completed todos deleted!', 'success');
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      showToast('Failed to delete completed todos', 'error');
    }
  };

  if (!user) return null;

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900">
      <Header user={user} onLogout={logout} />
      
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Stats Section */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <StatsCard
            title="Total Todos"
            value={stats.totalTodos}
            icon={<BarChart3 size={24} />}
            color="border-blue-500"
          />
          <StatsCard
            title="Completed"
            value={stats.completedTodos}
            icon={<Check size={24} />}
            color="border-green-500"
          />
          <StatsCard
            title="Pending"
            value={stats.pendingTodos}
            icon={<X size={24} />}
            color="border-orange-500"
          />
        </div>

        {/* Actions Section */}
        <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 mb-8">
          <div className="flex flex-col sm:flex-row gap-4 items-start sm:items-center justify-between">
            <div className="flex flex-col sm:flex-row gap-4 flex-1">
              {/* Search */}
              <div className="relative flex-1 max-w-md">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 dark:text-gray-500" size={18} />
                <Input
                  placeholder="Search todos..."
                  value={searchQuery}
                  onChange={setSearchQuery}
                  className="pl-10"
                />
              </div>

              {/* Filter */}
              <div className="flex gap-2">
                <Button
                  variant={filter === 'all' ? 'primary' : 'secondary'}
                  size="sm"
                  onClick={() => setFilter('all')}
                >
                  All ({stats.totalTodos})
                </Button>
                <Button
                  variant={filter === 'pending' ? 'primary' : 'secondary'}
                  size="sm"
                  onClick={() => setFilter('pending')}
                >
                  Pending ({stats.pendingTodos})
                </Button>
                <Button
                  variant={filter === 'completed' ? 'primary' : 'secondary'}
                  size="sm"
                  onClick={() => setFilter('completed')}
                >
                  Completed ({stats.completedTodos})
                </Button>
              </div>
            </div>

            {/* Action Buttons */}
            <div className="flex gap-3">
              {stats.completedTodos > 0 && (
                <Button variant="danger" size="sm" onClick={handleDeleteCompleted}>
                  <Trash2 size={16} />
                  Clear Completed
                </Button>
              )}
              <Button onClick={() => setIsCreateModalOpen(true)}>
                <Plus size={18} />
                Add Todo
              </Button>
            </div>
          </div>
        </div>

        {/* Todos Section */}
        <div className="space-y-4">
          {isLoading ? (
            <div className="flex items-center justify-center py-12">
              <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
              <span className="ml-3 text-gray-600 dark:text-gray-400">Loading todos...</span>
            </div>
          ) : filteredTodos.length === 0 ? (
            <div className="text-center py-12">
              <div className="w-16 h-16 mx-auto mb-4 bg-gray-100 dark:bg-gray-800 rounded-full flex items-center justify-center">
                <Check className="text-gray-400 dark:text-gray-600" size={24} />
              </div>
              <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-2">
                {searchQuery || filter !== 'all' ? 'No todos found' : 'No todos yet'}
              </h3>
              <p className="text-gray-500 dark:text-gray-400 mb-4">
                {searchQuery || filter !== 'all' 
                  ? 'Try adjusting your search or filter criteria'
                  : 'Create your first todo to get started!'
                }
              </p>
              {!searchQuery && filter === 'all' && (
                <Button onClick={() => setIsCreateModalOpen(true)}>
                  <Plus size={18} />
                  Create Todo
                </Button>
              )}
            </div>
          ) : (
            <>
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-lg font-semibold text-gray-900 dark:text-white">
                  {filter === 'all' ? 'All Todos' : 
                   filter === 'pending' ? 'Pending Todos' : 'Completed Todos'}
                  <span className="text-sm font-normal text-gray-500 dark:text-gray-400 ml-2">
                    ({filteredTodos.length})
                  </span>
                </h2>
              </div>

              {filteredTodos.map(todo => (
                <TodoItem
                  key={todo.id}
                  todo={todo}
                  onToggle={handleToggleTodo}
                  onEdit={setEditTodo}
                  onDelete={handleDeleteTodo}
                />
              ))}
            </>
          )}
        </div>
      </main>
      {/* Modals */}
      <CreateTodoModal
        isOpen={isCreateModalOpen}
        onClose={() => setIsCreateModalOpen(false)}
        onSubmit={handleCreateTodo}
      />

      <CreateTodoModal
        isOpen={!!editTodo}
        onClose={() => setEditTodo(null)}
        onSubmit={handleUpdateTodo}
        editTodo={editTodo}
      />

      {/* Toast */}
      {toast && (
        <Toast
          message={toast.message}
          type={toast.type}
          onClose={() => setToast(null)}
        />
      )}
    </div>
  );
};
