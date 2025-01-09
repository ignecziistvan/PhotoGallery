import { useCallback, useState } from 'react';
import css from './ImageUploader.module.css';
import { useDropzone } from 'react-dropzone';
import { uploadFiles } from './ImageUploaderService';

export default function ImageUploader({ categoryId } : { categoryId: number }) {
  const [files, setFiles] = useState<File[]>([]);
  const [posting, setPosting] = useState<boolean>(false);

  const onDrop = useCallback((acceptedFiles: File[]) => {
    setFiles((prevFiles) => [...prevFiles, ...acceptedFiles]);
  }, []);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: {
      'image/jpeg': [],
      'image/png': []
    },
    multiple: true
  });

  function removeFileFromList(file: File) {
    setFiles(files.filter(f => f !== file));
  }

  if (posting) {
    return (
      <div className={css.uploaderComponent}>
        <h1>Uploading</h1>
      </div>
    );
  } else return (
    <div className={css.uploaderComponent}>
      <div className={css.uploadSection}>
        <div {...getRootProps()} className={css.dropzone}>
          <input {...getInputProps()} />
          {isDragActive ? (
            <p>Drop the files here...</p>
          ) : (
            <p>Drop files here or click to select files</p>
          )}
        </div>

        <button 
          className={css.uploadButton} 
          onClick={() => uploadFiles(categoryId, files, setPosting, setFiles)}
        >Upload</button>
      </div>

      {files.length > 0 && 
        <div className={css.fileListContainer}>
          <h3>Files to upload</h3>
          <ul>
            {files.map((file, index) => (
              <li key={index}>
                <div className={css.imgContainer}>
                  <img src={URL.createObjectURL(file)} alt={file.name} />
                </div>
                <div className={css.imgInfo}>
                  <p>Name: {file.name}</p>
                  <p>Size: {(file.size / 1024 / 1024).toFixed(2) + ' MB'}</p>
                </div>
                <button onClick={() => removeFileFromList(file)}>X</button>
              </li>
            ))}
          </ul>
        </div>
      }
    </div>
  );
}