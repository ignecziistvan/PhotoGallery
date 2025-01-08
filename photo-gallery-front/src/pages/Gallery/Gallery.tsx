import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getCategory, getCategoryByAccessUrl } from "../../services/CategoryService";
import { Category } from "../../models/Category";
import css from './Gallery.module.css';
import { Photo } from "../../models/Photo";
import { getPhotosOfCategory } from "../../services/PhotoService";

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
        <h1>{ category?.name }</h1>
        <p>{ category?.description }</p>
      </section>

      <div className={css.photoGrid}>
        {photos.map(photo => {
          return (
            <div className={css.imgContainer}>
              <img src={ photo.thumbnailUrl } alt={ category?.name } />
            </div>
          );
        })}
      </div>
    </>
  );
}