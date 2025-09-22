const AuthContext = createContext<AuthContextType | null>(null);

const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(() => {
    if (typeof window !== 'undefined') {
      return localStorage.getItem('token');
    }
    return null;
  });
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (token) {
      apiService.setToken(token);
      getCurrentUser();
    } else {
      setIsLoading(false);
    }
  }, [token]);

  const getCurrentUser = async () => {
    try {
      const userData = await apiService.getCurrentUser();
      setUser(userData);
    } catch (error) {
      logout();
    } finally {
      setIsLoading(false);
    }
  };

  const login = async (username: string, password: string) => {
    const response = await apiService.login(username, password);
    const { token: newToken, id, username: userName, email, role } = response;
    
    setToken(newToken);
    setUser({ id, username: userName, email, userRole: role });
    if (typeof window !== 'undefined') {
      localStorage.setItem('token', newToken);
    }
    apiService.setToken(newToken);
  };

  const register = async (username: string, email: string, password: string) => {
    await apiService.register(username, email, password);
  };

  const logout = () => {
    setUser(null);
    setToken(null);
    if (typeof window !== 'undefined') {
      localStorage.removeItem('token');
    }
    apiService.setToken(null);
  };

  return (
    <AuthContext.Provider value={{ user, token, login, register, logout, isLoading }}>
      {children}
    </AuthContext.Provider>
  );
};

const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};