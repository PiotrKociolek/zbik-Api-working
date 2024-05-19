package pl.pkociolek.zbik.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Value;


import java.util.Properties;
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:zbik.properties")
public class EmailConfiguration {

    @Value("${EMAIL_DEBUG_MODE:false}")
    private Boolean debugMode;

    @Value("${EMAIL_TRANSPORT_PROTOCOL:smtp}")
    private String transportProtocol;

    @Value("${ENABLE_SMTP_AUTH:true}")
    private Boolean smtpAuth;

    @Value("${ENABLE_SSL:true}")
    private Boolean enableSsl;

    @Value("${ENABLE_START_SSL:true}")
    private Boolean startSslEnable;

    @Value("${REQUIRED_START_SSL:true}")
    private Boolean startSslRequired;

    @Value("${EMAIL_HOST:smtp.gmail.com}")
    private String mailSenderHost;

    @Value("${EMAIL_PORT:587}")
    private Integer mailSenderPort;

    @Value("${EMAIL_USERNAME:zbiktestmail@gmail.com}")
    private String mailSenderUsername;

    @Value("${EMAIL_PASSWORD:Dupa1234}")
    private String mailSenderPassword;
}

