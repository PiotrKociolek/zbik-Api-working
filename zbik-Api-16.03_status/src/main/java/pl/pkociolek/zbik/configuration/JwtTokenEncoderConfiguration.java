package pl.pkociolek.zbik.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pl.pkociolek.zbik.configuration.properties.encryption.EncryptionProperties;
import pl.pkociolek.zbik.repository.UserRepository;
import pl.pkociolek.zbik.utilities.jwt.JwtTokenEncoder;
import pl.pkociolek.zbik.utilities.jwt.JwtTokenEncoderImpl;


@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:zbik.properties")
public class JwtTokenEncoderConfiguration {
    private final UserRepository accountRepository;
    @Value("${encryption.vector:HSIEUT9SFFY9GJ5N}")
    String encryptionVector;
    @Value("${encryption.key:WD6C514FE9OZUQIR}")
    String encryptionKey;
    @Value("${token.expiry.hours:1}")
    int tokenExpiryHours;
    private final ObjectMapper mapper;
    @Bean
    JwtTokenEncoder getJwtTokenEncoder() {
        return new JwtTokenEncoderImpl(encryptionVector, encryptionKey, tokenExpiryHours, accountRepository,mapper);
    }
}
