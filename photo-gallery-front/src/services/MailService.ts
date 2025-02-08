import httpRequest from "../interceptors/HttpRequest";
import { Mail } from "../models/Mail";

export async function sendMail(form: Mail) {
  try {
    await httpRequest.post('/mail/v2/send', form);
    return null;
  } catch (error: any) {
    return error.response.data || error.response.data;
  }
}