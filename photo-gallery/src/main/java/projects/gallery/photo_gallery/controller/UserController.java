package projects.gallery.photo_gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projects.gallery.photo_gallery.dto.request.LoginRequest;
import projects.gallery.photo_gallery.dto.response.UserResponse;
import projects.gallery.photo_gallery.service.interfaces.AuthService;
import projects.gallery.photo_gallery.service.interfaces.UserService;

@RestController
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public String login(@RequestBody LoginRequest dto) {
        return authService.login(dto);
    }

    @GetMapping("/api/authenticate")
    public UserResponse authenticate(Authentication authentication) {
        return new UserResponse(authService.authenticate(authentication));
    }

    @GetMapping("/api/owner")
    public UserResponse getOwner() {
        return userService.getOwner();
    }
}
