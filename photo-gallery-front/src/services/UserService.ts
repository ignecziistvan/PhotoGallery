import httpRequest from "../interceptors/HttpRequest";
import { User } from "../models/User";

export async function getOwner(): Promise<User | undefined> {
  try {
    const response = (await httpRequest.get('/owner'));
    return response.data;
  } catch (error) {
    return undefined;
  }
}


export const login = async (username: string, password: string, setAuthenticated: React.Dispatch<React.SetStateAction<boolean>>) => { 
  const response = await httpRequest.post('login', {
    username: username,
    password: password
  });
  const token = response.data;
  localStorage.setItem('JWToken', token);
  setAuthenticated(true);
}

export const logout = (setAuthenticated: React.Dispatch<React.SetStateAction<boolean>>) => {
  try {
    localStorage.removeItem('JWToken');
    setAuthenticated(false);
    window.location.href = '/';
  } catch (e) {
    window.location.href = '/';
  }
}