import { useEffect, useState } from 'react';
import css from './Upload.module.css';
import { Category } from '../../../models/Category';
import { getCategories } from '../../../services/CategoryService';
import ImageUploader from './ImageUploader/ImageUploader';

export default function UploadComponent() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<Category | undefined>();

  useEffect(() => {
      async function init() {
        const categories = await getCategories();
        setCategories(categories);
      };
  
      init();
    }, []);

  const handleCategoryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedId = parseInt(e.target.value, 10);
    
    const category = categories.find(c => c.id === selectedId);
    
    if (category) {
      setSelectedCategory(category);
    }
  };

  return (
    <div className={css.page}>
      {selectedCategory ? (
        <>
          <div className={css.categorySelection}>
            <h2>Select category</h2>
            <select onChange={handleCategoryChange} value={selectedCategory.id}>
              <option value="" disabled hidden>
                Select a category
              </option>
              {categories.map(c => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>
            <ImageUploader categoryId={selectedCategory.id} />
          </div>
        </>
      ) : (
        <div className={css.categorySelection}>
          <h2>Select category</h2>
          <select onChange={handleCategoryChange} value={selectedCategory}>
            <option value="" disabled hidden>
              Select a category
            </option>
            {categories.map(c => (
              <option key={c.id} value={c.id}>
                {c.name}
              </option>
            ))}
          </select>
        </div>
      )}
    </div>
  );
}
