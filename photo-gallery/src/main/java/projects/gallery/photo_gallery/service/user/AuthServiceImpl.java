package projects.gallery.photo_gallery.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final MessageSource messageSource;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.messageSource = messageSource;
    }

    @Override
    public String login(LoginRequest dto) {
        String authFailedText = messageSource.getMessage(
                "authentication-failed",
                null,
                "Authentication failed",
                LocaleContextHolder.getLocale()
        );
        String usernameText = messageSource.getMessage(
                "username-not-exists",
                null,
                "Username does not exists",
                LocaleContextHolder.getLocale()
        );
        String passwordText = messageSource.getMessage(
                "invalid-password",
                null,
                "Invalid password",
                LocaleContextHolder.getLocale()
        );

        try {
            userRepository.findByUsername(dto.getUsername()).orElseThrow(
                    () -> new UnauthorizedException(
                            authFailedText,
                            Map.of("username", usernameText)
                    )
            );
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );

            return jwtService.createToken(authentication);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(
                    authFailedText,
                    Map.of("password", passwordText)
            );
        }
    }

    @Override
    public User authenticate(Authentication authentication) {
        try {
            return userRepository.findByUsername(authentication.getName()).orElseThrow(
                    () -> new UnauthorizedException("Unauthenticated")
            );
        } catch (Exception e) {
            throw new UnauthorizedException(messageSource.getMessage(
                    "authentication-failed",
                    null,
                    "Authentication failed",
                    LocaleContextHolder.getLocale()
            ));
        }
    }
}