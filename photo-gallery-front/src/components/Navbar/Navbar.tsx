import css from './Navbar.module.css';
import img from '../../assets/nav_icon.webp';
import { Link, useLocation } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export default function Navbar() {
  const { t } = useTranslation();
  const location  = useLocation();
  const activeLink = (path: string) => {
    return location.pathname === path;
  };

  return (
    <nav className={css.nav}>
      <div>
        <img src={img} alt="Home" />
        <Link to={'/'} className={activeLink('/') ? css.activeLink : css.link}>Photo Gallery</Link>
      </div>
      <div>
        <Link to={'/categories'} className={activeLink('/categories') ? css.activeLink : css.link}>{t('navbar.explore')}</Link>
        <Link to={'/contact'} className={activeLink('/contact') ? css.activeLink : css.link}>{t('navbar.contact')}</Link>
      </div>
    </nav>
  );
}