package projects.gallery.photo_gallery.service.interfaces;

import org.springframework.validation.annotation.Validated;
import projects.gallery.photo_gallery.dto.request.MailRequest;

public interface MailService {
    @Deprecated
    void sendMail(MailRequest dto);
    void getConfirmationMail(MailRequest dto);
}
