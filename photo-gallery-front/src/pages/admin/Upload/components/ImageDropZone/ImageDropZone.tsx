import { Dispatch, SetStateAction, useCallback } from 'react';
import css from './ImageDropZone.module.css';
import { useDropzone } from 'react-dropzone';

export default function ImageDropZone({ setFiles } : { setFiles:Dispatch<SetStateAction<File[]>> }) {
  const onDrop = useCallback((acceptedFiles: File[]) => {
    setFiles((prevFiles) => [...prevFiles, ...acceptedFiles]);
  }, []);
  
  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: {
      'image/jpg': [],
      'image/jpeg': [],
      'image/png': [],
      'image/webp': []
    },
    multiple: true
  });

  return (
    <div {...getRootProps()} className={css.dropzone}>
      <input {...getInputProps()} />
      {isDragActive ? (
        <p>Drop the files here...</p>
      ) : (
        <p>Drop files here or click to select files</p>
      )}
    </div>
  );
}