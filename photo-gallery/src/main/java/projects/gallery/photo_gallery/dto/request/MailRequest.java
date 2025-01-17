package projects.gallery.photo_gallery.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MailRequest {
    private String name;
    private String email;
    private String message;
}
