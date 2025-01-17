export interface Category {
  id: number;
  name: string;
  accessUrl: string;
  description?: string;
  thumbnailUrl?: string;
}



export interface CreateCategoryRequest {
  name: string;
  description: string;
}

export interface PatchCategoryRequest {
  name: string;
  description: string;
  thumbnailId?: number;
}