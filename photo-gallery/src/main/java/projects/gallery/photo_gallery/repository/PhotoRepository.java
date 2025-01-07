package projects.gallery.photo_gallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projects.gallery.photo_gallery.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
