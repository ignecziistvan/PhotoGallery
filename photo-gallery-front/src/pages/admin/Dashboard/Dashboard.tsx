import { useEffect, useState } from 'react';
import css from './Dashboard.module.css';
import { getCategories } from '../../../services/CategoryService';
import { Category } from '../../../models/Category';
import Upload from '../../../components/Upload/Upload';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBackward } from '@fortawesome/free-solid-svg-icons/faBackward';
import { logout } from '../../../services/UserService';

export default function DashboardComponent({ authenticated, setAuthenticated } : { authenticated: boolean, setAuthenticated: React.Dispatch<React.SetStateAction<boolean>> }) {
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    async function init() {
      const categories = await getCategories();
      setCategories(categories);
    };

    init();
  }, []);

  if (!authenticated) {
    return (
      <>
        <h1>You have no permission to visit this page</h1>
      </>
    );
  } else return (
    <div className={css.page}>
      <section className={css.logout}>
        <button onClick={() => logout(setAuthenticated)}>
          <FontAwesomeIcon icon={faBackward} />
          Logout
        </button>
      </section>

      <section className={css.uploadSection}>
        <h1>Upload photos</h1>
        <Upload categories={categories} />
      </section>

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
            </li>
          )}
        </ul>
      </section>
    </div>
  );
}