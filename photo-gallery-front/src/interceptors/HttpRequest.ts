import axios from "axios";
import { toast } from "react-toastify";

const httpRequest = axios.create({
  //baseUrl: 'http://localhost:5000/api',
  baseURL: 'http://photo-gallery-api-demo.eu-north-1.elasticbeanstalk.com/api',
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