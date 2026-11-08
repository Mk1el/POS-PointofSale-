export const roleRedirect = (role) => {
  const dashboards = {
    ROLE_ADMIN: "/admin/dashboard",
    ROLE_STORE_ADMIN: "/store-admin/dashboard",
    ROLE_STORE_MANAGER: "/store-manager/dashboard",
    ROLE_BRANCH_MANAGER: "/branch-manager/dashboard",
    ROLE_BRANCH_CASHIER: "/cashier/dashboard",
    ROLE_USER: "/user/dashboard",
  };

  return dashboards[role] || "/login";
};