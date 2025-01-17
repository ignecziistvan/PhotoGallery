import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import HomeComponent from './pages/Home/Home.tsx'
import GalleryComponent from './pages/Gallery/Gallery.tsx'
import LoginComponent from './pages/admin/Login/Login.tsx'
import DashboardComponent from './pages/admin/Dashboard/Dashboard.tsx'
import ContactComponent from './pages/Contact/Contact.tsx'
import CategoriesModule from './pages/admin/Categories/Categories.tsx'
import CategoryEditModule from './pages/admin/Category_Edit/CategoryEdit.tsx'
import UploadComponent from './pages/admin/Upload/Upload.tsx'
import CategoryCreateComponent from './pages/admin/Category_Create/CategoryCreate.tsx'

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<HomeComponent />} />
        <Route path='/category/:categoryAccessUrl' element={<GalleryComponent />} />
        <Route path='/contact' element={<ContactComponent />} />
        
        <Route path='/login' element={<LoginComponent />} />

        <Route path='/admin/dashboard' element={<DashboardComponent />} />
        <Route path='/admin/upload' element={<UploadComponent />} />
        <Route path='/admin/categories' element={<CategoriesModule />} />
        <Route path='/admin/categories/create' element={<CategoryCreateComponent />} />
        <Route path='/admin/categories/:categoryAccessUrl' element={<CategoryEditModule />} />

      </Routes>
    </Router>
  );
}