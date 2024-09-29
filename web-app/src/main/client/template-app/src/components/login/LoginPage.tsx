import React, { useState, ChangeEvent } from "react";
import {
  TextField,
  Button,
  Box,
  Typography,
  Paper,
  Avatar,
} from "@mui/material";
import axios from "../../api/axios";
import { anonymousConfigType } from "../../redux/actions";
import { connect } from "react-redux";

interface LoginPageProps {
  config: anonymousConfigType;
}

const FormContent: React.FC<{
  formData: { username: string; password: string };
  handleChange: (e: ChangeEvent<HTMLInputElement>) => void;
}> = ({ formData, handleChange }) => (
  <>
    <TextField
      variant="outlined"
      fullWidth
      label="Username or Email"
      name="username"
      autoComplete="username"
      value={formData.username}
      onChange={handleChange}
      required
      sx={{ marginBottom: "1rem" }}
    />
    <TextField
      variant="outlined"
      fullWidth
      label="Password"
      name="password"
      type="password"
      autoComplete="current-password"
      value={formData.password}
      onChange={handleChange}
      required
      sx={{ marginBottom: "1rem" }}
    />
    <Button
      type="submit"
      fullWidth
      variant="contained"
      sx={{
        marginTop: "1.5rem",
        backgroundColor: "primary.main",
        color: "#fff",
        "&:hover": {
          backgroundColor: "primary.dark",
        },
      }}
    >
      Login
    </Button>
  </>
);

const LoginPage: React.FC<LoginPageProps> = ({ config }) => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const authType = config.authType;

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (authType.toUpperCase() === "JWT") {
      try {
        const response = await axios.post("/jwt/login", formData);
        localStorage.setItem("token", response.data.token);
        const users = axios.get("/user");
        console.log(users);
      } catch (err: any) {
        console.error("Login failed:", err);
      }
    }
  };

  return (
    <Box
      sx={{
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f0f2f5",
      }}
    >
      <Paper
        elevation={6}
        sx={{
          padding: "2rem",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          width: "400px",
          borderRadius: "10px",
          boxShadow: "0 3px 10px rgba(0,0,0,0.2)",
        }}
      >
        <Avatar
          sx={{ backgroundColor: "primary.main", marginBottom: "1rem" }}
        />
        <Typography component="h1" variant="h5">
          Login
        </Typography>
        <Box sx={{ width: "100%", marginTop: "1rem" }}>
          {authType.toUpperCase() === "JWT" ? (
            <form onSubmit={handleSubmit} noValidate>
              <FormContent formData={formData} handleChange={handleChange} />
            </form>
          ) : (
            <form method="POST" action="/app/public/login" noValidate>
              <FormContent formData={formData} handleChange={handleChange} />
            </form>
          )}
        </Box>
      </Paper>
    </Box>
  );
};

const mapStateToProps = (state: { config: anonymousConfigType }) => ({
  config: state.config,
});

export default connect(mapStateToProps)(LoginPage);
