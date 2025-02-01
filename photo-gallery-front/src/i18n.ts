import i18n from 'i18next';  
import { initReactI18next } from 'react-i18next';  
i18n  
 .use(initReactI18next)  
 .init({  
    resources: {  
      en: {  
        translation: {  
          navbar: {
            explore: "Explore",
            contact: "Contact"
          },
          footer: {
            contact: "Contact"
          },
          welcome: {  
            h1: "Photo Gallery Demo Project",  
            h3: "This is one of my projects I made for my portfolio. I used React.js and my Rackhost.hu web storage for the frontend part, and as backend, I used Spring Boot and AWS servers.",
            a_github_sc: "see the source code in the github repo",
          }, 
          imageSlider: {
            see_gallery: "See gallery",
            see_all_categories: "Explore all",
          }, 
          gallery: {
            photos: "photos"
          },
          contact: {
            h1: "Contact me",
            p1: "Feel free to reach out to me for any questions, collaborations, or just to talk.",
            p2: "I would be happy to connect with you!",
            github: "See my github portfolio",
            linkedin: "See my LinkedIn profile",
            msg_sent: "Message has been sent",
            form_h1: "Send Message",
            form_name_ph: "Your full name",
            form_email_ph: "your@email.address",
            form_message_ph: "Message",
            form_msg_char_counter: "characters",
            form_btn_send: "Send",
            form_btn_sending: "Sending..."
          }
        },  
      },  
      hu: {  
        translation: {  
          navbar: {
            explore: "Felfedezés",
            contact: "Kapcsolat"
          },
          footer: {
            contact: "Kapcsolat"
          },
          welcome: {  
            h1: "Photo Gallery Demo Projekt",  
            h3: "Ez egy a portfóliómhoz készített projektem. A frontend részhez React.js-t és a Rackhost webtárhelyem, míg a backendhez Spring Bootot és az AWS szervereit használom.",
            a_github_sc: "a forráskód itt elérhető",
          },  
          imageSlider: {
            see_gallery: "Ugrás a galériára",
            see_all_categories: "Összes kategória",
          },
          gallery: {
            photos: "fotói"
          },
          contact: {
            h1: "Kapcsolatfelvétel",
            p1: "Nyugodtan vedd fel velem a kapcsolatot ha bármi féle kérdésed van!",
            p2: "Szívesen válaszolok.",
            github: "Github portfólió",
            linkedin: "LinkedIn profil",
            msg_sent: "Üzenet elküldve",
            form_h1: "Üzenet küldése",
            form_name_ph: "Teljes név",
            form_email_ph: "az@email.cimed",
            form_message_ph: "Üzenet",
            form_msg_char_counter: "karakter",
            form_btn_send: "Küldés",
            form_btn_sending: "egy pillanat..."
          }
        },  
      },  
    },  
    lng: 'hu',  
    fallbackLng: 'en',  
    interpolation: {  
      escapeValue: false   
    },  
 });