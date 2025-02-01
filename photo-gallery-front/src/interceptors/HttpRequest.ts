import axios from "axios";
import { toast } from "react-toastify";

const httpRequest = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true
});

httpRequest.interceptors.request.use((config) => {
    config.headers.Accept = 'application/json';

    const jwt = localStorage.getItem('JWToken');

    if (jwt) {
      config.headers.Authorization = 'Bearer ' + jwt;
    }

    return config;
  }
);

httpRequest.interceptors.response.use(
  (response) => response,
  (error) => {
    const message = error.response.data?.error || error.response.data?.message || "An error has occurred";
    toast.error(message);
    return Promise.reject(error);
  }
);


export default httpRequest;