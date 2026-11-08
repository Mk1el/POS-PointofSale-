import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import { roleRedirect } from "../../utils/roleRedirect";

const GuestRoute = () => {
  const { user, isAuthenticated } = useAuth();

  if (isAuthenticated) {
    return (
      <Navigate
        to={roleRedirect(user?.role)}
        replace
      />
    );
  }

  return <Outlet />;
};

export default GuestRoute;