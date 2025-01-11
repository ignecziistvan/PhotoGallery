import { useEffect, useRef, useState } from 'react';
import css from './Home.module.css';
import { getCategories } from '../../services/CategoryService';
import { Category } from '../../models/Category';
import { Link } from 'react-router-dom';

export default function HomeComponent() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [windowScroll, setWindowScroll] = useState<number>(0);
  const [selectedCategory, setSelectedCategory] = useState<Category | undefined>();

  const introduceRef = useRef<HTMLElement>(null);
  const categoriesRef = useRef<HTMLElement>(null);

  useEffect(() => {
    async function init() {
      const response = await getCategories();
      setCategories(response);
      setSelectedCategory(response[0]);
    }
    
    init();

    window.addEventListener('scroll', () => setWindowScroll(window.scrollY));
    return () => window.removeEventListener('scroll', () => setWindowScroll(window.scrollY));
  }, []);

  return (
    <>
      <section 
        className={css.introduce}
        style={{ opacity: 1 - windowScroll * 3 / window.innerHeight }}
      >
        <h1>Welcome to PhotoGallery!</h1>
        <h3>Take a look around the site</h3>
      </section>

      <section 
        className={css.categories}
        style={{ 
          opacity: 0 + windowScroll / window.innerHeight
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
          {categories.map((category, index) => {
            return category.id === selectedCategory?.id ? (
              <div className={css.categorySelected}>
                <img src={category.thumbnailUrl} alt="" />
              </div>
            ) : (
              <div className={css.categoryUnselected} onClick={() => setSelectedCategory(categories[index])}>
                <img src={category.thumbnailUrl} alt={category.name} />
              </div>
            );
          })}
        </div>
        
      </section>
    </>
  );
}