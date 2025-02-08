import { createRoot } from 'react-dom/client'
import './index.css'
import '../node_modules/tailwindcss/index.css';
import App from './App.tsx'
import './i18n.ts';

createRoot(document.getElementById('root')!).render(
  <App />
)
