package dashspace.fun.car_rental_server.common.util.message.sender;

import dashspace.fun.car_rental_server.common.util.message.contact.MessageContact;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender implements MessageSender {

    private final JavaMailSender mailSender;

    @Override
    public void sendRegistrationOtp(MessageContact contact, String code) {
        String subject = "DashSpace - Registration OTP";
        String content = """
                Welcome to DashSpace!
                
                Your registration OTP code is: %s
                
                This code will expire in 5 minutes.
                
                If you didn't request this, please ignore this email.
                
                Best regards,
                DashSpace Team
                """.formatted(code);
        sendSimpleEmail(contact.getRecipient(), subject, content);
    }

    @Override
    public void sendForgotPasswordOtp(MessageContact contact, String code) {
        String subject = "DashSpace - Forgot Password OTP";
        String content = """
                Hello,
                
                Your OTP code for resetting your password is: %s
                
                This code will expire in 5 minutes.
                
                If you didn't request this, please ignore this email.
                
                Best regards,
                DashSpace Team
                """.formatted(code);
        sendSimpleEmail(contact.getRecipient(), subject, content);
    }

    @Override
    public void sendHostRegistrationVerificationOtp(MessageContact contact, String code) {
        String subject = "DashSpace - Host Registration OTP";
        String content = """
                Hello,
                
                Your OTP code for host registration is: %s
                
                This code will expire in 15 minutes.
                
                If you didn't request this, please ignore this email.
                
                Best regards,
                DashSpace Team
                """.formatted(code);
        sendSimpleEmail(contact.getRecipient(), subject, content);
    }

    private void sendSimpleEmail(String email, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        this.mailSender.send(message);
    }
}
