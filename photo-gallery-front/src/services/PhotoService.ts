import httpRequest from "../interceptors/HttpRequest";

export async function getPhotosOfCategory(categoryId: number) {
  const response = await httpRequest.get('/media/categories/' + categoryId + '/photos');
  return response.data;
}