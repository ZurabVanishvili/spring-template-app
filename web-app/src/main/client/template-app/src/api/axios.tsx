import axios from "axios";
import { BASE_REST_URL } from "../AppUtil";

const instance = axios.create({
  baseURL: BASE_REST_URL,
  timeout: 30_000,
});

instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
export default instance;
