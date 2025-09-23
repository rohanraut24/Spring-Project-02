import React, { useState } from 'react';
import { Settings, User, Lock, Trash2, AlertTriangle } from 'lucide-react';
import ChangePasswordModal from '../components/user/ChangePasswordModal';
import UserManagement from '../components/user/UserManagement';

interface UserSettingsProps {
  user: any;
  onLogout: () => void;
}

const UserSettings: React.FC<UserSettingsProps> = ({ user, onLogout }) => {
  const [showChangePassword, setShowChangePassword] = useState(false);
  const [toast, setToast] = useState<{ message: string; type: 'success' | 'error' } | null>(null);

  const showToast = (message: string, type: 'success' | 'error') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 3000);
  };

  const handleDeleteAccount = async () => {
    if (!confirm('Are you sure you want to delete your account? This action cannot be undone and all your data will be permanently deleted.')) {
      return;
    }

    const confirmText = prompt('Type "DELETE" to confirm account deletion:');
    if (confirmText !== 'DELETE') {
      alert('Account deletion cancelled. You must type "DELETE" exactly.');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/users/${user.id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });

      if (!response.ok) {
        throw new Error('Failed to delete account');
      }

      showToast('Account deleted successfully', 'success');
      setTimeout(() => {
        onLogout();
      }, 2000);
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      showToast('Failed to delete account', 'error');
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900">
      <div className="max-w-4xl mx-auto p-6">
        {/* Header */}
        <div className="flex items-center gap-3 mb-8">
          <Settings className="text-blue-600" size={28} />
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">User Settings</h1>
        </div>
        
        {/* Profile Information */}
        <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 mb-6">
          <div className="flex items-center gap-4 mb-6">
            <div className="w-16 h-16 bg-blue-600 rounded-full flex items-center justify-center">
              <User className="text-white" size={24} />
            </div>
            <div>
              <h2 className="text-xl font-semibold text-gray-900 dark:text-white">{user?.username}</h2>
              <p className="text-gray-600 dark:text-gray-400">{user?.email}</p>
              <span className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full mt-1 ${
                user?.userRole === 'ADMIN' 
                  ? 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200'
                  : 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200'
              }`}>
                {user?.userRole}
              </span>
            </div>
          </div>
        </div>

        {/* Security Section */}
        <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 mb-6">
          <h2 className="text-lg font-semibold text-gray-900 dark:text-white mb-4 flex items-center gap-2">
            <Lock size={20} />
            Security
          </h2>
          <p className="text-gray-600 dark:text-gray-400 text-sm mb-4">
            Keep your account secure by using a strong password.
          </p>
          <button
            onClick={() => setShowChangePassword(true)}
            className="bg-gray-600 hover:bg-gray-700 text-white font-medium py-3 px-6 rounded-lg transition-colors flex items-center gap-2"
          >
            <Lock size={16} />
            Change Password
          </button>
        </div>

        {/* Admin Section */}
        {user?.userRole === 'ADMIN' && (
          <div className="mb-6">
            <UserManagement currentUser={user} />
          </div>
        )}

        {/* Danger Zone */}
        <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 border-l-4 border-red-500">
          <h2 className="text-lg font-semibold text-red-600 dark:text-red-400 mb-4 flex items-center gap-2">
            <AlertTriangle size={20} />
            Danger Zone
          </h2>
          <p className="text-gray-600 dark:text-gray-400 text-sm mb-4">
            Once you delete your account, there is no going back. Please be certain.
          </p>
          <button
            onClick={handleDeleteAccount}
            className="bg-red-600 hover:bg-red-700 text-white font-medium py-3 px-6 rounded-lg transition-colors flex items-center gap-2"
          >
            <Trash2 size={16} />
            Delete Account
          </button>
        </div>

        {/* Modals */}
        <ChangePasswordModal 
          isOpen={showChangePassword} 
          onClose={() => setShowChangePassword(false)}
          onSuccess={(message) => showToast(message, 'success')}
        />

        {/* Toast */}
        {toast && (
          <div className={`fixed top-4 right-4 p-4 rounded-lg shadow-lg z-50 animate-in slide-in-from-right ${
            toast.type === 'success' ? 'bg-green-600 text-white' : 'bg-red-600 text-white'
          }`}>
            <div className="flex items-center gap-2">
              <span>{toast.message}</span>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default UserSettings;  