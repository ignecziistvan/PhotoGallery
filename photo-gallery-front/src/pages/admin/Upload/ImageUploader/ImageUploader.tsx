import { useState } from 'react';
import css from './ImageUploader.module.css';
import { uploadFiles } from './ImageUploaderService';
import ImageCropper from '../components/ImageCropper/ImageCropper';
import ImageDropZone from '../components/ImageDropZone/ImageDropZone';
import ImageList from '../components/ImageList/ImageList';
import 'react-photo-view/dist/react-photo-view.css';

export default function ImageUploader({ categoryId }: { categoryId: number }) {
  const [files, setFiles] = useState<File[]>([]);
  const [posting, setPosting] = useState<boolean>(false);
  const [croppingFile, setCroppingFile] = useState<File | undefined>(undefined);
  const [progress, setProgress] = useState<number>(0);

  function onCropDone(croppedFile: File | undefined) {
    if (!croppingFile || !croppedFile) return;
  
    const updatedFiles = files.map((file) =>
      file.name === croppingFile.name ? croppedFile : file
    );
  
    setFiles(updatedFiles);
    setCroppingFile(undefined);
  }

  function onCropCancel() {
    setCroppingFile(undefined);
  }

  if (croppingFile) {
    return (
      <ImageCropper
        file={croppingFile}
        onCropDone={onCropDone}
        onCropCancel={onCropCancel}
      />
    );
  } else {
    return (
      <div className={css.uploaderComponent}>
        <div className={css.uploadSection}>
          <ImageDropZone setFiles={setFiles} />
          {files.length > 0 && (
            <button
              className={css.uploadButton}
              onClick={() => uploadFiles(categoryId, files, setPosting, setFiles, setProgress)}
            >
              Upload
            </button>
          )}
        </div>

        {posting &&
          <>
            <h2>Uploading...</h2>
            <h3>Please wait</h3>
            <div className={css.progressBar}>
              <div 
                className={css.progress} 
                style={{
                  width: progress + '%'
                }}
              ></div>
            </div>
            <p>{progress}%</p>
          </>
        }

        {files.length > 0 && (
          <ImageList 
            files={files} 
            setFiles={setFiles} 
            setCrop={setCroppingFile} 
            posting={posting}
          />
        )}
      </div>
    );
  }
}
