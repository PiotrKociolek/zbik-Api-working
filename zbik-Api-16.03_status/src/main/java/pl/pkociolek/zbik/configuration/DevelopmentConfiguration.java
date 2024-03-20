package pl.pkociolek.zbik.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.pkociolek.zbik.repository.RoleRepository;
import pl.pkociolek.zbik.repository.UserRepository;

@RequiredArgsConstructor
@Configuration
@Profile("dev")
public class DevelopmentConfiguration {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Bean
    DevDatabaseConfiguration devDatabaseCOnfiguration() {
        final DevDatabaseConfiguration devDatabaseCOnfiguration =
                new DevDatabaseConfiguration(userRepository, roleRepository);
        devDatabaseCOnfiguration.initialize();
        return devDatabaseCOnfiguration;
    }
}
// zrobic kopie i zmienic na prod
