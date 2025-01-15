import { useEffect, useState } from 'react';
import css from './Contact.module.css';
import { getOwner } from '../../services/UserService';
import { User } from '../../models/User';

import background from '../../assets/contact_bg.jpg';

import { SocialIcon } from 'react-social-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMailBulk } from '@fortawesome/free-solid-svg-icons';
import FormGroup from '../../components/FormGroup/FormGroup';



interface EmailForm {
  name: string;
  email: string;
  message: string;
}

export default function ContactComponent() {
  const [owner, setOwner] = useState<User | undefined>();
  const [form, setForm] = useState<EmailForm>({
    name: '',
    email: '',
    message: ''
  });
  const [error, setError] = useState<EmailForm>({
    name: '',
    email: '',
    message: ''
  });

  useEffect(() => {
    async function init() {
      const response = await getOwner();
      if (response) setOwner(response);
    }

    init();
  }, []);

  function submitForm(e: React.FormEvent) {
    e.preventDefault();
    setError({
      name: '',
      email: '',
      message: ''
    });

    
  }

  return (
    <div className={css.page}>
      <div 
        className={css.background}
        style={{
          backgroundImage: "url(" + background + ")"
        }}
      />
      <div className={css.intro}>
        <h1>Contact me</h1>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. 
          Officiis vero sequi, cupiditate fugit ipsum facere vel ex, quas beatae pariatur optio sunt. 
          Quibusdam nihil totam facilis excepturi molestiae aliquam dolore?
        </p>
      </div>

      <div className={css.flex}>
        <div className={css.contents}>
          <div className={css.info}>
            <div className={css.svgContainer}>
              <FontAwesomeIcon icon={faMailBulk} />
            </div>
            <div className={css.flexCol}>
              <h3>Email</h3>
              <p>{owner?.email}</p>
            </div>
          </div>
          <div className={css.info}>
            <SocialIcon url={owner?.github} bgColor="white" fgColor="black" className={css.icon} style={{ width: "2.5em", height: "2.5em" }} />
            <div className={css.flexCol}>
              <h3>Github</h3>
              <p>See my github portfolio</p>
            </div>
          </div>
          <div className={css.info}>
            <SocialIcon url={owner?.linkedIn} bgColor="white" fgColor="black" className={css.icon} style={{ width: "2.5em", height: "2.5em" }} />
            <div className={css.flexCol}>
              <h3>LinkedIn</h3>
              <p>See my LinkedIn profile</p>
            </div>
          </div>
        </div>
        <form action="#" method="post" onSubmit={submitForm}>
          <h1>Send Message</h1>
          <FormGroup 
            label=''
            id='name'  
            type='text'
            placeholder='Your full name'
            value={form.name}
            onChange={(e) =>
              setForm((prevForm) => ({
                ...prevForm,
                name: e.target.value
              }))
            }
            error={error.name}
          />
          <FormGroup 
            label=''
            id='email'  
            type='email'
            placeholder='your@email.address'
            value={form.email}
            onChange={(e) =>
              setForm((prevForm) => ({
                ...prevForm,
                email: e.target.value
              }))
            }
            error={error.email}
          />
          <div className={css.formGroup}>
            <p>{form.message.length} / 1000 characters</p>
            <textarea 
              value={form.message}
              onChange={(e) =>
                setForm((prevForm) => ({
                  ...prevForm,
                  message: e.target.value
                }))
              }
              placeholder='Message'
            />
            {error.message && (
              <ul className={css.errors}>
                <li className={css.error}>{error.message}</li>
              </ul>
            )}
          </div>

          <button type='submit'>Send</button>
        </form>
      </div>
    </div>
  );
}