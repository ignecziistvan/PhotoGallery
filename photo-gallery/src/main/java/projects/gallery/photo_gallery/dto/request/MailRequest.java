package projects.gallery.photo_gallery.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MailRequest {
    @NotBlank(message = "{form-error.mail.name.not-blank}")
    @Size(
            max = 50,
            message = "{form-error.mail.name.size}"
    )
    private String name;

    @NotBlank(message = "{form-error.mail.email.not-blank}")
    @Email(message = "{form-error.mail.email.email}")
    private String email;

    @NotBlank(message = "{form-error.mail.message.not-blank}")
    @Size(
            min = 10,
            max = 1000,
            message = "{form-error.mail.message.size}"
    )
    private String message;


}
