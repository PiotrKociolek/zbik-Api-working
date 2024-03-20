package pl.pkociolek.zbik.service;

public interface EmailActivationAndPassReset {
    public void sendActivationEmail(String recipientEmail, String activationLink);

    public void sendPasswordResetEmail(String recipientEmail, String resetLink);
    }

