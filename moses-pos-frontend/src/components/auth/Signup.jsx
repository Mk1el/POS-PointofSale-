import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {User,Mail,Phone,LockKeyhole,Store,Eye,EyeOff,} from "lucide-react";
import { useAuth } from "../../context/AuthContext";

const Signup = () => {
  const navigate = useNavigate();
  const { signup, loading } = useAuth();

  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    phone: "",
    password: "",
    role: "ROLE_USER",
    branchId: "",
    storeId: "",
  });

  const [confirmPassword, setConfirmPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    setError("");
    setSuccess("");

    if (formData.password.length < 8) {
      setError("Password must contain at least 8 characters.");
      return;
    }

    if (formData.password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    const payload = {
      ...formData,
      branchId: formData.branchId
        ? Number(formData.branchId)
        : null,
      storeId: formData.storeId
        ? Number(formData.storeId)
        : null,
    };

    try {
      await signup(payload);

      setSuccess("Account created successfully. Please log in.");

      setTimeout(() => {
        navigate("/login");
      }, 1500);

    } catch (err) {
      setError(err.message);
    }
  };

  const fields = [
    {
      name: "fullName",
      label: "Full name",
      placeholder: "Enter your full name",
      icon: User,
      type: "text",
    },
    {
      name: "email",
      label: "Email address",
      placeholder: "name@example.com",
      icon: Mail,
      type: "email",
    },
    {
      name: "phone",
      label: "Phone number",
      placeholder: "Enter your phone number",
      icon: Phone,
      type: "tel",
    },
  ];

  return (
    <div className="min-h-screen grid lg:grid-cols-2 bg-slate-50">

      {/* Branding */}
      <div className="hidden lg:flex flex-col justify-between bg-slate-950 text-white p-14">

        <div className="flex items-center gap-3">
          <div className="bg-blue-600 p-3 rounded-xl">
            <Store size={27} />
          </div>

          <h1 className="text-2xl font-bold">Moses POS</h1>
        </div>

        <div>
          <h2 className="text-5xl font-bold leading-tight max-w-lg">
            Start managing your business smarter.
          </h2>

          <p className="text-slate-400 mt-6 text-lg max-w-lg">
            Create your account and access a centralized
            platform for managing your business operations.
          </p>
        </div>

        <p className="text-slate-500 text-sm">
          © {new Date().getFullYear()} Moses POS System.
        </p>
      </div>

      {/* Signup form */}
      <div className="flex items-center justify-center p-6 sm:p-10">

        <div className="w-full max-w-md">

          <div className="lg:hidden flex items-center gap-3 mb-8">
            <Store className="text-blue-600" size={30} />
            <h1 className="text-2xl font-bold">Moses POS</h1>
          </div>

          <h2 className="text-3xl font-bold text-slate-900">
            Create your account
          </h2>

          <p className="text-slate-500 mt-2 mb-7">
            Enter your details to get started.
          </p>

          {error && (
            <div className="p-3 mb-4 bg-red-50 border border-red-200 text-red-700 rounded-lg text-sm">
              {error}
            </div>
          )}

          {success && (
            <div className="p-3 mb-4 bg-green-50 border border-green-200 text-green-700 rounded-lg text-sm">
              {success}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">

            {fields.map((field) => {
              const Icon = field.icon;

              return (
                <div key={field.name}>
                  <label className="block text-sm font-medium mb-2">
                    {field.label}
                  </label>

                  <div className="relative">
                    <Icon
                      size={19}
                      className="absolute left-3 top-3.5 text-slate-400"
                    />

                    <input
                      name={field.name}
                      type={field.type}
                      required
                      value={formData[field.name]}
                      onChange={handleChange}
                      placeholder={field.placeholder}
                      className="w-full pl-11 pr-4 py-3 border border-slate-200 rounded-xl outline-none focus:ring-2 focus:ring-blue-500"
                    />
                  </div>
                </div>
              );
            })}

            
            

            <div>
              <label className="block text-sm font-medium mb-2">
                Password
              </label>

              <div className="relative">
                <LockKeyhole
                  size={19}
                  className="absolute left-3 top-3.5 text-slate-400"
                />

                <input
                  type={showPassword ? "text" : "password"}
                  required
                  minLength={8}
                  value={formData.password}
                  name="password"
                  onChange={handleChange}
                  placeholder="Create a password"
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

            <div>
              <label className="block text-sm font-medium mb-2">
                Confirm password
              </label>

              <input
                type="password"
                required
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="Confirm your password"
                className="w-full px-4 py-3 border border-slate-200 rounded-xl outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full bg-blue-600 hover:bg-blue-700 disabled:opacity-60 text-white font-semibold py-3 rounded-xl transition"
            >
              {loading ? "Creating account..." : "Create Account"}
            </button>

          </form>

          <p className="text-center text-slate-500 mt-6 text-sm">
            Already have an account?{" "}
            <Link
              to="/login"
              className="text-blue-600 font-semibold hover:underline"
            >
              Sign in
            </Link>
          </p>

        </div>
      </div>
    </div>
  );
};

export default Signup;