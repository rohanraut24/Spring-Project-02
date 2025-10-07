import type { User } from "../types/auth.types";
import type { Todo, TodoStats } from "../types/todo.types";

// Update API_BASE_URL to use environment variable
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

class ApiService {
  private token: string | null = null;

  setToken(token: string | null) {
    this.token = token;
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  private async request(endpoint: string, options: RequestInit = {}): Promise<any> {
    const url = `${API_BASE_URL}${endpoint}`;
    const config: RequestInit = {
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
      ...options,
    };

    const response = await fetch(url, config);
    
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Something went wrong');
    }

    return response.json();
  }

  // Auth endpoints
  async login(username: string, password: string) {
    return this.request('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    });
  }

  async register(username: string, email: string, password: string) {
    return this.request('/auth/register', {
      method: 'POST',
      body: JSON.stringify({ username, email, password }),
    });
  }

  // User endpoints
  async getCurrentUser(): Promise<User> {
    return this.request('/users/me');
  }

  // Todo endpoints
  async getTodos(): Promise<Todo[]> {
    return this.request('/todos');
  }

  async createTodo(title: string, description: string): Promise<Todo> {
    return this.request('/todos', {
      method: 'POST',
      body: JSON.stringify({ title, description, completed: false }),
    });
  }

  async updateTodo(id: number, updates: Partial<Todo>): Promise<Todo> {
    return this.request(`/todos/${id}`, {
      method: 'PUT',
      body: JSON.stringify(updates),
    });
  }

  async deleteTodo(id: number): Promise<void> {
    return this.request(`/todos/${id}`, {
      method: 'DELETE',
    });
  }

  async toggleTodo(id: number): Promise<void> {
    return this.request(`/todos/${id}/toggle`, {
      method: 'PATCH',
    });
  }

  async getTodoStats(): Promise<TodoStats> {
    return this.request('/todos/stats');
  }

  async searchTodos(title: string): Promise<Todo[]> {
    return this.request(`/todos/search?title=${encodeURIComponent(title)}`);
  }

  async deleteCompletedTodos(): Promise<void> {
    return this.request('/todos/completed', {
      method: 'DELETE',
    });
  }
  async updateUser(id: number, updates: { username?: string; email?: string }) {
  return this.request(`/users/${id}`, {
    method: 'PUT',
    body: JSON.stringify(updates),
  });
}

async changePassword(currentPassword: string, newPassword: string) {
  return this.request('/users/change-password', {
    method: 'PUT',
    body: JSON.stringify({ currentPassword, newPassword }),
  });
}

async deleteUser(id: number) {
  return this.request(`/users/${id}`, {
    method: 'DELETE',
  });
}

async getAllUsers(): Promise<User[]> {
  return this.request('/users');
}

async getUserById(id: number): Promise<User> {
  return this.request(`/users/${id}`);
}
}

export const apiService = new ApiService();