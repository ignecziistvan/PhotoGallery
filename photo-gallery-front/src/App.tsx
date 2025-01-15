import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import HomeComponent from './pages/Home/Home.tsx'
import GalleryComponent from './pages/Gallery/Gallery.tsx'
import LoginComponent from './pages/admin/Login/Login.tsx'
import { useEffect, useState } from 'react'
import DashboardComponent from './pages/admin/Dashboard/Dashboard.tsx'
import { authenticate } from './services/UserService.ts'
import Loading from './components/Loading/Loading.tsx'


export default function App() {
  const [authenticated, setAuthenticated] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    async function init() {
      try {
        setLoading(true);
        const response = await authenticate();
        setAuthenticated(response);
      } catch (error) {
        setAuthenticated(false);
      } finally {
        setLoading(false);
      }
    }
    init();
  }, []);

  if (loading) {
    return <Loading />
  } 
  
  return (
    <Router>
      <Routes>
        <Route path='/' element={<HomeComponent />} />
        <Route path='/category/:categoryAccessUrl' element={<GalleryComponent />} />
        
        <Route path='/login' element={<LoginComponent setAuthenticated={setAuthenticated} />} />

        <Route path='/dashboard' element={<DashboardComponent authenticated={authenticated} />} />
      </Routes>
    </Router>
  );
}