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

export async function authenticate() {
  try {
    const response = await httpRequest.get('authenticate');
    if (response) return true; else return false;
  } catch (error) {
    return false;
  }
}