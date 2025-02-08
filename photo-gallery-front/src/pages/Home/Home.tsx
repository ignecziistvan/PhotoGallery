import css from './Home.module.css';
import hero_img from '../../assets/header.jpg';
import { useEffect, useState } from 'react';
import { Category } from '../../models/Category';
import { getCategories } from '../../services/CategoryService';
import ImageSlider from './components/ImageSlider/ImageSlider';
import Footer from '../../components/Footer/Footer';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';

export default function HomePage() {
  const { t } = useTranslation();
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
      <section 
        className={css.hero}
        style={{
          backgroundImage: 'url(' + hero_img + ')'
        }}
      >
        <div>
          <h1>{t('welcome.h1')}</h1>
          <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aut, fugit tempora ad odit laboriosam dolores repudiandae debitis accusamus aspernatur. Fuga accusamus voluptatibus ullam eos facere obcaecati sint quaerat deleniti sapiente.</h3>
          <Link to={'https://www.github.com/ignecziistvan/PhotoGallery'}>{t('welcome.a_github_sc')}</Link>
        </div>
      </section>

      <section 
        className={css.categories}
      >
        <ImageSlider categories={categories} />
      </section>

      <section>
        <Footer />
      </section>
      
    </div>
  );
}