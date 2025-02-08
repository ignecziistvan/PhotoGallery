import { useState } from 'react';
import css from './Login.module.css';
import handleErrors from './LoginService';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../../security/AuthContext';

export default function LoginComponent() {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [errors, setErrors] = useState<{ [key: string]: string }>({});
  const { login: loginInAuthContext } = useAuth();
  const navigate = useNavigate();

  async function submitForm(e: React.FormEvent) {
    e.preventDefault();
    setErrors({});

    const errors = await loginInAuthContext(username, password);
    if (errors) {
      setErrors(handleErrors(errors));
    } else navigate('/admin/dashboard');
  };

  return (
    <div className={css.page}>
      <form onSubmit={submitForm} className={css.loginForm}>
        <h1>Login</h1>
        <div className={css.formGroup}>
          <label htmlFor='username'>Username</label>
          <input 
            type='text' 
            id='username'
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder='Enter your username'
          />
          {errors.username && (
            <ul className={css.errors}>
              <li className={css.error}>{errors.username}</li>
            </ul>
          )}
        </div>
        <div className={css.formGroup}>
          <label htmlFor='password'>Password</label>
          <input 
            type='password' 
            id='password'
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder='Enter your password'
          />
          {errors.password && (
            <ul className={css.errors}>
              <li className={css.error}>{errors.password}</li>
            </ul>
          )}
        </div>
        <button type='submit'>Login</button>
      </form>
    </div>
  );
}