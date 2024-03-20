package pl.pkociolek.zbik.utilities.jwt;

import pl.pkociolek.zbik.model.dtos.response.UserJWT;

public interface JwtTokenEncoder {
    boolean isTokenValid(String bearerTokenString);

    UserJWT getTokenModel(final String bearerTokenString);

    String generateBearerJwtTokenFromModel(UserJWT jwtToken);
}
