package projects.gallery.photo_gallery.service.interfaces;

import projects.gallery.photo_gallery.dto.request.MailDto;

public interface MailService {
    void sendMail(MailDto dto);
    void getConfirmationMail(MailDto dto);
}
