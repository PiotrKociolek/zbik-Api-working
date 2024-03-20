package pl.pkociolek.zbik.configuration.properties.encryption;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:zbik.properties")
@ConfigurationProperties(prefix = "encryption")
public @Data class EncryptionProperties {
    private String vector;
    private String key;
    private Integer tokenExpiryHours;
}
