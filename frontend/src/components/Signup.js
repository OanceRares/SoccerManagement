// src/components/Signup.js

import React, { useState } from "react";
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate, Link } from "react-router-dom";

function Signup() {
    const [formData, setFormData] = useState({
        firstName: "",  // Add if your User entity has firstName
        lastName: "",   // Add if your User entity has lastName
        email: "",
        password: "",
        // Add other fields if necessary
    });

    const [message, setMessage] = useState("");
    const navigate = useNavigate(); // Use useNavigate instead of useHistory

    const { firstName, lastName, email, password } = formData;

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSignup = async (event) => {
        event.preventDefault();
        setMessage("");

        try {
            // Send signup request to backend
            const response = await axios.post("http://localhost:8080/api/users/signup", formData, {
                headers: {
                    "Content-Type": "application/json",
                },
            });

            // Update the UI with success message
            setMessage("Signup successful! Redirecting to login...");

            // Redirect to login after a short delay
            setTimeout(() => {
                navigate("/login");
            }, 2000);

        } catch (error) {
            console.error("Signup failed:", error);
            if (error.response && error.response.data) {
                setMessage(error.response.data); // Display backend error message
            } else {
                setMessage("Signup failed. Please try again.");
            }
        }
    };

    return (
        <div className="container d-flex justify-content-center align-items-center vh-100">
            <div className="card shadow-sm" style={{ maxWidth: "500px", width: "100%" }}>
                <div className="card-body">
                    <h2 className="card-title text-center mb-4">Signup</h2>
                    {/* Signup Form */}
                    <form onSubmit={handleSignup}>
                        {/* First Name */}
                        <div className="mb-3">
                            <label htmlFor="firstName" className="form-label">First Name</label>
                            <input
                                type="text"
                                className="form-control"
                                id="firstName"
                                name="firstName"
                                placeholder="Enter your first name"
                                value={firstName}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        {/* Last Name */}
                        <div className="mb-3">
                            <label htmlFor="lastName" className="form-label">Last Name</label>
                            <input
                                type="text"
                                className="form-control"
                                id="lastName"
                                name="lastName"
                                placeholder="Enter your last name"
                                value={lastName}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        {/* Email */}
                        <div className="mb-3">
                            <label htmlFor="emailSignup" className="form-label">Email</label>
                            <input
                                type="email"
                                className="form-control"
                                id="emailSignup"
                                name="email"
                                placeholder="Enter your email"
                                value={email}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        {/* Password */}
                        <div className="mb-3">
                            <label htmlFor="passwordSignup" className="form-label">Password</label>
                            <input
                                type="password"
                                className="form-control"
                                id="passwordSignup"
                                name="password"
                                placeholder="Enter your password"
                                value={password}
                                onChange={handleChange}
                                required
                                minLength="6" // Ensure password has a minimum length
                            />
                        </div>

                        <button type="submit" className="btn btn-primary w-100">Signup</button>
                    </form>

                    {/* Display Message */}
                    {message && (
                        <div className="mt-3 text-center">
                            <p>{message}</p>
                        </div>
                    )}

                    <div className="mt-3 text-center">
                        <small>Already have an account? <Link to="/login">Login</Link></small>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Signup;
