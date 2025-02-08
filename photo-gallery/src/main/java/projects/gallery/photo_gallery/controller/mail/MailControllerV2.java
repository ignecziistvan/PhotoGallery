package projects.gallery.photo_gallery.controller.mail;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.gallery.photo_gallery.dto.request.MailRequest;
import projects.gallery.photo_gallery.service.interfaces.MailService;

@RestController
@RequestMapping("/api/mail/v2")
public class MailControllerV2 {
    private final MailService mailService;

    @Autowired
    public MailControllerV2(@Qualifier("mailServiceV2") MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public void sendMail(@Valid @RequestBody MailRequest dto) {
        mailService.sendMail(dto);
        mailService.getConfirmationMail(dto);
    }
}
