import httpRequest from "../../interceptors/HttpRequest";
import { Mail } from "../../models/Mail";

export async function sendMail(form: Mail) {
  try {
    await httpRequest.post('/mail/send', form);
    return null;
  } catch (error: any) {
    return error.response.data.errors || error.response.data.error;
  }
}