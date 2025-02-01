package projects.gallery.photo_gallery.service.interfaces;

import projects.gallery.photo_gallery.dto.request.MailRequest;

public interface MailService {
    void sendMail(MailRequest dto);
    void getConfirmationMail(MailRequest dto);
}
