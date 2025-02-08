import { useEffect, useState } from "react";
import { useAuth } from "../../../security/AuthContext";
import { useNavigate } from "react-router-dom";


export default function Logout() {
  const [loading, setLoading] = useState<boolean>(true);
  const { logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    logout
    setLoading(false);

    if (!loading) navigate('/');
  }, [loading]);

  return (
    <>
      <h1>Logging out...</h1>
    </>
  );
}