import { useEffect } from 'react';
import css from './Dashboard.module.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBackward } from '@fortawesome/free-solid-svg-icons';
import { authenticate, logout } from '../../../services/UserService';
import { Link, useNavigate } from 'react-router-dom';

export default function DashboardComponent() {
  const navigate = useNavigate();

  useEffect(() => {
    async function init() {
      try {
        const response = await authenticate();
        if (!response) navigate('/login');
      } catch (error) {
        navigate('/login');
      }
    };

    init();
  }, []);

  return (
    <div className={css.page}>
      <section className={css.logout}>
        <button onClick={() => logout()}>
          <FontAwesomeIcon icon={faBackward} />
          Logout
        </button>
      </section>

      <Link to='/admin/upload' className={css.option}>
        <h1>Upload photos</h1>
      </Link>
      <Link to='/admin/categories' className={css.option}>
        <h1>Manage categories</h1>
      </Link>
    </div>
  );
}