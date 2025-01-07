import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import { createRoot } from 'react-dom/client'
import './index.css'
import Home from './pages/Home/Home.tsx'

createRoot(document.getElementById('root')!).render(
  <Router>
    <Routes>
      <Route path='/' element={<Home />} />
    </Routes>
  </Router>
)
