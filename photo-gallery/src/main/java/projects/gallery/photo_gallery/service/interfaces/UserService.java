package projects.gallery.photo_gallery.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import projects.gallery.photo_gallery.dto.response.UserDto;

public interface UserService extends UserDetailsService {
    UserDto getOwner();
}
