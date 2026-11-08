import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Eye, EyeOff, LockKeyhole, Mail, Store } from "lucide-react";

import { useAuth } from "../../context/AuthContext";
import { roleRedirect } from "../../utils/roleRedirect";

const Login = () => {
  const navigate = useNavigate();
  const { login, loading } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const loggedInUser = await login(email, password);

      navigate(roleRedirect(loggedInUser.role), {
        replace: true,
      });
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="min-h-screen grid lg:grid-cols-2 bg-slate-50">

      {/* Branding */}
      <div className="hidden lg:flex flex-col justify-between bg-slate-950 text-white p-14">

        <div className="flex items-center gap-3">
          <div className="bg-blue-600 p-3 rounded-xl">
            <Store size={27} />
          </div>

          <div>
            <h1 className="text-2xl font-bold">Moses POS</h1>
            <p className="text-slate-400 text-sm">
              Point of Sale Management
            </p>
          </div>
        </div>

        <div className="max-w-lg">
          <h2 className="text-5xl font-bold leading-tight">
            Manage your business with confidence.
          </h2>

          <p className="text-slate-400 mt-6 text-lg leading-8">
            Manage branches, sales, inventory, employees
            and business operations from one powerful platform.
          </p>
        </div>

        <p className="text-slate-500 text-sm">
          © {new Date().getFullYear()} Moses POS System.
          All rights reserved.
        </p>
      </div>

      {/* Login form */}
      <div className="flex items-center justify-center p-6 sm:p-12">

        <div className="w-full max-w-md">

          <div className="lg:hidden flex items-center gap-3 mb-10">
            <Store className="text-blue-600" size={30} />
            <h1 className="text-2xl font-bold text-slate-900">
              Moses POS
            </h1>
          </div>

          <h2 className="text-3xl font-bold text-slate-900">
            Welcome back
          </h2>

          <p className="text-slate-500 mt-2 mb-8">
            Sign in to access your POS dashboard.
          </p>

          {error && (
            <div className="mb-5 p-3 rounded-lg bg-red-50 border border-red-200 text-red-700 text-sm">
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-5">

            <div>
              <label className="block text-sm font-medium mb-2">
                Email address
              </label>

              <div className="relative">
                <Mail className="absolute left-3 top-3.5 text-slate-400" size={19} />

                <input
                  type="email"
                  required
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="name@example.com"
                  className="w-full pl-11 pr-4 py-3 border border-slate-200 rounded-xl outline-none focus:ring-2 focus:ring-blue-500"
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium mb-2">
                Password
              </label>

              <div className="relative">
                <LockKeyhole className="absolute left-3 top-3.5 text-slate-400" size={19} />

                <input
                  type={showPassword ? "text" : "password"}
                  required
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="Enter your password"
                  className="w-full pl-11 pr-12 py-3 border border-slate-200 rounded-xl outline-none focus:ring-2 focus:ring-blue-500"
                />

                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-3.5 text-slate-400"
                >
                  {showPassword ? <EyeOff size={19} /> : <Eye size={19} />}
                </button>
              </div>
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full bg-blue-600 hover:bg-blue-700 disabled:opacity-60 text-white font-semibold py-3 rounded-xl transition"
            >
              {loading ? "Signing in..." : "Sign In"}
            </button>

          </form>

          <p className="text-center text-slate-500 mt-8 text-sm">
            Don't have an account?{" "}
            <Link
              to="/signup"
              className="text-blue-600 font-semibold hover:underline"
            >
              Create account
            </Link>
          </p>

        </div>
      </div>
    </div>
  );
};

export default Login;