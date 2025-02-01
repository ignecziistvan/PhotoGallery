import httpRequest from "../interceptors/HttpRequest";

export async function getPhotosOfCategory(categoryId: number) {
  const response = await httpRequest.get('/media/open/v2/photos', {
    params: {
      categoryId: categoryId
    }
  });
  return response.data;
}