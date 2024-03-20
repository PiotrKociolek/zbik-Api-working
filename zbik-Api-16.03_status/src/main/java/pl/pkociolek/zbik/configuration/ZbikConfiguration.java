package pl.pkociolek.zbik.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.pkociolek.zbik.configuration.properties.email.EmailProperties;
import pl.pkociolek.zbik.configuration.properties.encryption.EncryptionProperties;

@Configuration
@EnableConfigurationProperties({EmailProperties.class, EncryptionProperties.class})
public class ZbikConfiguration {}
