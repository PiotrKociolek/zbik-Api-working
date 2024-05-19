package pl.pkociolek.zbik.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

public interface EmailActivationAndPassReset {
    public void sendActivationEmail(String recipientEmail, String activationLink);

    public void sendPasswordResetEmail(String recipientEmail, String resetLink);

    }

