package pl.pkociolek.zbik.configuration;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.pkociolek.zbik.service.SecurityDetailsService;
import pl.pkociolek.zbik.utilities.TokenAuthFilter;
import pl.pkociolek.zbik.utilities.TokenAuthenticationFilter;
import pl.pkociolek.zbik.utilities.jwt.JwtTokenEncoder;


@Configuration
@EnableWebMvc
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SpringSecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenEncoder jwtTokenEncoder;
    private final SecurityDetailsService securityDetailsService;

    @Bean
    public AuthenticationManager authManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(chain -> chain
                        .requestMatchers("/swagger-ui/**")
                        .permitAll()
                       /* .requestMatchers(GET)
                        .permitAll()*/

                        .anyRequest()
                        .permitAll())
                .addFilter(new TokenAuthFilter(authManager(), jwtTokenEncoder))
                .addFilterBefore(new TokenAuthenticationFilter(authManager(), jwtTokenEncoder, securityDetailsService), TokenAuthenticationFilter.class);
        return http.build();
    }
}
