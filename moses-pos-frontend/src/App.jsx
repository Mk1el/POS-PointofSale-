import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import { AuthProvider } from "../src/context/AuthContext";

import Login from "../src/components/auth/Login";
import Signup from "../src/components/auth/Signup";

import ProtectedRoute from "../src/components/auth/ProtectedRoute";
import GuestRoute from "../src/components/auth/GuestRoute";

import AdminDashboard from "../src/pages/AdminDashboard";
import StoreAdminDashboard from "../src/pages/StoreAdminDashboard";
import StoreManagerDashboard from "../src/pages/StoreAdminDashboard";
import BranchManagerDashboard from "../src/pages/BranchManagerDashboard";
import CashierDashboard from "../src/pages/CashierDashboard";
import UserDashboard from "../src/pages/UserDashboard";

const roleRoutes = [
  {
    path: "/admin/dashboard",
    role: "ROLE_ADMIN",
    component: AdminDashboard,
  },
  {
    path: "/store-admin/dashboard",
    role: "ROLE_STORE_ADMIN",
    component: StoreAdminDashboard,
  },
  {
    path: "/store-manager/dashboard",
    role: "ROLE_STORE_MANAGER",
    component: StoreManagerDashboard,
  },
  {
    path: "/branch-manager/dashboard",
    role: "ROLE_BRANCH_MANAGER",
    component: BranchManagerDashboard,
  },
  {
    path: "/cashier/dashboard",
    role: "ROLE_BRANCH_CASHIER",
    component: CashierDashboard,
  },
  {
    path: "/user/dashboard",
    role: "ROLE_USER",
    component: UserDashboard,
  },
];

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>

          {/* Public authentication pages */}
          <Route element={<GuestRoute />}>
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
          </Route>

          {/* Role-protected dashboards */}
          {roleRoutes.map(({ path, role, component: Component }) => (
            <Route
              key={role}
              element={<ProtectedRoute allowedRoles={[role]} />}
            >
              <Route path={path} element={<Component />} />
            </Route>
          ))}

          {/* Default route */}
          <Route path="/" element={<Navigate to="/login" replace />} />

          <Route path="*" element={<Navigate to="/" replace />} />

        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;