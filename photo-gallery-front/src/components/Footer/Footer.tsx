import css from './Footer.module.css';
import { SocialIcon } from 'react-social-icons'

export default function Footer() {


  return (
    <footer className={css.footer}>
      <div className={css.footerContent}>
        <div className={css.footerLinks}>
          <a href="/about">About</a>
          <a href="/contact">Contact</a>
          <a href="/privacy">Privacy Policy</a>
        </div>
        <div className={css.right}>
          <div className={css.socialMedia}>
            <SocialIcon url="https://twitter.com" bgColor="white" fgColor="black" className={css.icon} style={{ width: "2.5em", height: "2.5em" }} />
            <SocialIcon url="https://instagram.com"  bgColor="white" fgColor="black" className={css.icon} style={{ width: "2.5em", height: "2.5em" }} />
            <SocialIcon url="https://facebook.com"  bgColor="white" fgColor="black" className={css.icon} style={{ width: "2.5em", height: "2.5em" }} />
          </div>
          <p>© 2025 PhotoGallery. All Rights Reserved.</p>
        </div>
      </div>
    </footer>
  );
}