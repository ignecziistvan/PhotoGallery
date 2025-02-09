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
import { AuthProvider } from './security/AuthContext.tsx'
import PrivateRoute from './pages/admin/PrivateRotue.tsx'

export default function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />

        <Routes>
          <Route path='/' element={<HomePage />} />
          <Route path='/categories' element={<CategoriesPage />} />
          <Route path='/categories/:categoryAccessUrl' element={<GalleryComponent />} />
          <Route path='/contact' element={<ContactComponent />} />
          
          <Route path='/login' element={<LoginComponent />} />

          <Route path='/admin/*' element={<PrivateRoute />}>
            <Route path='dashboard' element={<DashboardComponent />} />
            <Route path='upload' element={<UploadComponent />} />
            <Route path='categories' element={<CategoriesModule />} />
            <Route path='categories/create' element={<CategoryCreateComponent />} />
            <Route path='categories/:categoryAccessUrl' element={<CategoryEditModule />} />
          </Route>

          <Route path='*' element={<NotFoundPage />} />
        </Routes>

        <ToastContainer position="bottom-right" autoClose={3000} />
      </Router>
    </AuthProvider>
  );
}