package projects.gallery.photo_gallery.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.exception.NotFoundException;
import projects.gallery.photo_gallery.repository.user.UserRepository;
import projects.gallery.photo_gallery.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("User was not found")
        );
    }
}
