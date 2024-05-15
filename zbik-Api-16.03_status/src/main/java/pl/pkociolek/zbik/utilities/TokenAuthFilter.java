package pl.pkociolek.zbik.utilities;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.pkociolek.zbik.model.dtos.user.UserJWT;
import pl.pkociolek.zbik.utilities.jwt.JwtTokenEncoder;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class TokenAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenEncoder tokenEncoder;

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getParameter("email"), request.getParameter("password"), new ArrayList<>()));
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        final User user = ((User) authResult.getPrincipal());
        final UserJWT jwt = new UserJWT();
        jwt.setEmail(user.getUsername());
        final String token = tokenEncoder.generateBearerJwtTokenFromModel(jwt);
        response.addHeader("Autenthication", token);
    }
}