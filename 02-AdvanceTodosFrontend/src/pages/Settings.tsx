// src/pages/Settings.tsx
import React, { useState, useEffect } from "react";
import { User, Lock,Shield,Trash2,Edit2,AlertTriangle,X,Settings as SettingsIcon,Save,Mail,UserCheck} from "lucide-react";
import { AdminPanel } from "../components/user/AdminPanel";
import { ChangePasswordModal } from "../components/user/ChangePasswordModal";

// ==================== INTERFACES ====================
export interface UserData {
  id: number;
  username: string;
  email: string;
  userRole: 'USER' | 'ADMIN';
}

export interface Toast {
  message: string;
  type: 'success' | 'error';
}

export const Settings: React.FC<{ user?: UserData; onLogout?: () => void }> = ({ 
  user, 
  onLogout = () => {} 
}) => {
  // Component state
  const [currentUser, setCurrentUser] = useState<UserData | null>(user || null);
  const [toast, setToast] = useState<Toast | null>(null);
  const [showChangePassword, setShowChangePassword] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  
  // Form states for editing profile
  const [editUsername, setEditUsername] = useState(currentUser?.username || '');
  const [editEmail, setEditEmail] = useState(currentUser?.email || '');

  // Load current user if not provided
  useEffect(() => {
    if (!user) {
      loadCurrentUser();
    } else {
      setCurrentUser(user);
      setEditUsername(user.username);
      setEditEmail(user.email);
    }
  }, [user]);

  // Function to load current user from backend
  const loadCurrentUser = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/users/me', {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });

      if (response.ok) {
        const userData = await response.json();
        setCurrentUser(userData);
        setEditUsername(userData.username);
        setEditEmail(userData.email);
      }
    } catch (error) {
      showToast('Failed to load user data', 'error');
    }
  };

  // Function to show toast notifications
  const showToast = (message: string, type: 'success' | 'error') => {
    setToast({ message, type });
    // Auto hide toast after 3 seconds
    setTimeout(() => setToast(null), 3000);
  };

  // Function to handle profile update
  const handleUpdateProfile = async () => {
    if (!currentUser) return;
    
    setIsLoading(true);
    try {
      const response = await fetch(`http://localhost:8080/api/users/${currentUser.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify({
          username: editUsername,
          email: editEmail
        })
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to update profile');
      }

      // Update local state
      const updatedUser = { ...currentUser, username: editUsername, email: editEmail };
      setCurrentUser(updatedUser);
      setIsEditing(false);
      showToast('Profile updated successfully!', 'success');
    } catch (error: any) {
      showToast(error.message || 'Failed to update profile', 'error');
    } finally {
      setIsLoading(false);
    }
  };

  // Function to cancel editing
  const handleCancelEdit = () => {
    if (currentUser) {
      setEditUsername(currentUser.username);
      setEditEmail(currentUser.email);
    }
    setIsEditing(false);
  };

  if (!currentUser) {
    return (
      <div className="min-h-screen bg-gray-50 dark:bg-gray-900 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p className="text-gray-600 dark:text-gray-400">Loading user settings...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900 p-6">
      <div className="max-w-4xl mx-auto">
        {/* Page Header */}
        <div className="flex items-center gap-3 mb-8">
          <SettingsIcon className="text-blue-600" size={32} />
          <h1 className="text-3xl font-bold text-gray-900 dark:text-white">Settings</h1>
        </div>

        {/* Profile Section */}
        <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 mb-6">
          {/* Profile Header */}
          <div className="flex items-center justify-between mb-6">
            <div className="flex items-center gap-4">
              <div className="w-16 h-16 bg-blue-600 rounded-full flex items-center justify-center">
                <User className="text-white" size={28} />
              </div>
              <div>
                <h2 className="text-2xl font-semibold text-gray-900 dark:text-white">Profile Information</h2>
                <p className="text-gray-600 dark:text-gray-400">Manage your account details</p>
              </div>
            </div>
            
            {/* Edit Button */}
            {!isEditing && (
              <button
                onClick={() => setIsEditing(true)}
                className="bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-lg transition-colors flex items-center gap-2"
              >
                <Edit2 size={16} />
                Edit Profile
              </button>
            )}
          </div>

          {/* Profile Form */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* Username Field */}
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                <UserCheck className="inline mr-2" size={16} />
                Username
              </label>
              {isEditing ? (
                <input
                  type="text"
                  value={editUsername}
                  onChange={(e) => setEditUsername(e.target.value)}
                  className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                />
              ) : (
                <div className="w-full px-4 py-2 bg-gray-50 dark:bg-gray-700 border border-gray-200 dark:border-gray-600 rounded-lg text-gray-900 dark:text-white">
                  {currentUser.username}
                </div>
              )}
            </div>

            {/* Email Field */}
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                <Mail className="inline mr-2" size={16} />
                Email
              </label>
              {isEditing ? (
                <input
                  type="email"
                  value={editEmail}
                  onChange={(e) => setEditEmail(e.target.value)}
                  className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                />
              ) : (
                <div className="w-full px-4 py-2 bg-gray-50 dark:bg-gray-700 border border-gray-200 dark:border-gray-600 rounded-lg text-gray-900 dark:text-white">
                  {currentUser.email}
                </div>
              )}
            </div>

            {/* Role Field (Read-only) */}
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                <Shield className="inline mr-2" size={16} />
                Role
              </label>
              <div className="w-full px-4 py-2 bg-gray-50 dark:bg-gray-700 border border-gray-200 dark:border-gray-600 rounded-lg">
                <span className={`inline-flex px-3 py-1 text-sm font-semibold rounded-full ${
                  currentUser.userRole === 'ADMIN' 
                    ? 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200'
                    : 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200'
                }`}>
                  {currentUser.userRole}
                </span>
              </div>
            </div>

            {/* User ID Field (Read-only) */}
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                User ID
              </label>
              <div className="w-full px-4 py-2 bg-gray-50 dark:bg-gray-700 border border-gray-200 dark:border-gray-600 rounded-lg text-gray-900 dark:text-white">
                {currentUser.id}
              </div>
            </div>
          </div>

          {/* Action Buttons for Editing */}
          {isEditing && (
            <div className="flex gap-3 mt-6 pt-6 border-t border-gray-200 dark:border-gray-600">
              <button
                onClick={handleUpdateProfile}
                disabled={isLoading}
                className="bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-6 rounded-lg transition-colors flex items-center gap-2 disabled:opacity-50"
              >
                {isLoading ? (
                  <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-white"></div>
                ) : (
                  <Save size={16} />
                )}
                Save Changes
              </button>
              <button
                onClick={handleCancelEdit}
                className="bg-gray-200 hover:bg-gray-300 dark:bg-gray-700 dark:hover:bg-gray-600 text-gray-800 dark:text-white font-medium py-2 px-6 rounded-lg transition-colors"
              >
                Cancel
              </button>
            </div>
          )}
        </div>

        {/* Security Section */}
        <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 mb-6">
          <div className="flex items-center gap-3 mb-6">
            <Lock className="text-orange-600" size={24} />
            <div>
              <h2 className="text-xl font-semibold text-gray-900 dark:text-white">Security</h2>
              <p className="text-gray-600 dark:text-gray-400">Keep your account secure</p>
            </div>
          </div>
          
          <button
            onClick={() => setShowChangePassword(true)}
            className="bg-orange-600 hover:bg-orange-700 text-white font-medium py-3 px-6 rounded-lg transition-colors flex items-center gap-2"
          >
            <Lock size={18} />
            Change Password
          </button>
        </div>

        {/* Admin Panel Section - Only show for ADMIN users */}
        {currentUser.userRole === 'ADMIN' && (
          <div className="mb-6">
            <AdminPanel currentUser={currentUser} />
          </div>
        )}

        {/* Change Password Modal */}
        <ChangePasswordModal
          isOpen={showChangePassword}
          onClose={() => setShowChangePassword(false)}
          onSuccess={(message) => showToast(message, 'success')}
        />

        {/* Toast Notification */}
        {toast && (
          <div className={`fixed top-4 right-4 p-4 rounded-lg shadow-lg z-50 transition-all duration-300 ${
            toast.type === 'success' 
              ? 'bg-green-600 text-white' 
              : 'bg-red-600 text-white'
          }`}>
            <div className="flex items-center gap-2">
              <span>{toast.message}</span>
              <button 
                onClick={() => setToast(null)}
                className="hover:opacity-75 ml-2"
              >
                <X size={16} />
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};