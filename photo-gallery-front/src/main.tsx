import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import { createRoot } from 'react-dom/client'
import './index.css'
import HomeComponent from './pages/Home/Home.tsx'
import GalleryComponent from './pages/Gallery/Gallery.tsx'
import UploadComponent from './pages/Upload/Upload.tsx'

createRoot(document.getElementById('root')!).render(
  <Router>
    <Routes>
      <Route path='/' element={<HomeComponent />} />
      <Route path='/category/:categoryAccessUrl' element={<GalleryComponent />} />
      <Route path='/upload' element={<UploadComponent />} />
    </Routes>
  </Router>
)
