import { useEffect, useState } from 'react';
import css from './ImageCropper.module.css';
import Cropper, { Area } from 'react-easy-crop';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClose, faCheck } from '@fortawesome/free-solid-svg-icons';

interface Props {
  file: File;
  onCropDone: (croppedArea: File | undefined) => void;
  onCropCancel: () => void;
}

const aspectRatios = [
  { label: '1', value: 1 },
  { label: '4/3', value: 4 / 3 },
  { label: '16/9', value: 16 / 9 },
  { label: '21/9', value: 21 / 9 },
];

export default function ImageCropper(props: Props) {
  const [image, setImage] = useState<string>('');
  const [crop, setCrop] = useState({ x: 0, y: 0 });
  const [zoom, setZoom] = useState(1);

  const [croppedArea, setCroppedArea] = useState<Area>();
  const [aspectRatio, setAspectRatio] = useState(4 / 3);
  const [aspectRatioOfLoadedImage, setAspectRatioOfLoadedImage] = useState<number>(0);

  useEffect(() => {
    const reader = new FileReader();
    reader.readAsDataURL(props.file);
    reader.onload = () => {
      const result = reader.result as string;
      setImage(result);

      const img = new Image();
      img.src = result;
      img.onload = () => {
        setAspectRatioOfLoadedImage(img.width / img.height);
        setAspectRatio(img.width / img.height);
      };
    };
  }, [props.file]);

  function onCropComplete(_:any, croppedAreaPixels: Area) {
    setCroppedArea(croppedAreaPixels);
  }

  function cropImage(croppedArea: Area): Promise<File | undefined> {
    const canvas = document.createElement('canvas');
    canvas.width = croppedArea.width;
    canvas.height = croppedArea.height;
  
    const context = canvas.getContext('2d');
    if (!context) return Promise.resolve(undefined);
  
    const imgObject = new Image();
    imgObject.src = image;
  
    return new Promise((resolve) => {
      imgObject.onload = function () {
        context.drawImage(
          imgObject,
          croppedArea.x,
          croppedArea.y,
          croppedArea.width,
          croppedArea.height,
          0,
          0,
          croppedArea.width,
          croppedArea.height
        );
  
        canvas.toBlob((blob) => {
          if (!blob) {
            resolve(undefined);
          } else {
            const croppedFile = new File([blob], props.file.name, { type: "image/jpeg" });
            resolve(croppedFile);
          }
        }, "image/jpeg");
      };
    });
  }
  

  return (
    <div className={css.cropperComponent}>
      <Cropper
        image={image}
        crop={crop}
        zoom={zoom}
        aspect={aspectRatio}
        onCropChange={setCrop}
        onZoomChange={setZoom}
        onCropComplete={onCropComplete}
        style={{
          containerStyle: {
            width: '100%',
            height: '80vh',
          },
        }}
      />

      <div className={css.btnContainer}>
        <div className={css.aspectRatios}>
          <label>
            <input
              type="radio"
              name="ratio"
              checked={aspectRatio === aspectRatioOfLoadedImage}
              onChange={() => setAspectRatio(aspectRatioOfLoadedImage)}
            />{' '}
            Original
          </label>
          {aspectRatios.map(({ label, value }) => (
            <label key={'aspect-' + label}>
              <input
                type="radio"
                name="ratio"
                checked={aspectRatio === value}
                onChange={() => setAspectRatio(value)}
              />{' '}
              {label}
            </label>
          ))}
        </div>
        <button
          onClick={async () => {
            const croppedFile = await cropImage(croppedArea!);
            props.onCropDone(croppedFile);
          }}
        ><FontAwesomeIcon icon={faCheck} /></button>
        <button onClick={props.onCropCancel}>
          <FontAwesomeIcon icon={faClose} />
        </button>
      </div>
    </div>
  );
}
