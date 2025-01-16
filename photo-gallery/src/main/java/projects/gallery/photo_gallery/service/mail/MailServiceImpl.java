package projects.gallery.photo_gallery.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import projects.gallery.photo_gallery.dto.request.MailRequest;
import projects.gallery.photo_gallery.exception.BadRequestException;
import projects.gallery.photo_gallery.service.interfaces.MailService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final Environment env;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, Environment env) {
        this.mailSender = mailSender;
        this.env = env;
    }

    @Override
    public void sendMail(MailRequest dto) {
        Map<String, String> errors = validateMail(dto);

        if (!errors.isEmpty()) {
            throw new BadRequestException("Error sending the message", errors);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(env.getProperty("admin.email"));
        message.setSubject("Private message received");
        message.setText(
                "You have received a new message from: " + dto.getEmail() + "\n" +
                "Name: " + dto.getName() + "\n\n" +
                dto.getMessage()
        );
        message.setFrom(env.getProperty("spring.mail.username"));

        mailSender.send(message);
    }

    @Override
    public void getConfirmationMail(MailRequest dto) {
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
    }

    private Map<String, String> validateMail(MailRequest dto) {
        String NAME_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)+$";
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        int MESSAGE_MAX_LENGTH = 1000;

        Map<String, String> errors = new HashMap<>();

        if (dto.getName() == null || dto.getName().isBlank()) {
            errors.put("name", "Name cannot be empty");
        } else if (!Pattern.matches(NAME_REGEX, dto.getName())) {
            errors.put("name", "Enter your full name");
        }


        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            errors.put("email", "Email cannot be empty");
        } else if (!Pattern.matches(EMAIL_REGEX, dto.getEmail())) {
            errors.put("email", "Invalid email format");
        }

        if (dto.getMessage() == null || dto.getMessage().isBlank()) {
            errors.put("message", "Message cannot be empty");
        } else if (dto.getMessage().length() > MESSAGE_MAX_LENGTH) {
            errors.put("message", "Message must be maximum " + MESSAGE_MAX_LENGTH + " characters long");
        }

        return errors;
    }
}
