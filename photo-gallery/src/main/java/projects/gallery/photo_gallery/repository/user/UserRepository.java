package projects.gallery.photo_gallery.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.gallery.photo_gallery.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
