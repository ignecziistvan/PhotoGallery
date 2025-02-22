import { useEffect, useState } from 'react';
import { getCategoryByAccessUrl, patchCategory } from '../../../services/CategoryService';
import { getPhotosOfCategory } from '../../../services/PhotoService';
import css from './CategoryEdit.module.css';
import { useParams } from 'react-router-dom';
import { Photo } from '../../../models/Photo';
import { Category, PatchCategoryRequest } from '../../../models/Category';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faClose } from '@fortawesome/free-solid-svg-icons';
import { toast } from 'react-toastify';

export default function CategoryEditModule() {
  const [category, setCategory] = useState<Category>();
  const [photos, setPhotos] = useState<Photo[]>([]);
  const { categoryAccessUrl } = useParams();

  const [editing, setEditing] = useState<boolean>(false);
  const [form, setForm] = useState<PatchCategoryRequest>({
    name: category?.name || '',
    description: category?.description || '',
    thumbnailPhotoId: undefined
  });

  const [changeThumbnail, setChangeThumbnail] = useState<boolean>(false);
  const [modifiedThumbnail, setModifiedThumbnail] = useState<Photo>();

  const [errorMessage, setErrorMessage] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);

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
      thumbnailPhotoId: undefined
      });
  }, [category]);

  async function submitForm(e: React.FormEvent) {
    e.preventDefault();

    setErrorMessage('');
    setLoading(true);

    const response = await patchCategory(category?.id, form);
    if (response) {
      setErrorMessage(response);
    } else {
      setCategory(await getCategoryByAccessUrl(categoryAccessUrl));
      setEditing(false);
      toast.success('Category has been modified');
    }

    setLoading(false);
  }

  function setThumbnail(photo: Photo) {
    setForm((prevForm) => ({
      ...prevForm,
      thumbnailPhotoId: photo.id
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
              <button type="button" onClick={() => setChangeThumbnail(true)}>Change thumbnail</button>
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

            {errorMessage && <p className={css.error}>{errorMessage}</p>}

            <div className={css.btnContainer}>
              <button type='button' onClick={() => setEditing(false)}>Discard changes</button>
              {loading ? <button type='button' disabled>Saving...</button> : <button type='submit'>Save</button>}
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
          <button type='button' onClick={() => setChangeThumbnail(false)}>
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