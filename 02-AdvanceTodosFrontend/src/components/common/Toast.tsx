import { X } from "lucide-react";
import { useEffect } from "react";

export const Toast: React.FC<{
  message: string;
  type: 'success' | 'error';
  onClose: () => void;
}> = ({ message, type, onClose }) => {
  useEffect(() => {
    const timer = setTimeout(onClose, 3000);
    return () => clearTimeout(timer);
  }, [onClose]);

  return (
    <div className={`fixed top-4 right-4 p-4 rounded-lg shadow-lg z-50 ${
      type === 'success' ? 'bg-green-600 text-white' : 'bg-red-600 text-white'
    }`}>
      <div className="flex items-center gap-2">
        <span>{message}</span>
        <button onClick={onClose} className="hover:opacity-75">
          <X size={16} />
        </button>
      </div>
    </div>
  );
};