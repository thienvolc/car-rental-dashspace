package dashspace.fun.car_rental_server.domain.verification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public void sendRegistrationOtp(String email, String code) {
        String subject = "DashSpace - Registration OTP";
        String content = """
                Welcome to DashSpace!
                
                Your registration OTP code is: %s
                
                This code will expire in 5 minutes.
                
                If you didn't request this, please ignore this email.
                
                Best regards,
                DashSpace Team
                """.formatted(code);
        sendSimpleEmail(email, subject, content);
    }

    public void sendForgotPasswordOtp(String email, String code) {
        String subject = "DashSpace - Forgot Password OTP";
        String content = """
                Hello,
                
                Your OTP code for resetting your password is: %s
                
                This code will expire in 5 minutes.
                
                If you didn't request this, please ignore this email.
                
                Best regards,
                DashSpace Team
                """.formatted(code);
        sendSimpleEmail(email, subject, content);
    }

    public void sendHostRegistrationOtp(String email, String code) {
        String subject = "DashSpace - Host Registration OTP";
        String content = """
                Hello,
                
                Your OTP code for host registration is: %s
                
                This code will expire in 15 minutes.
                
                If you didn't request this, please ignore this email.
                
                Best regards,
                DashSpace Team
                """.formatted(code);
        sendSimpleEmail(email, subject, content);
    }

    private void sendSimpleEmail(String email, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        this.mailSender.send(message);
    }
}
