package pl.pkociolek.zbik.utilities.jwt;

import pl.pkociolek.zbik.model.dtos.user.UserJWT;

public interface JwtTokenEncoder {
    boolean isTokenValid(String bearerTokenString);

    UserJWT getTokenModel(final String bearerTokenString);

    String generateBearerJwtTokenFromModel(UserJWT jwtToken);
}
