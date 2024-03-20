package pl.pkociolek.zbik.configuration;

import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import pl.pkociolek.zbik.configuration.properties.email.EmailProperties;

@Configuration
@RequiredArgsConstructor
public class JavaMailSenderConfiguration {
  private final EmailProperties emailProperties;

  @Bean
  JavaMailSender javaMailSender() {
    final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost(emailProperties.getMailSender().getHost());
    javaMailSender.setPort(emailProperties.getMailSender().getPort());
    javaMailSender.setUsername(emailProperties.getMailSender().getUsername());
    javaMailSender.setPassword(emailProperties.getMailSender().getPassword());
    javaMailSender.setDefaultEncoding(emailProperties.getMailSender().getDefaultEncoding());
    final Properties props = javaMailSender.getJavaMailProperties();
    props.setProperty("mail.transport.protocol", emailProperties.getTransportProtocol());
    props.setProperty("mail.smtp.auth", emailProperties.getSmtp().getAuth().toString());
    props.setProperty(
            "mail.smtp.ssl.enable", emailProperties.getSmtp().getEnableSsl().toString());
    props.setProperty(
            "mail.smtp.starttls.enable",
            emailProperties.getSmtp().getStartSslEnable().toString());
    props.setProperty(
            "mail.smtp.starttls.required",
            emailProperties.getSmtp().getStartSslRequired().toString());
    props.setProperty("mail.debug", emailProperties.getDebugMode().toString());
    return javaMailSender;
  }
}
