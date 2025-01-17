package projects.gallery.photo_gallery.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.dto.request.LoginRequest;
import projects.gallery.photo_gallery.exception.UnauthorizedException;
import projects.gallery.photo_gallery.model.user.User;
import projects.gallery.photo_gallery.repository.user.UserRepository;
import projects.gallery.photo_gallery.service.interfaces.AuthService;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(LoginRequest dto) {
        try {
            userRepository.findByUsername(dto.getUsername()).orElseThrow(
                    () -> new UnauthorizedException(
                            "Authentication failed",
                            Map.of("username", "User does not exist")
                    )
            );
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );

            return jwtService.createToken(authentication.getName());
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(
                    "Authentication failed",
                    Map.of("password", "Invalid password")
            );
        }
    }

    @Override
    public User authenticate() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return userRepository.findByUsername(auth.getName()).orElseThrow(
                    () -> new UnauthorizedException("Unauthenticated")
            );
        } catch (Exception e) {
            throw new UnauthorizedException("You are unauthenticated");
        }
    }
}