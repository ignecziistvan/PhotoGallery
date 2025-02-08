import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import GalleryComponent from './pages/Gallery/Gallery.tsx'
import LoginComponent from './pages/admin/Login/Login.tsx'
import DashboardComponent from './pages/admin/Dashboard/Dashboard.tsx'
import ContactComponent from './pages/Contact/Contact.tsx'
import CategoriesModule from './pages/admin/Categories/Categories.tsx'
import CategoryEditModule from './pages/admin/Category_Edit/CategoryEdit.tsx'
import UploadComponent from './pages/admin/Upload/Upload.tsx'
import CategoryCreateComponent from './pages/admin/Category_Create/CategoryCreate.tsx'
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import CategoriesPage from './pages/Categories/Categories.tsx'
import HomePage from './pages/Home/Home.tsx'
import Navbar from './components/Navbar/Navbar.tsx'
import NotFoundPage from './pages/404/NotFoundPage.tsx'

export default function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path='/' element={<HomePage />} />
        <Route path='/categories' element={<CategoriesPage />} />
        <Route path='/categories/:categoryAccessUrl' element={<GalleryComponent />} />
        <Route path='/contact' element={<ContactComponent />} />
        
        <Route path='/login' element={<LoginComponent />} />

        <Route path='/admin/dashboard' element={<DashboardComponent />} />
        <Route path='/admin/upload' element={<UploadComponent />} />
        <Route path='/admin/categories' element={<CategoriesModule />} />
        <Route path='/admin/categories/create' element={<CategoryCreateComponent />} />
        <Route path='/admin/categories/:categoryAccessUrl' element={<CategoryEditModule />} />

        <Route path='*' element={<NotFoundPage />} />
      </Routes>

      <ToastContainer position="bottom-right" autoClose={3000} />
    </Router>
  );
}