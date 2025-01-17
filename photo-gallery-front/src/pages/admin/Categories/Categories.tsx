import css from './Categories.module.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faEdit, faAdd } from '@fortawesome/free-solid-svg-icons';
import { useEffect, useState } from 'react';
import { authenticate } from '../../../services/UserService';
import { Link, useNavigate } from 'react-router-dom';
import { getCategories } from '../../../services/CategoryService';
import { Category } from '../../../models/Category';

export default function CategoriesModule() {
  const [categories, setCategories] = useState<Category[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    async function init() {
      try {
        const response = await authenticate();
        if (!response) navigate('/login');
      } catch (error) {
        navigate('/login');
      }

      const categories = await getCategories();
      setCategories(categories);
    };

    init();
  }, []);

  return (
    <>
    <section className={css.categorySection}>
        <h1>Manage categories</h1>
        <ul className={css.categoryList}>
          {categories.map(c => 
            <li key={'category-' + c.id}>
              <div className={css.imgContainer}>
                <img src={c.thumbnailUrl} alt={c.name} />
              </div>
              <div className={css.imgInfo}>
                <h2>{c.name}</h2>
              </div>
              <button>
                <FontAwesomeIcon icon={faTrash} />
              </button>
              <Link to={'/admin/categories/' + c.accessUrl}>
                <FontAwesomeIcon icon={faEdit} />
              </Link>
            </li>
          )}
        </ul>
      </section>
    </>
  );
}