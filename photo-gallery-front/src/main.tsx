import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import { createRoot } from 'react-dom/client'
import './index.css'
import HomeComponent from './pages/Home/Home.tsx'
import GalleryComponent from './pages/Gallery/Gallery.tsx'

createRoot(document.getElementById('root')!).render(
  <Router>
    <Routes>
      <Route path='/' element={<HomeComponent />} />
      <Route path='/category/:categoryAccessUrl' element={<GalleryComponent />} />
    </Routes>
  </Router>
)
