package pl.pkociolek.zbik.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.pkociolek.zbik.service.EmailActivationAndPassReset;

public class EmailActivationAndPassResetImpl implements EmailActivationAndPassReset {
    private JavaMailSender javaMailSender;
    @Override
    public void sendActivationEmail(String recipientEmail, String activationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Aktywacja konta");
        message.setText("Aktywuj swoje konto na stronie koła łowieckiego żbik: " + activationLink);
        javaMailSender.send(message);

    }



    @Override
    public void sendPasswordResetEmail(String recipientEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Resetowanie hasła");
        message.setText("Kliknij w poniższy link, aby zresetować swoje hasło: " + resetLink);
        javaMailSender.send(message);
    }
}
