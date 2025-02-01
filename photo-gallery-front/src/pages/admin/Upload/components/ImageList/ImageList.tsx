import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import css from './ImageList.module.css';
import { faClose, faCrop, faEye } from '@fortawesome/free-solid-svg-icons';
import { Dispatch, SetStateAction } from 'react';
import { PhotoProvider, PhotoView } from 'react-photo-view';
import 'react-photo-view/dist/react-photo-view.css';

import notFoundImage from '../../../../../assets/404.png';

interface Props {
  files: File[];
  setFiles: Dispatch<SetStateAction<File[]>>;
  setCrop: Dispatch<SetStateAction<File | undefined>>;
  posting: boolean;
}

export default function ImageList(props: Props) {
  function removeFileFromList(file: File): void {
    props.setFiles(props.files.filter(f => f !== file));
  }

  return (
    <div className={css.fileListContainer}>
      <h3>Files to upload</h3>
      <ul>
        {props.files.map((file, index) => (
          <li key={index}>
            <div className={css.imgContainer}>
              <img src={URL.createObjectURL(file)} alt={file.name} />
            </div>
            <div className={css.imgInfo}>
              <p>Name: {file.name}</p>
              <p>Size: {(file.size / 1024 / 1024).toFixed(2) + ' MB'}</p>
            </div>
            <div className={css.btnContainer}>
              <PhotoProvider>
                <PhotoView src={file? URL.createObjectURL(file) : notFoundImage}>
                  <button disabled={props.posting}>
                    <FontAwesomeIcon icon={faEye} />
                  </button>
                </PhotoView>
              </PhotoProvider>
              <button onClick={() => props.setCrop(file)} disabled={props.posting}>
                <FontAwesomeIcon icon={faCrop} />
              </button>
              <button onClick={() => removeFileFromList(file)} disabled={props.posting}>
                <FontAwesomeIcon icon={faClose} />
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}