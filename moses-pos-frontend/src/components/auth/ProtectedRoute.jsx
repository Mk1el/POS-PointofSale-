import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import { roleRedirect } from "../../utils/roleRedirect";

const ProtectedRoute = ({ allowedRoles }) => {
  const { user, isAuthenticated } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (!allowedRoles.includes(user?.role)) {
    return (
      <Navigate
        to={roleRedirect(user?.role)}
        replace
      />
    );
  }

  return <Outlet />;
};

export default ProtectedRoute;