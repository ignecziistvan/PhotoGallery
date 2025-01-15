import axios from "axios";

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
  }, (error) => {
    return Promise.reject(error);
  }
);


export default httpRequest;