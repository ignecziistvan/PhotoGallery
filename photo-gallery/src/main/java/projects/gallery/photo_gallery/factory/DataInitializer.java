package projects.gallery.photo_gallery.factory;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import projects.gallery.photo_gallery.model.Category;
import projects.gallery.photo_gallery.model.Photo;
import projects.gallery.photo_gallery.repository.CategoryRepository;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private CategoryRepository repository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!repository.findAll().isEmpty()) return;

        Category weddingCat = new Category("Wedding");
        Category foodNDrinksCat = new Category("Food and Drinks");
        Category christmasCat = new Category("Christmas");

        Photo wedding1 = new Photo(
                "https://cdn0.hitched.co.uk/article/7500/3_2/960/jpg/150057-unique-wedding-ideas.jpeg",
                "https://cdn0.hitched.co.uk/article/7500/3_2/960/jpg/150057-unique-wedding-ideas.jpeg",
                weddingCat
        );
        Photo wedding2 = new Photo(
                "https://ichef.bbci.co.uk/ace/standard/2048/cpsprodpb/0b84/live/67b226a0-cab3-11ef-8fb8-75874e244aea.jpg",
                "https://ichef.bbci.co.uk/ace/standard/2048/cpsprodpb/0b84/live/67b226a0-cab3-11ef-8fb8-75874e244aea.jpg",
                weddingCat
        );
        Photo wedding3 = new Photo(
                "https://x.dkphoto.ie/wp-content/uploads/2024/03/Brides-Guide-to-Selecting-the-Perfect-Wedding-Dress.jpg.webp",
                "https://x.dkphoto.ie/wp-content/uploads/2024/03/Brides-Guide-to-Selecting-the-Perfect-Wedding-Dress.jpg.webp",
                weddingCat
        );

        weddingCat.setPhotos(List.of(
                wedding1, wedding2, wedding3
        ));



        Photo foodNDrinksCat1 = new Photo(
                "https://www.beanilla.com/wp/wp-content/uploads/2022/06/RefreshingDrinks-1024x683.jpg",
                "https://www.beanilla.com/wp/wp-content/uploads/2022/06/RefreshingDrinks-1024x683.jpg",
                foodNDrinksCat
        );
        Photo foodNDrinksCat2 = new Photo(
                "https://www.acouplecooks.com/wp-content/uploads/2021/06/Strawberry-Water-006.jpg",
                "https://www.acouplecooks.com/wp-content/uploads/2021/06/Strawberry-Water-006.jpg",
                weddingCat
        );
        Photo foodNDrinksCat3 = new Photo(
                "https://www.tasteofhome.com/wp-content/uploads/2024/05/Sweet-Rum-Punch_EXPS_FT24_273648_EC_050224_4.jpg",
                "https://www.tasteofhome.com/wp-content/uploads/2024/05/Sweet-Rum-Punch_EXPS_FT24_273648_EC_050224_4.jpg",
                foodNDrinksCat
        );
        Photo foodNDrinksCat4 = new Photo(
                "https://media.cnn.com/api/v1/images/stellar/prod/gettyimages-802667754.jpg?c=original",
                "https://media.cnn.com/api/v1/images/stellar/prod/gettyimages-802667754.jpg?c=original",
                foodNDrinksCat
        );

        foodNDrinksCat.setPhotos(List.of(
                foodNDrinksCat1, foodNDrinksCat2, foodNDrinksCat3, foodNDrinksCat4
        ));



        Photo chrsitmas1 = new Photo(
                "https://cdn.britannica.com/62/147462-050-3C0642C4/front-yard-Christmas.jpg",
                "https://cdn.britannica.com/62/147462-050-3C0642C4/front-yard-Christmas.jpg",
                christmasCat
        );

        Photo chrsitmas2 = new Photo(
                "https://cdn.britannica.com/38/196638-050-94E05EF4/Santa-Claus.jpg",
                "https://cdn.britannica.com/38/196638-050-94E05EF4/Santa-Claus.jpg",
                christmasCat
        );

        Photo chrsitmas3 = new Photo(
                "https://www.teachingcatholickids.com/wp-content/uploads/2015/12/baby-4258530_1920.jpg",
                "https://www.teachingcatholickids.com/wp-content/uploads/2015/12/baby-4258530_1920.jpg",
                christmasCat
        );

        Photo chrsitmas4 = new Photo(
                "https://www.myjewishlearning.com/wp-content/uploads/2005/12/pareve-christmas.jpg",
                "https://www.myjewishlearning.com/wp-content/uploads/2005/12/pareve-christmas.jpg",
                christmasCat
        );

        christmasCat.setPhotos(List.of(
                chrsitmas1, chrsitmas2, chrsitmas3, chrsitmas4
        ));


        repository.save(weddingCat);
        repository.save(foodNDrinksCat);
        repository.save(christmasCat);
    }
}
