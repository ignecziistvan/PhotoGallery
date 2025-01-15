import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getCategoryByAccessUrl } from "../../services/CategoryService";
import { Category } from "../../models/Category";
import css from './Gallery.module.css';
import { Photo } from "../../models/Photo";
import { getPhotosOfCategory } from "../../services/PhotoService";
import { PhotoProvider, PhotoView } from "react-photo-view";
import 'react-photo-view/dist/react-photo-view.css';

export default function GalleryComponent() {
  const [category, setCategory] = useState<Category>();
  const [photos, setPhotos] = useState<Photo[]>([]);
  const { categoryAccessUrl } = useParams();

  useEffect(() => {
    async function init() {
      const category = await getCategoryByAccessUrl(categoryAccessUrl?.toString());
      if (!category) return;

      setCategory(category);

      const photos = await getPhotosOfCategory(category.id);
      setPhotos(photos);
    }

    init();
  }, []);

  return (
    <>
      <section className={css.header}>
        <h1>{ category?.name } photos</h1>
      </section>

      <PhotoProvider>
        <div className={css.photoGrid}>
          {photos.map(photo => {
            return (
              <PhotoView src={photo.url} key={'photo-' + photo.id}>
                <div className={css.imgContainer}>
                  <img src={ photo.thumbnailUrl } alt={ category?.name } />
                </div>
              </PhotoView>
            );
          })}
        </div>
      </PhotoProvider>
    </>
  );
}