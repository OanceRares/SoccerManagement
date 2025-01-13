// src/components/Login.js

import React, { useState } from "react";
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate, Link } from "react-router-dom";

function Login() {
    const [email, setEmail] = useState("");  // Email state
    const [password, setPassword] = useState("");  // Password state
    const [message, setMessage] = useState("");  // Message state

    const navigate = useNavigate(); // Replace useHistory with useNavigate

    const handleLogin = async (event) => {
        event.preventDefault();

        try {
            // Send login request to backend
            const response = await axios.post("http://localhost:8080/api/users/login", {  // Ensure the endpoint is correct
                email,
                password,
            });

            localStorage.clear();
            const token = response.data.token;
            localStorage.setItem("token", token);
            localStorage.setItem("email",email);

            setMessage("Login successful!");

            navigate("/main");

        } catch (error) {
            console.error("Login failed:", error);
            setMessage("Login failed. Please check your credentials.");
        }
    };

    return (
        <div className="App">
            <div className="container d-flex justify-content-center align-items-center vh-100">
                <div className="card shadow-sm" style={{ maxWidth: "400px", width: "100%" }}>
                    <div className="card-body">
                        <h2 className="card-title text-center mb-4">Login</h2>
                        {/* Login Form */}
                        <form onSubmit={handleLogin}>
                            <div className="mb-3">
                                <label htmlFor="email" className="form-label">Email</label>
                                <input
                                    type="email"
                                    className="form-control"
                                    id="email"
                                    placeholder="Enter your email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="password" className="form-label">Password</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    id="password"
                                    placeholder="Enter your password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    required
                                />
                            </div>
                            <button type="submit" className="btn btn-primary w-100">Login</button>
                        </form>

                        {message && (
                            <div className="mt-3 text-center">
                                <p>{message}</p>
                            </div>
                        )}

                        <div className="mt-3 text-center">
                            <small>Don't have an account? <Link to="/register">Sign up</Link></small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;
