package pl.pkociolek.zbik.configuration;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

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
        http.authorizeHttpRequests(
                        chain ->
                                chain
                                        .requestMatchers("/swagger-ui/**")
                                        .permitAll()
                                        .requestMatchers(GET, "/gallery/**", "/management/**", "/posts/**")
                                        .hasAnyRole("ADMIN", "MODERATOR", "NORMAL", "GUEST")
                                        .requestMatchers(
                                                DELETE,
                                                "/calendar/**",
                                                "/user/**",
                                                "/gallery/**",
                                                "/management/**",
                                                "/documents/**",
                                                "/maps/**",
                                                "/posts/**")
                                        .hasAnyRole("ADMIN", "MODERATOR")
                                        .requestMatchers(
                                                PUT, "/calendar/**", "/documents/**", "/gallery/**", "/management/**", "/posts/**")
                                        .hasAnyRole("ADMIN", "MODERATOR", "NORMAL", "GUEST")
                                        .requestMatchers(POST, "/user/**", "/gallery/**")
                                        .hasAnyRole("ADMIN")
                                        .requestMatchers(POST, "/gallery/**")
                                        .hasAnyRole("ADMIN", "MODERATOR")
                                        .requestMatchers(POST, "/api/v1/admin/admin")
                                        .permitAll()
                                        .anyRequest()
                                        .permitAll())
                .httpBasic(Customizer.withDefaults())
                .addFilter(new TokenAuthFilter(authManager(), jwtTokenEncoder))
                .addFilterBefore(
                        new TokenAuthenticationFilter(authManager(), jwtTokenEncoder, securityDetailsService),
                        TokenAuthenticationFilter.class);
        return http.build();
    }
}
