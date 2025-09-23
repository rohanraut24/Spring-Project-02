import { LoadingScreen } from "./components/common/LoadingScreen";
import { useAuth } from "./contexts/AuthContext";
import { AuthPage } from "./pages/AuthPage";
import { Dashboard } from "./pages/Dashboard";

export const App: React.FC = () => {
  const { user, isLoading } = useAuth();

  if (isLoading) {
    return <LoadingScreen />;
  }

  return user ? <Dashboard /> : <AuthPage />;
};
