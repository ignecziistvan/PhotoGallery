package projects.gallery.photo_gallery.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import projects.gallery.photo_gallery.model.user.Role;
import projects.gallery.photo_gallery.repository.user.RoleRepository;

@Component
public class RoleDataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.findByRole("ROLE_USER").isEmpty()) {
            repository.save(new Role("ROLE_USER"));
        }
    }
}
