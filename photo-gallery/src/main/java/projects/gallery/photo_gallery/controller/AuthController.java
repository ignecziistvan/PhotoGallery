package projects.gallery.photo_gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projects.gallery.photo_gallery.dto.request.LoginDto;
import projects.gallery.photo_gallery.dto.request.RegistrationDto;
import projects.gallery.photo_gallery.service.interfaces.AuthService;

@RestController
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/api/register")
    public String register (@RequestBody RegistrationDto dto) {
        return service.register(dto);
    }

    @PostMapping("/api/login")
    public String login(@RequestBody LoginDto dto) {
        return service.login(dto);
    }
}
