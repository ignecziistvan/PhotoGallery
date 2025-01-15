package projects.gallery.photo_gallery.dto.response;

import lombok.Getter;
import projects.gallery.photo_gallery.model.user.User;

@Getter
public class UserDto {
    private final String email;
    private final String name;
    private final String avatarUrl;
    private final String description;
    private final String linkedIn;
    private final String github;

    public UserDto(User u) {
        this.email = u.getEmail();
        this.name = u.getLastName() + " " + u.getFirstName();
        this.avatarUrl = u.getAvatar();
        this.description = u.getDescription();
        this.linkedIn = u.getLinkedIn();
        this.github = u.getGithub();
    }
}
