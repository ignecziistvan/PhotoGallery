import { useEffect, useState } from 'react';
import css from './Categories.module.css';
import { Category } from '../../models/Category';
import { getCategories } from '../../services/CategoryService';
import { Link } from 'react-router-dom';
import Footer from '../../components/Footer/Footer';

export default function CategoriesPage() {
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    async function init() {
      const response = await getCategories();
      setCategories(response);
    }

    init();
  }, []);


  return (
    <div className={css.page}>
      <div className={css.list}>
        {categories.map(c => {
          return (
            <Link to={'/categories/' + c.accessUrl} className={css.item} key={'category-' + c.id}>
              <div className={css.imgContainer}>
                <img src={c.thumbnailUrl} alt={c.name} />
              </div>
              <h2>{c.name}</h2>
            </Link>
          );
        })}
      </div>
      <Footer />
    </div>
  );
}