import httpRequest from "../../interceptors/HttpRequest";

export async function uploadFiles(categoryId: number, files: File[], setPosting: React.Dispatch<React.SetStateAction<boolean>>) {
  if (files.length === 0) return;
  setPosting(true);
  const response = await httpRequest.post('/media/categories/' + categoryId + '/upload', files);
  setPosting(false);
  return response.data;
}