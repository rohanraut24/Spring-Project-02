import { Check, Edit2, Trash2 } from "lucide-react";
import type { Todo } from "../../types/todo.types";

export const TodoItem: React.FC<{
  todo: Todo;
  onToggle: (id: number) => void;
  onEdit: (todo: Todo) => void;
  onDelete: (id: number) => void;
}> = ({ todo, onToggle, onEdit, onDelete }) => {
  return (
    <div className={`bg-white dark:bg-gray-800 rounded-lg shadow-md p-4 border-l-4 transition-all duration-200 hover:shadow-lg ${
      todo.completed ? 'border-green-500 bg-green-50 dark:bg-green-900/20' : 'border-blue-500'
    }`}>
      <div className="flex items-start gap-3">
        <button
          onClick={() => onToggle(todo.id)}
          className={`mt-1 w-5 h-5 rounded-full border-2 flex items-center justify-center transition-all duration-200 ${
            todo.completed 
              ? 'bg-green-500 border-green-500 text-white' 
              : 'border-gray-300 dark:border-gray-600 hover:border-green-500'
          }`}
        >
          {todo.completed && <Check size={12} />}
        </button>
        
        <div className="flex-1">
          <h3 className={`font-semibold text-gray-900 dark:text-white ${todo.completed ? 'line-through opacity-60' : ''}`}>
            {todo.title}
          </h3>
          <p className={`text-sm text-gray-600 dark:text-gray-400 mt-1 ${todo.completed ? 'line-through opacity-60' : ''}`}>
            {todo.description}
          </p>
          <p className="text-xs text-gray-500 dark:text-gray-500 mt-2">
            Created: {new Date(todo.createdAt).toLocaleDateString()}
          </p>
        </div>
        
        <div className="flex gap-2">
          <button
            onClick={() => onEdit(todo)}
            className="p-2 text-gray-400 hover:text-blue-600 dark:hover:text-blue-400 transition-colors"
          >
            <Edit2 size={16} />
          </button>
          <button
            onClick={() => onDelete(todo.id)}
            className="p-2 text-gray-400 hover:text-red-600 dark:hover:text-red-400 transition-colors"
          >
            <Trash2 size={16} />
          </button>
        </div>
      </div>
    </div>
  );
};
