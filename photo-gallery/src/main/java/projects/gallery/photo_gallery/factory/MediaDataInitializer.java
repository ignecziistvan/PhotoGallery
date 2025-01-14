package projects.gallery.photo_gallery.factory;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import projects.gallery.photo_gallery.model.media.Category;
import projects.gallery.photo_gallery.model.media.Photo;
import projects.gallery.photo_gallery.repository.media.CategoryRepository;

import java.util.List;

@Component
public class MediaDataInitializer implements CommandLineRunner {
    @Autowired
    private CategoryRepository repository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!repository.findAll().isEmpty()) return;

        Category weddingCat = new Category("Wedding");
        Category foodNDrinksCat = new Category("Food and Drinks");
        Category christmasCat = new Category("Christmas");
        Category fashionCat = new Category("Fashion");
        Category portraitCat = new Category("Portrait");
        Category petCat = new Category("Pet");

        Photo wedding1 = new Photo(
                "https://cdn0.hitched.co.uk/article/7500/3_2/960/jpg/150057-unique-wedding-ideas.jpeg",
                "https://cdn0.hitched.co.uk/article/7500/3_2/960/jpg/150057-unique-wedding-ideas.jpeg",
                null,
                weddingCat
        );
        Photo wedding2 = new Photo(
                "https://ichef.bbci.co.uk/ace/standard/2048/cpsprodpb/0b84/live/67b226a0-cab3-11ef-8fb8-75874e244aea.jpg",
                "https://ichef.bbci.co.uk/ace/standard/2048/cpsprodpb/0b84/live/67b226a0-cab3-11ef-8fb8-75874e244aea.jpg",
                null,
                weddingCat
        );
        Photo wedding3 = new Photo(
                "https://x.dkphoto.ie/wp-content/uploads/2024/03/Brides-Guide-to-Selecting-the-Perfect-Wedding-Dress.jpg.webp",
                "https://x.dkphoto.ie/wp-content/uploads/2024/03/Brides-Guide-to-Selecting-the-Perfect-Wedding-Dress.jpg.webp",
                null,
                weddingCat
        );
        Photo wedding4 = new Photo(
                "https://themagnoliavenue.com/wp-content/uploads/2021/11/3H9A9561_websize.jpg",
                "https://themagnoliavenue.com/wp-content/uploads/2021/11/3H9A9561_websize.jpg",
                null,
                weddingCat
        );

        weddingCat.setPhotos(List.of(
                wedding1, wedding2, wedding3, wedding4
        ));
        weddingCat.setDescription("Step into a world of love, joy, and celebration with our Wedding Photo Gallery. " +
                "Here, you can explore a collection of breathtaking moments captured on one of the most significant days of a couple’s life. " +
                "From the tender vows exchanged in front of family and friends to the spontaneous laughter shared during the reception, " +
                "each image tells a unique story of happiness and togetherness.");
        weddingCat.setThumbnailPhoto(wedding4);



        Photo foodNDrinksCat1 = new Photo(
                "https://www.beanilla.com/wp/wp-content/uploads/2022/06/RefreshingDrinks-1024x683.jpg",
                "https://www.beanilla.com/wp/wp-content/uploads/2022/06/RefreshingDrinks-1024x683.jpg",
                null,
                foodNDrinksCat
        );
        Photo foodNDrinksCat2 = new Photo(
                "https://www.acouplecooks.com/wp-content/uploads/2021/06/Strawberry-Water-006.jpg",
                "https://www.acouplecooks.com/wp-content/uploads/2021/06/Strawberry-Water-006.jpg",
                null,
                weddingCat
        );
        Photo foodNDrinksCat3 = new Photo(
                "https://www.tasteofhome.com/wp-content/uploads/2024/05/Sweet-Rum-Punch_EXPS_FT24_273648_EC_050224_4.jpg",
                "https://www.tasteofhome.com/wp-content/uploads/2024/05/Sweet-Rum-Punch_EXPS_FT24_273648_EC_050224_4.jpg",
                null,
                foodNDrinksCat
        );
        Photo foodNDrinksCat4 = new Photo(
                "https://media.cnn.com/api/v1/images/stellar/prod/gettyimages-802667754.jpg?c=original",
                "https://media.cnn.com/api/v1/images/stellar/prod/gettyimages-802667754.jpg?c=original",
                null,
                foodNDrinksCat
        );
        Photo foodNDrinksCat5 = new Photo(
                "https://images.squarespace-cdn.com/content/v1/568bea5540667a54498bf784/1698418621607-91888NLWM0GSP5RQU410/Lux-still-life-food-drink-photographyOCT-TEASER.jpg",
                "https://images.squarespace-cdn.com/content/v1/568bea5540667a54498bf784/1698418621607-91888NLWM0GSP5RQU410/Lux-still-life-food-drink-photographyOCT-TEASER.jpg",
                null,
                foodNDrinksCat
        );
        Photo foodNDrinksCat6 = new Photo(
                "https://images.squarespace-cdn.com/content/v1/568bea5540667a54498bf784/1683041869719-VPO00QXJCK9VZYBLYQCZ/OCTOBER-THEME.jpg",
                "https://images.squarespace-cdn.com/content/v1/568bea5540667a54498bf784/1683041869719-VPO00QXJCK9VZYBLYQCZ/OCTOBER-THEME.jpg",
                null,
                foodNDrinksCat
        );

        foodNDrinksCat.setPhotos(List.of(
                foodNDrinksCat1, foodNDrinksCat2, foodNDrinksCat3, foodNDrinksCat4, foodNDrinksCat5, foodNDrinksCat6
        ));
        foodNDrinksCat.setDescription("Indulge your senses with our Food & Drinks Gallery, " +
                "where the art of cuisine and the pleasure of fine beverages come to life. " +
                "This collection showcases a variety of mouthwatering dishes and expertly crafted drinks, " +
                "each one designed to delight your taste buds and satisfy your cravings.");
        foodNDrinksCat.setThumbnailPhoto(foodNDrinksCat6);


        Photo chrsitmas1 = new Photo(
                "https://cdn.britannica.com/62/147462-050-3C0642C4/front-yard-Christmas.jpg",
                "https://cdn.britannica.com/62/147462-050-3C0642C4/front-yard-Christmas.jpg",
                null,
                christmasCat
        );

        Photo chrsitmas2 = new Photo(
                "https://cdn.britannica.com/38/196638-050-94E05EF4/Santa-Claus.jpg",
                "https://cdn.britannica.com/38/196638-050-94E05EF4/Santa-Claus.jpg",
                null,
                christmasCat
        );

        Photo chrsitmas3 = new Photo(
                "https://www.teachingcatholickids.com/wp-content/uploads/2015/12/baby-4258530_1920.jpg",
                "https://www.teachingcatholickids.com/wp-content/uploads/2015/12/baby-4258530_1920.jpg",
                null,
                christmasCat
        );

        Photo chrsitmas4 = new Photo(
                "https://www.myjewishlearning.com/wp-content/uploads/2005/12/pareve-christmas.jpg",
                "https://www.myjewishlearning.com/wp-content/uploads/2005/12/pareve-christmas.jpg",
                null,
                christmasCat
        );

        Photo chrsitmas5 = new Photo(
                "https://www.wowphotostudios.com/wp-content/uploads/2020/11/CB5I1601-1024x683.jpg",
                "https://www.wowphotostudios.com/wp-content/uploads/2020/11/CB5I1601-1024x683.jpg",
                null,
                christmasCat
        );

        christmasCat.setPhotos(List.of(
                chrsitmas1, chrsitmas2, chrsitmas3, chrsitmas4, chrsitmas5
        ));
        christmasCat.setDescription("Step into the festive spirit with our Christmas Photo Gallery, " +
                "a heartwarming collection of holiday moments captured in stunning detail. " +
                "From twinkling lights and beautifully decorated trees to cozy family gatherings and joyful celebrations, " +
                "this gallery encapsulates the magic of the Christmas season.");
        christmasCat.setThumbnailPhoto(chrsitmas5);


        Photo fashion1 = new Photo(
                "https://audaces.com/wp-content/uploads/2020/08/fashion-styles.webp",
                "https://audaces.com/wp-content/uploads/2020/08/fashion-styles.webp",
                null,
                fashionCat
        );
        Photo fashion2 = new Photo(
                "https://media.glamour.com/photos/66f5c2777e09bc43bcee2067/16:9/w_1280,c_limit/men%E2%80%99s%20fashion%20trends.jpg",
                "https://media.glamour.com/photos/66f5c2777e09bc43bcee2067/16:9/w_1280,c_limit/men%E2%80%99s%20fashion%20trends.jpg",
                null,
                fashionCat
        );
        Photo fashion3 = new Photo(
                "https://assets.vogue.com/photos/60e70bcccd2d1c05db4c9f58/4:3/w_1600,c_limit/00_promo_4x3.jpg",
                "https://assets.vogue.com/photos/60e70bcccd2d1c05db4c9f58/4:3/w_1600,c_limit/00_promo_4x3.jpg",
                null,
                fashionCat
        );
        fashionCat.setPhotos(List.of(
                fashion1, fashion2, fashion3
        ));
        fashionCat.setThumbnailPhoto(fashion1);

        Photo portrait1 = new Photo(
                "https://images.unsplash.com/photo-1544005313-94ddf0286df2?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8cG9ydHJhaXR8ZW58MHx8MHx8fDA%3D",
                "https://images.unsplash.com/photo-1544005313-94ddf0286df2?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8cG9ydHJhaXR8ZW58MHx8MHx8fDA%3D",
                null,
                portraitCat
        );
        Photo portrait2 = new Photo(
                "https://res.cloudinary.com/dqzkirtbz/image/upload/w_auto/q_auto,f_auto,dpr_auto/v1698342290/Website/blog/female_portrait_postcrest.jpg",
                "https://res.cloudinary.com/dqzkirtbz/image/upload/w_auto/q_auto,f_auto,dpr_auto/v1698342290/Website/blog/female_portrait_postcrest.jpg",
                null,
                portraitCat
        );
        Photo portrait3 = new Photo(
                "https://storage.googleapis.com/pod_public/750/173045.jpg",
                "https://storage.googleapis.com/pod_public/750/173045.jpg",
                null,
                portraitCat
        );
        portraitCat.setPhotos(List.of(
                portrait1, portrait2, portrait3
        ));
        portraitCat.setThumbnailPhoto(portrait1);


        Photo pet1 = new Photo(
                "https://images.theconversation.com/files/625049/original/file-20241010-15-95v3ha.jpg?ixlib=rb-4.1.0&rect=4%2C12%2C2679%2C1521&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
                "https://images.theconversation.com/files/625049/original/file-20241010-15-95v3ha.jpg?ixlib=rb-4.1.0&rect=4%2C12%2C2679%2C1521&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
                null,
                petCat
        );
        Photo pet2 = new Photo(
                "https://www.lanecove.nsw.gov.au/files/sharedassets/public/v/1/pets.jpg?w=1080",
                "https://www.lanecove.nsw.gov.au/files/sharedassets/public/v/1/pets.jpg?w=1080",
                null,
                petCat
        );
        Photo pet3 = new Photo(
                "https://www.atozvet.com/wp-content/uploads/2017/07/Prevention-and-Treatment-For-Pet-Disease-Midland-TX-scaled.jpg",
                "https://www.atozvet.com/wp-content/uploads/2017/07/Prevention-and-Treatment-For-Pet-Disease-Midland-TX-scaled.jpg",
                null,
                petCat
        );
        Photo pet4 = new Photo(
                "https://www.onehealth.org/hubfs/blog/Tips-for-Choosing-the-Right-Pet-for-Your-Home-and-Lifestyle.jpeg",
                "https://www.onehealth.org/hubfs/blog/Tips-for-Choosing-the-Right-Pet-for-Your-Home-and-Lifestyle.jpeg",
                null,
                petCat
        );
        petCat.setPhotos(List.of(
                pet1, pet2, pet3, pet4
        ));
        petCat.setThumbnailPhoto(pet1);


        repository.save(weddingCat);
        repository.save(foodNDrinksCat);
        repository.save(christmasCat);
        repository.save(portraitCat);
        repository.save(petCat);
        repository.save(fashionCat);
    }
}
