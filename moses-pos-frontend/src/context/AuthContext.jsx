import {createContext,useContext,useEffect,useRef,useState,useCallback} from "react";
import { authService } from "../services/authService";

const AuthContext = createContext(null);
const INACTIVITY_LIMIT = 5 * 60 * 1000;

// Extract JWT claims when the backend provides a JWT.
const decodeToken = (token) => {
  try {
    const payload = token.split(".")[1];
    const base64 = payload.replace(/-/g, "+").replace(/_/g, "/");

    return JSON.parse(
      decodeURIComponent(
        atob(base64)
          .split("")
          .map((char) =>
            "%" + char.charCodeAt(0).toString(16).padStart(2, "0")
          )
          .join("")
      )
    );
  } catch {
    return {};
  }
};

const extractSession = (response) => {
  const data = response?.data ?? response;

  const token =
    data?.accessToken ||
    data?.token ||
    data?.jwt ||
    data?.data?.accessToken ||
    data?.data?.token;

  const tokenClaims = token ? decodeToken(token) : {};

  const user =
    data?.user ||
    data?.data?.user ||
    data?.data?.account ||
    data?.data ||
    data;

  return {
    token,
    user: {
      ...tokenClaims,
      ...user,
      role:
        user?.role ||
        tokenClaims?.role ||
        tokenClaims?.authorities?.[0],
    },
  };
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    try {
      const savedUser = sessionStorage.getItem("authUser");
      return savedUser ? JSON.parse(savedUser) : null;
    } catch {
      return null;
    }
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const timerRef = useRef(null);

  const logout = useCallback(() => {
    clearTimeout(timerRef.current);

    sessionStorage.removeItem("authUser");
    sessionStorage.removeItem("accessToken");

    setUser(null);
  }, []);

  const resetInactivityTimer = useCallback(() => {
    if (!user) return;

    clearTimeout(timerRef.current);

    timerRef.current = setTimeout(() => {
      logout();
    }, INACTIVITY_LIMIT);
  }, [user, logout]);

  // Login
  const login = async (email, password) => {
    setLoading(true);
    setError("");

    try {
      const response = await authService.login({
        email,password,
      });

      const session = extractSession(response);

      if (!session.user?.role) {
        throw new Error(
          "Login succeeded but no user role was returned. Check your backend login response."
        );
      }

      if (session.token) {
        sessionStorage.setItem("accessToken", session.token);
      }

      sessionStorage.setItem(
        "authUser",
        JSON.stringify(session.user)
      );

      setUser(session.user);

      return session.user;
    } catch (err) {
      const message =
        err.response?.data?.message ||
        err.response?.data?.error ||
        err.message ||
        "Login failed. Please try again.";

      setError(message);
      throw new Error(message);
    } finally {
      setLoading(false);
    }
  };

  // Signup
  const signup = async (userData) => {
    setLoading(true);
    setError("");

    try {
      const response = await authService.signup(userData);
      return response;
    } catch (err) {
      const message =
        err.response?.data?.message ||
        err.response?.data?.error ||
        "Registration failed. Please try again.";

      setError(message);
      throw new Error(message);
    } finally {
      setLoading(false);
    }
  };

  // Five-minute inactivity logout
  useEffect(() => {
    if (!user) return;

    const events = [
      "mousemove",
      "mousedown",
      "keydown",
      "scroll",
      "touchstart",
      "click",
    ];

    events.forEach((event) =>
      window.addEventListener(event, resetInactivityTimer)
    );

    resetInactivityTimer();

    return () => {
      events.forEach((event) =>
        window.removeEventListener(event, resetInactivityTimer)
      );

      clearTimeout(timerRef.current);
    };
  }, [user, resetInactivityTimer]);

  return (
    <AuthContext.Provider
      value={{
        user,
        loading,
        error,
        login,
        signup,
        logout,
        isAuthenticated: Boolean(user),
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }

  return context;
};