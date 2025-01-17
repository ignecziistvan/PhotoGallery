import { useState } from 'react';
import css from './CategoryCreate.module.css';
import { Link } from 'react-router-dom';


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

  async function submitForm(e: React.FormEvent) {
    e.preventDefault();

    console.log(form);
    
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

        <div className={css.btnContainer}>
          <Link to='/admin/categories'>Back</Link>
          <button type='submit'>Save</button>
        </div>
      </form>
    </>
  );
}