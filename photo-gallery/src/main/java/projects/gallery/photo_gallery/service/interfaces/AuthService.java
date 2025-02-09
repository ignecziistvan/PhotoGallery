package projects.gallery.photo_gallery.service.interfaces;

import org.springframework.security.core.Authentication;
import projects.gallery.photo_gallery.dto.request.LoginRequest;
import projects.gallery.photo_gallery.model.user.User;

public interface AuthService {
    String login(LoginRequest dto);
    User authenticate(Authentication authentication);
}
