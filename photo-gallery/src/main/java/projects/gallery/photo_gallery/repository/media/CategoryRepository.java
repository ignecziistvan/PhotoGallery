package projects.gallery.photo_gallery.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projects.gallery.photo_gallery.model.media.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
