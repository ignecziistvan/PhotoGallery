import httpRequest from "../../../../interceptors/HttpRequest";

export async function uploadFiles(
  categoryId: number, 
  files: File[], 
  setPosting: React.Dispatch<React.SetStateAction<boolean>>, 
  setFiles: React.Dispatch<React.SetStateAction<File[]>>,
  setProgress: React.Dispatch<React.SetStateAction<number>>
) {
  if (files.length === 0) return;
  
  const formData = new FormData();
  formData.append("categoryId", categoryId.toString());
  files.forEach(file => {
    formData.append("files", file);
  });

  setPosting(true);
  try {
    const response = await httpRequest.post('/media/auth/v2/photos/upload', formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      },
      onUploadProgress: (progressEvent) => {
        if (progressEvent.total) {
          setProgress(Math.round((progressEvent.progress ?? 0) * 100));
        }
      }
    });
    setFiles([]);
    return response.data;
  } catch (error) {
    console.error("Error uploading files");
  } finally {
    setPosting(false);
    setProgress(0);
  }
}