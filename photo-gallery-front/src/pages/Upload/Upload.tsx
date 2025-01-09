import { useEffect, useState } from 'react';
import ImageUploader from '../../components/ImageUploader/ImageUploader';
import css from './Upload.module.css';
import { Category } from '../../models/Category';
import { getCategories } from '../../services/CategoryService';

export default function UploadComponent() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<Category|undefined>();

  useEffect(() => {
    async function init() {
      const response = await getCategories();
      setCategories(response);
    }
    
    init();
  }, []);

  return (
    <div className={css.page}>
      {selectedCategory ?
        <div className={css.imgUploadContainer}>
          <ImageUploader categoryId={selectedCategory?.id} />
        </div>
        :
        <div className={css.categorySelection}>
          <h3>Select category</h3>
          <ul>
            {categories.map(c => {
              return (
                <li key={'category-' + c.id} onClick={() => setSelectedCategory(c)}>
                  <div className={css.imgContainer}>
                    <img src={c.thumbnailUrl} alt={c.name} />
                  </div>
                  <div className={css.imgInfo}>
                    <h2>{c.name}</h2>
                  </div>
                </li>
              );
            })}
          </ul>
        </div>
      }
    </div>
  );
}