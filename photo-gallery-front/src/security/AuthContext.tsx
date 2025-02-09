import { createContext, ReactNode, useContext, useEffect, useState } from "react";
import { authenticate } from "../services/UserService";
import Loading from "../components/Loading/Loading";
import httpRequest from "../interceptors/HttpRequest";

interface AuthContextType {
  token: string | null;
  login: (username: string, password: string) => Promise<any>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(() => localStorage.getItem("JWToken"));
  const [loading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    async function init() {
      if (token) {
        setLoading(true);
        const auth = await authenticate();
        auth ? localStorage.setItem("JWToken", token) : localStorage.removeItem("JWToken");
      } else {
        localStorage.removeItem("JWToken");
      }

      setLoading(false);
    }

    init();
  }, [token]);

  const login = async (username: string, password: string) => {
    localStorage.removeItem('JWToken');
    setToken(null);

    try {
      const response = await httpRequest.post('login', {
        username: username,
        password: password
      });
  
      const token = response.data || null;
      if (token) {
        setToken(token);
        localStorage.setItem('JWToken', token);
      }

      return null;
    } catch (error) {
      return error;
    }
  };

  const logout = () => {
    setToken(null);
    localStorage.removeItem("JWToken");
  };

  return loading ? (
    <Loading />
  ) : (
    <AuthContext.Provider value={{ token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}