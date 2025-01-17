import { useEffect, useState } from 'react';
import { getCategoryByAccessUrl } from '../../../services/CategoryService';
import { getPhotosOfCategory } from '../../../services/PhotoService';
import css from './CategoryEdit.module.css';
import { useParams } from 'react-router-dom';
import { Photo } from '../../../models/Photo';
import { Category } from '../../../models/Category';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faClose } from '@fortawesome/free-solid-svg-icons';

interface Form {
  name: string;
  description: string;
  thumbnailId?: number;
}

export default function CategoryEditModule() {
  const [category, setCategory] = useState<Category>();
  const [photos, setPhotos] = useState<Photo[]>([]);
  const { categoryAccessUrl } = useParams();

  const [editing, setEditing] = useState<boolean>(false);
  const [form, setForm] = useState<Form>({
    name: category?.name || '',
    description: category?.description || '',
    thumbnailId: undefined
  });

  const [changeThumbnail, setChangeThumbnail] = useState<boolean>(false);
  const [modifiedThumbnail, setModifiedThumbnail] = useState<Photo>();

  useEffect(() => {
    async function init() {
      const category = await getCategoryByAccessUrl(categoryAccessUrl?.toString());
      if (!category) return;

      setCategory(category);

      const photos = await getPhotosOfCategory(category.id);
      setPhotos(photos);
    }

    init();
  }, []);

  useEffect(() => {
    setForm({
      name: category?.name || '',
      description: category?.description || '',
      thumbnailId: undefined
      });
  }, [category]);

  async function submitForm(e: React.FormEvent) {
    e.preventDefault();

    console.log(form);
    
  }

  function setThumbnail(photo: Photo) {
    setForm((prevForm) => ({
      ...prevForm,
      thumbnailId: photo.id
    }));

    setModifiedThumbnail(photo);
    setChangeThumbnail(false);
  }

  return (
    <>
      <section className={css.info}>
        {editing ? (
          <form action="#" method="post" onSubmit={submitForm}>
            <div className={css.thumbnailContainer}>
              <img 
                src={modifiedThumbnail?.thumbnailUrl || category?.thumbnailUrl} 
                alt={category?.name} 
              />
              <button onClick={() => setChangeThumbnail(true)}>Change thumbnail</button>
            </div> 

            <div className={css.titleContainer}>
              <input 
                type="text" 
                value={form?.name}
                onChange={(e) =>
                  setForm((prevForm) => ({
                    ...prevForm,
                    name: e.target.value
                  }))
                }
              />
            </div>
            <div className={css.descriptionContainer}>
              <textarea 
                name="description" 
                id="description" 
                value={form.description}
                onChange={(e) =>
                  setForm((prevForm) => ({
                    ...prevForm,
                    description: e.target.value
                  }))
                }
              />
            </div>

            <div className={css.btnContainer}>
              <button type='button' onClick={() => setEditing(false)}>Discard changes</button>
              <button type='submit'>Save</button>
            </div>
          </form>
        ) : (
          <>
            <button className={css.editButton} onClick={() => setEditing(true)}>
              <FontAwesomeIcon icon={faEdit} />
              <span>Edit</span>
            </button>
            <div className={css.thumbnailContainer}>
              <img 
                src={modifiedThumbnail?.thumbnailUrl || category?.thumbnailUrl} 
                alt={category?.name} 
              />
            </div>
            <div className={css.titleContainer}>
              <h1>{category?.name}</h1>
            </div>
            <div className={css.descriptionContainer}>
              <p>{category?.description || 'No description available'}</p>
            </div>
          </>
        )}
      </section>

      {changeThumbnail && 
        <section className={css.thumbnailSelector}>
          <button onClick={() => setChangeThumbnail(false)}>
            <FontAwesomeIcon icon={faClose} />
          </button>
          <div>
            {photos.map(photo => 
              <div className={css.photoItem} onClick={() => setThumbnail(photo)} key={'thumbnail-' + photo.id} >
                <img src={photo.thumbnailUrl} alt={photo.categoryName} />
              </div>
            )}
          </div>
        </section>
      }
    </>
  );
}