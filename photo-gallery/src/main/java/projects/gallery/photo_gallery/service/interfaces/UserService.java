package projects.gallery.photo_gallery.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import projects.gallery.photo_gallery.dto.response.UserResponse;

public interface UserService extends UserDetailsService {
    UserResponse getOwner();
}
