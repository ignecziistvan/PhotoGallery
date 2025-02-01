import httpRequest from "../interceptors/HttpRequest";
import { Category, CreateCategoryRequest, PatchCategoryRequest } from "../models/Category";

export async function getCategories(): Promise<Category[]> {
  const response = await httpRequest.get('/media/open/v2/categories');
  return response.data || [];
}

export async function getCategory(categoryId:number): Promise<Category|undefined> {
  const response = await httpRequest.get('/media/open/v2/categories/' + categoryId);
  return response.data;
}

export async function getCategoryByAccessUrl(accessUrl?: string): Promise<Category|undefined> {
  if (!accessUrl) return;
  const response = await getCategories();
  return response.find(c => c.accessUrl === accessUrl);
}



export async function createCategory(form: CreateCategoryRequest) {
  try {
    await httpRequest.post('/media/auth/v2/categories/', form);
    return null;
  } catch (error: any) {
    return error.response.data.error;
  }
}

export async function deleteCategory(categoryId: number) {
  try {
    await httpRequest.delete('/media/auth/v2/categories/' + categoryId);
    return null;
  } catch (error: any) {
    return error.response.data.error;
  }
}

export async function patchCategory(categoryId: number|undefined, form: PatchCategoryRequest) {
  if (!categoryId) return 'Couldnt read the ID of category';

  try {
    await httpRequest.put('/media/auth/v2/categories/' + categoryId, form);
    return null;
  } catch (error: any) {
    return error.response.data.error;
  }
}