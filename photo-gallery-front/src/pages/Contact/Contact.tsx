import { useEffect, useState } from 'react';
import css from './Contact.module.css';
import { getOwner } from '../../services/UserService';
import { User } from '../../models/User';

export default function ContactModule() {
  const [owner, setOwner] = useState<User | undefined>();

  useEffect(() => {
    async function init() {
      const response = await getOwner();
      if (response) setOwner(response);
    }

    init();
  }, []);

  return (
    <>
      
    </>
  );
}