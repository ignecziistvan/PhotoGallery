package projects.gallery.photo_gallery.factory;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import projects.gallery.photo_gallery.model.user.Role;
import projects.gallery.photo_gallery.model.user.User;
import projects.gallery.photo_gallery.repository.user.RoleRepository;
import projects.gallery.photo_gallery.repository.user.UserRepository;

@Component
public class UserDataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Environment env;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserDataInitializer(UserRepository userRepository, RoleRepository roleRepository, Environment env) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.env = env;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername(env.getProperty("admin.username")).isPresent()) return;

        Role userRole = roleRepository.findByRole("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        Role adminRole = roleRepository.findByRole("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        String hashedPassword = encoder.encode(env.getProperty("admin.password"));
        User user = new User(
                env.getProperty("admin.username"),
                env.getProperty("admin.email"),
                hashedPassword,
                env.getProperty("admin.first-name"),
                env.getProperty("admin.last-name")
        );

        user.getRoles().add(userRole);
        user.getRoles().add(adminRole);

        user.setLinkedIn(env.getProperty("admin.linkedIn"));
        user.setGithub(env.getProperty("admin.github"));

        userRepository.save(user);
    }
}
