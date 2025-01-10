package projects.gallery.photo_gallery.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.gallery.photo_gallery.model.user.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);
}
