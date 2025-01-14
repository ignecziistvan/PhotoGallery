import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import HomeComponent from './pages/Home/Home.tsx'
import GalleryComponent from './pages/Gallery/Gallery.tsx'
import UploadComponent from './pages/Upload/Upload.tsx'
import Navbar from './components/Navbar/Navbar.tsx'
import LoginComponent from './pages/Login/Login.tsx'
import { useEffect, useState } from 'react'
import httpRequest from './interceptors/HttpRequest.ts'


export default function App() {
  const [authenticated, setAuthenticated] = useState<boolean>(false);

  useEffect(() => {
    async function authenticate() {
      try {
        const response = await httpRequest.get('/authenticate');
        if (response.status === 200) setAuthenticated(true);
      } catch (error) {
        setAuthenticated(false);
      }
    }
    authenticate();
  }, []);

  return (
    <Router>
      {authenticated && <Navbar />}
      <Routes>
        <Route path='/' element={<HomeComponent />} />
        <Route path='/category/:categoryAccessUrl' element={<GalleryComponent />} />
        <Route path='/upload' element={<UploadComponent />} />
        <Route path='/login' element={<LoginComponent setAuthenticated={setAuthenticated} />} />
      </Routes>
    </Router>
  );
}