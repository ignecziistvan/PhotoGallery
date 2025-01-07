package projects.gallery.photo_gallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projects.gallery.photo_gallery.model.Category;

@Repository
public interface MediaRepository extends JpaRepository<Category, Long> {
}
