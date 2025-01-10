package projects.gallery.photo_gallery.factory;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import projects.gallery.photo_gallery.model.user.User;
import projects.gallery.photo_gallery.repository.user.UserRepository;

@Component
public class UserDataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (repository.findByUsername("TEST").isEmpty()) {
            repository.save(
                    new User(
                            "TEST",
                            "test@test.test",
                            "test-password",
                            "test",
                            "user"
                    )
            );
        }
    }
}
