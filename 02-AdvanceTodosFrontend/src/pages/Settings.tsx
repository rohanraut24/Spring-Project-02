import React, { useEffect, useState } from "react";
import axios from "axios";

// User Types
interface UserResponse {
  id: number;
  username: string;
  email: string;
}

// Todo Types
interface TodoResponse {
  id: number;
  title: string;
  description: string;
  completed: boolean;
}

// Stats
interface TodoStatsResponse {
  total: number;
  completed: number;
  pending: number;
}

export const Settings: React.FC = () => {
  // User State
  const [user, setUser] = useState<UserResponse | null>(null);
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");

  // Todos State
  const [todos, setTodos] = useState<TodoResponse[]>([]);
  const [newTodoTitle, setNewTodoTitle] = useState("");
  const [newTodoDesc, setNewTodoDesc] = useState("");
  const [stats, setStats] = useState<TodoStatsResponse | null>(null);

  // Fetch current user and todos
  useEffect(() => {
    axios.get("/api/users/me")
      .then(res => {
        setUser(res.data);
        setUsername(res.data.username);
        setEmail(res.data.email);
      })
      .catch(err => console.error(err));

    fetchTodos();
    fetchTodoStats();
  }, []);

  const fetchTodos = () => {
    axios.get("/api/todos")
      .then(res => setTodos(res.data))
      .catch(err => console.error(err));
  };

  const fetchTodoStats = () => {
    axios.get("/api/todos/stats")
      .then(res => setStats(res.data))
      .catch(err => console.error(err));
  };

  // User Functions
  const handleUpdateUser = () => {
    if (!user) return;
    axios.put(`/api/users/${user.id}`, { username, email })
      .then(res => {
        alert(res.data.message);
        setUser({ ...user, username, email });
      })
      .catch(err => console.error(err));
  };

  const handleChangePassword = () => {
    axios.put("/api/users/change-password", { oldPassword, newPassword })
      .then(res => alert(res.data.message))
      .catch(err => console.error(err));
  };

  const handleDeleteAccount = () => {
    if (!user) return;
    if (window.confirm("Are you sure you want to delete your account?")) {
      axios.delete(`/api/users/${user.id}`)
        .then(res => {
          alert(res.data.message);
          // Redirect or clear user state
          setUser(null);
        })
        .catch(err => console.error(err));
    }
  };

  // Todo Functions
  const handleCreateTodo = () => {
    axios.post("/api/todos", { title: newTodoTitle, description: newTodoDesc })
      .then(() => {
        setNewTodoTitle("");
        setNewTodoDesc("");
        fetchTodos();
        fetchTodoStats();
      })
      .catch(err => console.error(err));
  };

  const handleUpdateTodo = (id: number, title: string, description: string) => {
    axios.put(`/api/todos/${id}`, { title, description })
      .then(() => fetchTodos())
      .catch(err => console.error(err));
  };

  const handleToggleTodo = (id: number) => {
    axios.patch(`/api/todos/${id}/toggle`)
      .then(() => {
        fetchTodos();
        fetchTodoStats();
      })
      .catch(err => console.error(err));
  };

  const handleDeleteTodo = (id: number) => {
    axios.delete(`/api/todos/${id}`)
      .then(() => {
        fetchTodos();
        fetchTodoStats();
      })
      .catch(err => console.error(err));
  };

  const handleDeleteCompletedTodos = () => {
    axios.delete("/api/todos/completed")
      .then(() => {
        fetchTodos();
        fetchTodoStats();
      })
      .catch(err => console.error(err));
  };

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900 p-8">
      <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-6">Settings</h1>

      {/* User Section */}
      {user && (
        <div className="mb-8 p-6 bg-white dark:bg-gray-800 rounded shadow">
          <h2 className="text-xl font-semibold mb-4 text-gray-900 dark:text-white">User Profile</h2>
          
          <input
            type="text"
            value={username}
            onChange={e => setUsername(e.target.value)}
            placeholder="Username"
            className="mb-2 w-full p-2 border rounded dark:bg-gray-700 dark:text-white"
          />
          <input
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            placeholder="Email"
            className="mb-2 w-full p-2 border rounded dark:bg-gray-700 dark:text-white"
          />
          <button
            onClick={handleUpdateUser}
            className="mr-2 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            Update Profile
          </button>

          <div className="mt-4">
            <input
              type="password"
              value={oldPassword}
              onChange={e => setOldPassword(e.target.value)}
              placeholder="Old Password"
              className="mb-2 w-full p-2 border rounded dark:bg-gray-700 dark:text-white"
            />
            <input
              type="password"
              value={newPassword}
              onChange={e => setNewPassword(e.target.value)}
              placeholder="New Password"
              className="mb-2 w-full p-2 border rounded dark:bg-gray-700 dark:text-white"
            />
            <button
              onClick={handleChangePassword}
              className="mr-2 px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
            >
              Change Password
            </button>
          </div>

          <div className="mt-4">
            <button
              onClick={handleDeleteAccount}
              className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
            >
              Delete Account
            </button>
          </div>
        </div>
      )}

      {/* Todo Section */}
      <div className="p-6 bg-white dark:bg-gray-800 rounded shadow">
        <h2 className="text-xl font-semibold mb-4 text-gray-900 dark:text-white">Todos</h2>

        {/* Stats */}
        {stats && (
          <div className="mb-4 text-gray-700 dark:text-gray-300">
            <p>Total: {stats.total}</p>
            <p>Completed: {stats.completed}</p>
            <p>Pending: {stats.pending}</p>
          </div>
        )}

        {/* Add Todo */}
        <div className="mb-4">
          <input
            type="text"
            value={newTodoTitle}
            onChange={e => setNewTodoTitle(e.target.value)}
            placeholder="Title"
            className="mb-2 w-full p-2 border rounded dark:bg-gray-700 dark:text-white"
          />
          <input
            type="text"
            value={newTodoDesc}
            onChange={e => setNewTodoDesc(e.target.value)}
            placeholder="Description"
            className="mb-2 w-full p-2 border rounded dark:bg-gray-700 dark:text-white"
          />
          <button
            onClick={handleCreateTodo}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            Add Todo
          </button>
          <button
            onClick={handleDeleteCompletedTodos}
            className="ml-2 px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
          >
            Delete Completed
          </button>
        </div>

        {/* List Todos */}
        {todos.map(todo => (
          <div
            key={todo.id}
            className="mb-2 p-2 border rounded flex justify-between items-center dark:bg-gray-700"
          >
            <div>
              <p className={`font-semibold ${todo.completed ? "line-through text-green-400" : ""}`}>{todo.title}</p>
              <p className="text-gray-500 dark:text-gray-300">{todo.description}</p>
            </div>
            <div className="flex space-x-2">
              <button
                onClick={() => handleToggleTodo(todo.id)}
                className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600"
              >
                Toggle
              </button>
              <button
                onClick={() => handleDeleteTodo(todo.id)}
                className="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600"
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
