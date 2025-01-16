package projects.gallery.photo_gallery.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.dto.response.UserResponse;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.model.user.User;
import projects.gallery.photo_gallery.repository.user.UserRepository;
import projects.gallery.photo_gallery.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final Environment env;

    @Autowired
    public UserServiceImpl(UserRepository repository, Environment env) {
        this.repository = repository;
        this.env = env;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }


    @Override
    public UserResponse getOwner() {
        User user = repository.findByUsername(env.getProperty("admin.username")).orElseThrow(
                () -> new NotFoundException("Owner was not found")
        );

        return new UserResponse(user);
    }
}
