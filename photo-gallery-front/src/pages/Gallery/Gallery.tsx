import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getCategoryByAccessUrl } from "../../services/CategoryService";
import { Category } from "../../models/Category";
import css from './Gallery.module.css';
import { Photo } from "../../models/Photo";
import { getPhotosOfCategory } from "../../services/PhotoService";
import { PhotoProvider, PhotoView } from "react-photo-view";
import 'react-photo-view/dist/react-photo-view.css';
import { useTranslation } from "react-i18next";
import Footer from "../../components/Footer/Footer";

export default function GalleryComponent() {
  const { t } = useTranslation();
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
        <h1>{ category?.name + " " + t('gallery.photos')}</h1>
      </section>

      <PhotoProvider>
        <div className={css.photoGrid}>
          {photos.map(photo => {
            return (
              <PhotoView src={photo.url} key={'photo-' + photo.id}>
                <div className={css.imgContainer}>
                  <img src={ photo.thumbnailUrl } alt={ category?.name } loading="lazy" decoding="async" />
                </div>
              </PhotoView>
            );
          })}
        </div>
      </PhotoProvider>
      <Footer />
    </>
  );
}