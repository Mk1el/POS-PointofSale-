import { useAuth } from "../../src/context/AuthContext";
import { useNavigate } from "react-router-dom";
import { LogOut, Store } from "lucide-react";

const Dashboard = ({ title }) => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login", { replace: true });
  };

  return (
    <div className="min-h-screen bg-slate-100">

      <header className="bg-white border-b px-6 py-4 flex justify-between items-center">

        <div className="flex items-center gap-3">
          <Store className="text-blue-600" size={26} />
          <h1 className="font-bold text-xl">Moses POS</h1>
        </div>

        <div className="flex items-center gap-4">

          <div className="text-right hidden sm:block">
            <p className="font-semibold text-sm">
              {user?.fullName || user?.email}
            </p>

            <p className="text-xs text-slate-500">
              {user?.role}
            </p>
          </div>

          <button
            onClick={handleLogout}
            className="flex items-center gap-2 bg-red-50 text-red-600 px-4 py-2 rounded-lg hover:bg-red-100"
          >
            <LogOut size={18} />
            Logout
          </button>

        </div>
      </header>

      <main className="p-8">

        <h2 className="text-3xl font-bold text-slate-900">
          {title}
        </h2>

        <p className="text-slate-500 mt-2">
          Welcome to your Moses POS dashboard.
        </p>

        <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-5 mt-8">

          {[
            "Total Sales",
            "Total Orders",
            "Products",
            "Customers",
          ].map((item) => (
            <div
              key={item}
              className="bg-white p-6 rounded-xl shadow-sm border"
            >
              <p className="text-slate-500">{item}</p>
              <h3 className="text-2xl font-bold mt-3">--</h3>
              <p className="text-xs text-slate-400 mt-2">
                Awaiting API integration
              </p>
            </div>
          ))}

        </div>

      </main>
    </div>
  );
};

export default Dashboard;