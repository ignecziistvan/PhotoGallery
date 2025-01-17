import { useState } from 'react';
import css from './Login.module.css';
import handleErrors from './LoginService';
import { useNavigate } from 'react-router-dom';
import { login } from '../../../services/UserService';
import FormGroup from '../../../components/FormGroup/FormGroup';


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
    <>
      <form onSubmit={submitForm} className={css.loginForm}>
          <h1>Login</h1>
          <FormGroup 
            type='text'
            label='Username'
            id='username'
            placeholder='Enter your username'
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            error={errors.username}
          />
          <FormGroup 
            type='password'
            label='Password'
            id='password'
            placeholder='Enter your password'
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            error={errors.password}
          />
          <button type='submit'>Login</button>
        </form>
    </>
  );
}