package projects.gallery.photo_gallery.service.interfaces;

import projects.gallery.photo_gallery.dto.request.LoginDto;
import projects.gallery.photo_gallery.model.user.User;

public interface AuthService {
    String login(LoginDto dto);
    User authenticate();
}
