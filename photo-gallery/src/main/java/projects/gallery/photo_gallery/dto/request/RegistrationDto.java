package projects.gallery.photo_gallery.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationDto {
    private String username;
    private String email;
    private String password;
    private String passwordConfirm;
    private String firstname;
    private String lastname;
    private String description;
}
