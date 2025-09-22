interface Todo {
  id: number;
  title: string;
  description: string;
  completed: boolean;
  createdAt: string;
  updatedAt: string;
  userId: number;
}

interface TodoStats {
  totalTodos: number;
  completedTodos: number;
  pendingTodos: number;
}