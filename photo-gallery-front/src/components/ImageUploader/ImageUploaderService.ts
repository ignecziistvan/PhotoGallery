import httpRequest from "../../interceptors/HttpRequest";

export async function uploadFiles(
  categoryId: number, 
  files: File[], 
  setPosting: React.Dispatch<React.SetStateAction<boolean>>, 
  setFiles: React.Dispatch<React.SetStateAction<File[]>>
) {
  if (files.length === 0) return;
  
  const formData = new FormData();
  files.forEach(file => {
    formData.append("files[]", file);
  });


  setPosting(true);
  const response = await httpRequest.post('/media/categories/' + categoryId + '/upload', formData, {
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });
  setPosting(false);
  setFiles([]);
  return response.data;
}