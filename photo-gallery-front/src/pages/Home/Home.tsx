import { useEffect, useState } from 'react';
import css from './Home.module.css';
import { getCategories } from '../../services/CategoryService';
import { Category } from '../../models/Category';
import { Link } from 'react-router-dom';
import Footer from '../../components/Footer/Footer';

import introducePhoto from '../../assets/introduce_photo.webp';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft, faArrowRight } from '@fortawesome/free-solid-svg-icons';

export default function HomeComponent() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [windowScroll, setWindowScroll] = useState<number>(0);
  const [selectedCategory, setSelectedCategory] = useState<Category | undefined>();

  useEffect(() => {
    async function init() {
      const response = await getCategories();
      setCategories(response);
      setSelectedCategory(response[0]);
      window.scrollTo(0,0);
    }
    
    init();
    window.addEventListener('scroll', () => setWindowScroll(window.scrollY));
    return () => window.removeEventListener('scroll', () => setWindowScroll(window.scrollY));
  }, []);

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

  function handleScrollToSelected() {
    const currentIndex = categories.findIndex(c => c.id === selectedCategory?.id);
    return window.innerWidth <= 768 ? -((currentIndex - 0.25) * 65) : -(currentIndex - 1) * 35;
  }

  return (
    <>
      <section 
        className={css.introduce}
        style={{ 
          opacity: window.innerWidth > 768 ? (1 - windowScroll * 5 / window.innerHeight) : (1 - windowScroll * 1.2 / window.innerHeight)
        }}
      >
        <div className={css.imgContainer}>
          <img src={introducePhoto} alt="Photo Gallery" />
        </div>
        <h1>Welcome to PhotoGallery!</h1>
        <h3>This website is a demo project</h3>
        <p>This website is a demo project I created to expand my portfolio and sharpen my skills. It's a fully functional, full-stack web application.</p>
        <p>Feel free to explore the site</p>
        <p>or</p>
        <a href="https://github.com/ignecziistvan/PhotoGallery">see the source code in the github repo</a>
      </section>

      <section 
        className={css.categoriesSection}
        style={{ 
          opacity: 0 + windowScroll * 3 / window.innerHeight
        }}
      >

        <div 
          className={css.background}
          style={{
            background: "url('" + selectedCategory?.thumbnailUrl + "') no-repeat center center / cover"
          }} 
        />

        <div className={css.selectedCategoryInfo}>
          <h1>{selectedCategory?.name}</h1>
          <p>{selectedCategory?.description}</p>
          <Link to={'/category/' + selectedCategory?.accessUrl}>See gallery</Link>
        </div>
        
        <div className={css.categoriesContainer}>
          <div 
            className={css.categoryList}
            style={{
              transform: "translateX(" + handleScrollToSelected() + "%)",
              transition: "transform 0.4s ease"
            }}
          >
            {categories.map(category => 
              <div 
                className={category === selectedCategory ? css.categorySelected : css.categoryUnselected} 
                onClick={() => setSelectedCategory(category)}
                key={'category-' + category.id}
              >
                <img src={category.thumbnailUrl} alt={category.name} />
              </div>
            )}
          </div>
          <div className={css.categoriesPagination}>
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
      </section>

      <Footer />
    </>
  );
}