import { useState } from 'react';
import css from './CategoryCreate.module.css';
import { Link } from 'react-router-dom';
import { createCategory } from '../../../services/CategoryService';


interface Form {
  name: string;
  description: string;
  thumbnailId?: number;
}

export default function CategoryCreateComponent() {
  const [form, setForm] = useState<Form>({
    name: '',
    description: ''
  });
  const [errorMessage, setErrorMessage] = useState<string>('');
  const [success, setSuccess] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);

  async function submitForm(e: React.FormEvent) {
    e.preventDefault();

    setErrorMessage('');
    setSuccess(false);
    setLoading(true);
    
    const response = await createCategory(form);
    if (response) {
      setErrorMessage(response);
      setSuccess(false);
    } else {
      setSuccess(true);
      setForm({
        name: '',
        description: ''
      });
    }

    setLoading(false);
  }

  return (
    <>
      <form action="#" method="post" onSubmit={submitForm}>
        <h2>Create a new category</h2>
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
            placeholder='Name of the category'
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
            placeholder='Write a description to the category'
          />
        </div>

        {errorMessage && <p className={css.error}>{errorMessage}</p>}
        {success && <p className={css.success}>Category has been created</p>}

        <div className={css.btnContainer}>
          <Link to='/admin/categories'>Back</Link>
          {loading ? (
            <button type='button' disabled>Saving...</button>
          ): (
            <button type='submit'>Save</button>
          )}
        </div>
      </form>
    </>
  );
}