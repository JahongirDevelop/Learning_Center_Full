package uz.pdp.learning_center_full.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.exception.SendVerificationCodeException;


import java.util.Random;


@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;
    public static final Random random = new Random();

    public String sendVerificationCode(String email) {

        try {
            String  verificationCode = String.valueOf(random.nextInt(90000) + 10000);
            String message = "This is your verification code: " + verificationCode;
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setText(message);
            mailSender.send(simpleMailMessage);
            return verificationCode;
        } catch (Exception e) {
            throw new SendVerificationCodeException("Error to send the verification code, because email invalid");
        }

    }

}