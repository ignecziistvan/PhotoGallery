import { Link } from 'react-router-dom';
import css from './Navbar.module.css';

export default function Navbar() {
  return (
    <nav className={css.navbar}>
      <Link to={'/'} className={css.homeLink}>Home</Link>
      <Link to={'/upload'}>Upload photos</Link>
    </nav>
  );
}