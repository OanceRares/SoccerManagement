import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./components/Login";
import Signup from "./components/Signup";
import MainPage from "./components/MainPage"; // Assuming you have a MainPage component
import ProtectedRoute from "./components/ProtectedRoute"; // We'll create this component

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<Navigate to="/login" replace />} />

          {/* Public Routes */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Signup />} />

          {/* Protected Routes */}
          <Route
              path="/main"
              element={
                <ProtectedRoute>
                  <MainPage />
                </ProtectedRoute>
              }
          />

          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </Router>
  );
}

export default App;
