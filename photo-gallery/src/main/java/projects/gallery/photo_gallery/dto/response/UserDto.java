package projects.gallery.photo_gallery.dto.response;

import lombok.Getter;
import projects.gallery.photo_gallery.model.user.User;

@Getter
public class UserDto {
    private final String username;
    private final String email;
    private final String firstname;
    private final String lastName;
    private final String avatarUrl;
    private final String description;

    public UserDto(User u) {
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.firstname = u.getFirstName();
        this.lastName = u.getLastName();
        this.avatarUrl = u.getAvatar();
        this.description = u.getDescription();
    }
}
