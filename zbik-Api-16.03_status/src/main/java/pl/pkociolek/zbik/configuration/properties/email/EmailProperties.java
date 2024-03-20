package pl.pkociolek.zbik.configuration.properties.email;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:zbik.properties")
@ConfigurationProperties(prefix = "email")
public @Data class EmailProperties {
  private String transportProtocol;
  private Boolean debugMode;
  private EmailSmtpSettings smtp;
  private EmailSenderSettings mailSender;
}
