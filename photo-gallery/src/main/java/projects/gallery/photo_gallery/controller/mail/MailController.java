package projects.gallery.photo_gallery.controller.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.gallery.photo_gallery.dto.request.MailRequest;
import projects.gallery.photo_gallery.service.interfaces.MailService;

@Deprecated
@RestController
@RequestMapping("/api/mail")
public class MailController {
    private final MailService mailService;

    @Autowired
    public MailController(@Qualifier("mailServiceV1") MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public void sendMail(@RequestBody MailRequest dto) {
        mailService.sendMail(dto);
        mailService.getConfirmationMail(dto);
    }
}
