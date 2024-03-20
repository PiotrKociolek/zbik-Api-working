package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pkociolek.zbik.repository.UserRepository;
import pl.pkociolek.zbik.repository.entity.ResetPasswordAndActiveAccEntity;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resetPasswordAndActivateAcc")

public class EmailActivationAndResetPassController {
    private JavaMailSender javaMailSender;
    private UserRepository user;
    private ModelMapper modelMapper;
    @PostMapping("/sendActivationEmail")
    public void sendActivationEmail(@RequestBody ResetPasswordAndActiveAccEntity userEntity) {
        String activationLink = UUID.randomUUID().toString();
        userEntity.setActivationLink(activationLink);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEntity.getUserAddress());
        message.setSubject("Aktywacja konta");
        message.setText("Witaj! Kliknij w poniższy link, aby aktywować swoje konto: http://localhost:8080/activate?token=" + activationLink);
        javaMailSender.send(message);
    }

    @PostMapping("/sendPasswordResetEmail")
    public void sendPasswordResetEmail(@RequestBody ResetPasswordAndActiveAccEntity entity) {
        String resetLink = UUID.randomUUID().toString();
        entity.setResetLink(resetLink);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(entity.getUserAddress());
        message.setSubject("Resetowanie hasła");
        message.setText("Witaj! Kliknij w poniższy link, aby zresetować swoje hasło: http://localhost:8080/reset?token=" + resetLink);
        javaMailSender.send(message);
    }


}

