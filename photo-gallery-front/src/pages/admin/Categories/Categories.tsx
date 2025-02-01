import css from './Categories.module.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';
import { useEffect, useState } from 'react';
import { authenticate } from '../../../services/UserService';
import { Link, useNavigate } from 'react-router-dom';
import { deleteCategory, getCategories } from '../../../services/CategoryService';
import { Category } from '../../../models/Category';

export default function CategoriesModule() {
  const [categories, setCategories] = useState<Category[]>([]);
  const navigate = useNavigate();
  const [successMessage, setSuccessMessage] = useState<string>('');
  const [errorMessage, setErrorMessage] = useState<string>('');

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

  async function delCategory(categoryId: number) {
    setSuccessMessage('');
    setErrorMessage('');
    const response = await deleteCategory(categoryId);
    if (response) {
      setErrorMessage('Something went wrong');
    } else {
      setSuccessMessage('Category was deleted successfully');
      const filteredCategories = categories.filter(c => c.id !== categoryId);
      setCategories(filteredCategories);
    }
  }

  return (
    <div className={css.page}>
      {successMessage && <p>{successMessage}</p>}
      {errorMessage && <p>{errorMessage}</p>}
      <div className={css.categories}>
        <h1>Manage categories</h1>
        <Link className={css.createLink} to='/admin/categories/create'>Create new category</Link>
        <ul className={css.categoryList}>
          {categories.map(c => 
            <li key={'category-' + c.id}>
              <div className={css.imgContainer}>
                <img src={c.thumbnailUrl} alt={c.name} />
              </div>
              <div className={css.imgInfo}>
                <h2>{c.name}</h2>
              </div>
              <div className={css.btnContainer}>
                <button onClick={() => delCategory(c.id)}>
                  <FontAwesomeIcon icon={faTrash} />
                </button>
                <Link to={'/admin/categories/' + c.accessUrl}>
                  <FontAwesomeIcon icon={faEdit} />
                </Link>
              </div>
            </li>
          )}
        </ul>
      </div>
    </div>
  );
}