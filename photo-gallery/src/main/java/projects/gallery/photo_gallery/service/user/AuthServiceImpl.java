package projects.gallery.photo_gallery.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.dto.request.LoginDto;
import projects.gallery.photo_gallery.dto.request.RegistrationDto;
import projects.gallery.photo_gallery.exception.BadRequestException;
import projects.gallery.photo_gallery.exception.UnauthorizedException;
import projects.gallery.photo_gallery.model.user.Role;
import projects.gallery.photo_gallery.model.user.User;
import projects.gallery.photo_gallery.repository.user.RoleRepository;
import projects.gallery.photo_gallery.repository.user.UserRepository;
import projects.gallery.photo_gallery.service.interfaces.AuthService;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = new BCryptPasswordEncoder();
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(LoginDto dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );
            return jwtService.createToken(dto.getUsername());
        } catch (BadCredentialsException e) {
            User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(
                    () -> new UnauthorizedException(
                            "Authentication failed",
                            Map.of("username", "User does not exist")
                    )
            );
            if (!encoder.matches(dto.getPassword(), user.getPassword())) {
                throw new UnauthorizedException(
                        "Authentication failed",
                        Map.of("password", "Invalid password")
                );
            }

            throw new UnauthorizedException("Authentication failed");
        }
    }

    @Override
    public String register(RegistrationDto dto) {
        Map<String, String> errors = validateDto(dto);
        if (!errors.isEmpty()) {
            throw new BadRequestException("Registration failed", errors);
        }

        String password = encoder.encode(dto.getPassword());

        User user = new User(
                dto.getUsername(),
                dto.getEmail(),
                password,
                dto.getFirstname(),
                dto.getLastname()
        );

        user.getRoles().add(getUserRole());
        userRepository.save(user);

        return login(new LoginDto(dto.getUsername(), dto.getEmail(), dto.getPassword()));
    }

    private Role getUserRole() {
        return roleRepository.findByRole("ROLE_USER").orElse(new Role("ROLE_USER"));
    }

    private Map<String, String> validateDto(RegistrationDto dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto.getFirstname().length() < 2) {
            errors.put("firstName", "First name is too short");
        }
        if (dto.getLastname().length() < 2) {
            errors.put("lastName", "Last name is too short");
        }
        String emailRegex = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!dto.getEmail().matches(emailRegex)) {
            errors.put("email", "Invalid email format");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            errors.put("email", "This e-mail is already in use");
        }
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            errors.put("username", "This username is already in use");
        }
        String passwordRegex = "^(?=.*\\d).{6,}$";
        if (!dto.getPassword().matches(passwordRegex)) {
            errors.put("password", "Password must be at least 6 characters long and contain at least one digit");
        }
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            errors.put("passwordConfirm", "Passwords don't match");
        }

        return errors;
    }
}
