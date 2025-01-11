package projects.gallery.photo_gallery.service.interfaces;

import projects.gallery.photo_gallery.dto.request.LoginDto;
import projects.gallery.photo_gallery.dto.request.RegistrationDto;

public interface AuthService {
    String login(LoginDto dto);
    String register(RegistrationDto dto);
}
