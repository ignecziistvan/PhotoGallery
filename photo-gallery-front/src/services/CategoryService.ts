import httpRequest from "../interceptors/HttpRequest";
import { Category } from "../models/Category";

export async function getCategories(): Promise<Category[]> {
  const response = await httpRequest.get('/media/categories');
  return response.data || [];
}

export async function getCategory(categoryId:number): Promise<Category|undefined> {
  const response = await httpRequest.get('/media/categories/' + categoryId);
  return response.data;
}

export async function getCategoryByAccessUrl(accessUrl?: string): Promise<Category|undefined> {
  if (!accessUrl) return;
  const response = await getCategories();
  return response.find(c => c.accessUrl === accessUrl);
}
