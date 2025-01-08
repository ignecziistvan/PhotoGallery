import { useEffect, useState } from 'react';
import css from './Home.module.css';
import { getCategories } from '../../services/CategoryService';
import { Category } from '../../models/Category';
import { Link } from 'react-router-dom';

export default function HomeComponent() {
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    async function init() {
      const response = await getCategories();
      setCategories(response);
    }
    
    init();
  }, []);

  return (
    <>
      <section className={css.introduce}>
        <h1>Welcome to PhotoGallery!</h1>
        <h3>Take a look around the site</h3>
      </section>

      <section className={css.categories}>
        {categories.map(category => {
          return (
            <Link
              to={'category/' + category.accessUrl} 
              key={'category-' + category.id} 
              className={css.categoryContainer}
            >
              <div className={css.imgContainer}>
                <img src={category.thumbnailUrl} alt={ category.name } />
              </div>
              <h3>{ category.name }</h3>
            </Link>
          )
        })}
      </section>
    </>
  );
}