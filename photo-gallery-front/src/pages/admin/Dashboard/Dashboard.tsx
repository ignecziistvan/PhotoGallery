import css from './Dashboard.module.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBackward } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';

export default function DashboardComponent() {
  return (
    <div className={css.page}>
      <section className={css.logout}>
        <Link to={'/logout'}>
          <FontAwesomeIcon icon={faBackward} />
          Logout
        </Link>
      </section>

      <section className={css.options}>
        <Link to='/admin/upload' className={css.option}>
          <h1>Upload photos</h1>
        </Link>
        <Link to='/admin/categories' className={css.option}>
          <h1>Manage categories</h1>
        </Link>
      </section>
    </div>
  );
}