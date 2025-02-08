import { useState } from 'react';
import css from './Login.module.css';
import handleErrors from './LoginService';
import { useNavigate } from 'react-router-dom';
import { login } from '../../../services/UserService';

export default function LoginComponent() {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [errors, setErrors] = useState<{ [key: string]: string }>({});
  const navigate = useNavigate();

  async function submitForm(e: React.FormEvent) {
    e.preventDefault();
    setErrors({});

    try {
      await login(username, password);
      navigate('/admin/dashboard');
    } catch (e) {
      setErrors(handleErrors(e));
    }
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