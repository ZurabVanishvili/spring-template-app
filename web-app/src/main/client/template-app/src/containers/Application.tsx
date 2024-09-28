import { SnackbarProvider } from "notistack";
import { Route, Routes, Navigate } from "react-router-dom";
import React from "react";
import LoginPage from "../components/login/LoginPage";

const Application = () => {
  return (
    <SnackbarProvider>
      <Routes>
        <Route path="/app/public/login" element={<LoginPage />} />
        <Route path="/" element={<Navigate to="/app/public/login" />} />
      </Routes>
    </SnackbarProvider>
  );
};

export default Application;
