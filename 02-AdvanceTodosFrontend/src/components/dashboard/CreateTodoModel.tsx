import { useEffect,useState } from "react";
import { Modal } from "../common/Modal";
import { Input } from "../common/Input";
import { Textarea } from "../common/Textarea";
import { Button } from "../common/Button";
import type { Todo } from "../../types/todo.types";

export const CreateTodoModal: React.FC<{
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (title: string, description: string) => void;
  editTodo?: Todo | null;
}> = ({ isOpen, onClose, onSubmit, editTodo }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

  useEffect(() => {
    if (editTodo) {
      setTitle(editTodo.title);
      setDescription(editTodo.description);
    } else {
      setTitle('');
      setDescription('');
    }
  }, [editTodo, isOpen]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(title, description);
    onClose();
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose} title={editTodo ? 'Edit Todo' : 'Create New Todo'}>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Title</label>
          <Input
            value={title}
            onChange={setTitle}
            placeholder="Enter todo title"
            required
          />
        </div>
        
        <div>
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Description</label>
          <Textarea
            value={description}
            onChange={setDescription}
            placeholder="Enter todo description"
            rows={4}
          />
        </div>
        
        <div className="flex gap-3 pt-4">
          <Button type="submit" className="flex-1">
            {editTodo ? 'Update Todo' : 'Create Todo'}
          </Button>
          <Button variant="secondary" onClick={onClose} className="flex-1">
            Cancel
          </Button>
        </div>
      </form>
    </Modal>
  );
};