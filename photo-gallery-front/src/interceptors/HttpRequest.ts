import axios from "axios";

const httpRequest = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true
});

httpRequest.interceptors.request.use((config) => {
    config.headers.Accept = 'application/json';
    
    return config;
  }, (error) => {
    return Promise.reject(error);
  }
);

httpRequest.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('Error logged in httpRequest interceptor: ', error);
  }
);

export default httpRequest;