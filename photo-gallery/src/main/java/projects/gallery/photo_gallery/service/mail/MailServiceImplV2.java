package projects.gallery.photo_gallery.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.dto.request.MailRequest;
import projects.gallery.photo_gallery.exception.GeneralException;
import projects.gallery.photo_gallery.service.interfaces.MailService;

@Service("mailServiceV2")
public class MailServiceImplV2 implements MailService {
    private final JavaMailSender mailSender;
    private final Environment env;

    @Autowired
    public MailServiceImplV2(JavaMailSender mailSender, Environment env) {
        this.mailSender = mailSender;
        this.env = env;
    }


    @Override
    public void sendMail(MailRequest dto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(env.getProperty("admin.email"));
            message.setSubject("Private message received");
            message.setText(
                    "You have received a new message from: " + dto.getEmail() + "\n" +
                            "Name: " + dto.getName() + "\n\n" +
                            dto.getMessage()
            );
            message.setFrom(env.getProperty("spring.mail.username"));

            this.mailSender.send(message);
        } catch (Exception e) {
            throw new GeneralException("Could not deliver the message for some reason :(");
        }
    }

    @Override
    public void getConfirmationMail(MailRequest dto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(dto.getEmail());
            message.setSubject("Confirmation - Photo gallery website");
            message.setText(
                    "Dear " + dto.getName() + ", \n" +
                            "Thank you for reaching out to me through the Photo Gallery Website. " +
                            "I have successfully received your message, and I will review it as soon as possible.\n" +
                            "Thank you for your interest, and I look forward to assisting you.\n\n" +
                            "Best regards,\n" +
                            env.getProperty("admin.last-name") + " " + env.getProperty("admin.first-name") + "\n" +
                            "Email: " + env.getProperty("admin.email") + "\n\n" +
                            "This is an auto-generated email."
            );

            message.setFrom(env.getProperty("spring.mail.username"));
            mailSender.send(message);
        } catch (Exception e) {
            throw new GeneralException("Something went wrong");
        }
    }
}
