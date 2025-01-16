package projects.gallery.photo_gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.gallery.photo_gallery.dto.request.MailRequest;
import projects.gallery.photo_gallery.service.interfaces.MailService;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public void sendMail(@RequestBody MailRequest dto) {
        mailService.sendMail(dto);
        mailService.getConfirmationMail(dto);
    }
}
