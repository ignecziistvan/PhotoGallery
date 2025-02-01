import { useEffect, useState } from 'react';
import { Category } from '../../../../models/Category';
import css from './ImageSlider.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft, faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export default function ImageSlider({ categories } : { categories : Category[] }) {
  const { t } = useTranslation();
  const [selectedCategory, setSelectedCategory] = useState<Category>();

  useEffect(() => {
    setSelectedCategory(categories[0]);
  }, [categories]);

  function paginateBack() {
    const currentIndex = categories.findIndex(c => c.id === selectedCategory?.id);
    const prevCategory = categories[currentIndex - 1] || categories[currentIndex];
    setSelectedCategory(prevCategory);
    return prevCategory;
  }

  function paginateForward() {
    const currentIndex = categories.findIndex(c => c.id === selectedCategory?.id);
    const nextCategory = categories[currentIndex + 1] || categories[currentIndex];
    setSelectedCategory(nextCategory); 
    return nextCategory;
  }

  function getPreviousCategory() {
    const currentIndex = categories.findIndex(c => c.id === selectedCategory?.id);
    return categories[currentIndex - 1] || undefined;
  }

  function getNextCategory() {
    const currentIndex = categories.findIndex(c => c.id === selectedCategory?.id);
    return categories[currentIndex + 1] || undefined;
  }

  return (
    <div className={css.slider}>
      <div 
        className={css.background}
        style={{
          backgroundImage: "url(" + selectedCategory?.thumbnailUrl + ")"
        }} 
      />

      <div className={css.info}>
        <h1>{selectedCategory?.name}</h1>
      </div>

      <div className={css.links}>
        <Link to={'/categories/' + selectedCategory?.accessUrl}>{t('imageSlider.see_gallery')}</Link>
        <Link to={'/categories'}>{t('imageSlider.see_all_categories')}</Link>
      </div>

      <div className={css.itemsContainer}>
        <div className={css.itemsList}>
          <div 
            className={css.item}
            id='prevItem'
            style={{
              backgroundImage: "url(" + getPreviousCategory()?.thumbnailUrl + ")"
            }}
            onClick={() => getPreviousCategory() !== undefined ? setSelectedCategory(getPreviousCategory()) : null}
          >
            <h3>{getPreviousCategory()?.name}</h3>
          </div>
          <div 
            className={css.itemSelected}
            style={{
              backgroundImage: "url(" + selectedCategory?.thumbnailUrl + ")"
            }}
          >
            <h3>{selectedCategory?.name}</h3>
          </div>
          <div 
            className={css.item}
            style={{
              backgroundImage: "url(" + getNextCategory()?.thumbnailUrl + ")"
            }}
            onClick={() => getNextCategory() !== undefined ? setSelectedCategory(getNextCategory()) : null}
          >
            <h3>{getNextCategory()?.name}</h3>
          </div> 
        </div>
        <div className={css.btnContainer}>
          <button 
            onClick={paginateBack}
            disabled={categories.findIndex(c => c.id === selectedCategory?.id) === 0}
          >
            <FontAwesomeIcon icon={faArrowLeft}></FontAwesomeIcon>
          </button>
          <button 
            onClick={paginateForward}
            disabled={categories.findIndex(c => c.id === selectedCategory?.id) === categories.length - 1}
          >
            <FontAwesomeIcon icon={faArrowRight}></FontAwesomeIcon>
          </button>
        </div>
      </div>
    </div>
  );
}