import { useEffect, useState } from 'react';
import css from './Footer.module.css';
import { SocialIcon } from 'react-social-icons'
import { User } from '../../models/User';
import { getOwner } from '../../services/UserService';

export default function Footer() {
  const [owner, setOwner] = useState<User|undefined>();

  useEffect(() => {
    async function init() {
      setOwner(await getOwner());
    }

    init();
  }, []);

  return (
    <footer className={css.footer}>
      <p>© 2025 Ignéczi István.</p>
      <div className={css.socialMedia}>
        <SocialIcon url="https://github.com/ignecziistvan" bgColor="white" fgColor="black" className={css.icon} style={{ width: "2.5em", height: "2.5em" }} />
        <SocialIcon url={owner?.linkedIn} bgColor="white" fgColor="black" className={css.icon} style={{ width: "2.5em", height: "2.5em" }} />
      </div>
    </footer>
  );
}