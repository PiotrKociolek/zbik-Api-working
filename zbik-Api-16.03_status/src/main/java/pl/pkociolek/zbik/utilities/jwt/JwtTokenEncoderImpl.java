package pl.pkociolek.zbik.utilities.jwt;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.spec.AlgorithmParameterSpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import pl.pkociolek.zbik.exception.JwtGenerationException;
import pl.pkociolek.zbik.model.dtos.user.UserJWT;
import pl.pkociolek.zbik.repository.UserRepository;
import pl.pkociolek.zbik.repository.entity.UserEntity;

@RequiredArgsConstructor

public class JwtTokenEncoderImpl implements JwtTokenEncoder{
    private final String encryptionVector;
    private final String encryptionKey;
    private final int tokenExpiryHours;
    private final UserRepository accountRepository;
    private final ObjectMapper mapper;
    @Override
    public boolean isTokenValid(final String bearerTokenString) {
        if (bearerTokenString == null) return false;
        if (!bearerTokenString.contains("Bearer")) return false;
        final UserJWT token = decodeJwtToken(bearerTokenString.replace("Bearer ", ""));
        final Optional<UserEntity> accountDocument = accountRepository.findById(token.getId());
        return accountDocument.map(doc -> checkJwtExpiryDateTime(token.getExpirationDate())).orElse(false);
    }


    @Override
    public UserJWT getTokenModel(String bearerTokenString) {
        return decodeJwtToken(bearerTokenString.replace("Bearer ", ""));
    }

    @Override
    public String generateBearerJwtTokenFromModel(final UserJWT jwtToken) {
        String jsonPayload = null;
        try {
            jwtToken.setExpirationDate(Instant.now().plus(tokenExpiryHours, ChronoUnit.HOURS));
            jsonPayload = mapper.writeValueAsString(jwtToken);
        } catch (final JsonProcessingException e) {
            throw new JwtGenerationException();
        }
        return encodeJsonContents(jsonPayload);
    }

    private String encodeJsonContents(final String jsonPayload) {
        try {
            final byte[] key = encryptionKey.getBytes(UTF_8);
            final byte[] ivs = encryptionVector.getBytes(UTF_8);
            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            final AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivs);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, paramSpec);
            return String.format("Bearer %s", Base64.getEncoder().encodeToString(cipher.doFinal(jsonPayload.getBytes(UTF_8))));
        } catch (final Exception e) {
            throw new JwtGenerationException();
        }
    }

    private UserJWT decodeJwtToken(final String jsonPayload) {
        try {
            final byte[] key = encryptionKey.getBytes(UTF_8);
            final byte[] ivs = encryptionVector.getBytes(UTF_8);
            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            final AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivs);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, paramSpec);
            final byte[] decodedString = Base64.getDecoder().decode(jsonPayload.getBytes(UTF_8));
            final String objectString = new String(cipher.doFinal(decodedString));
            return mapper.readValue(objectString, UserJWT.class);
        } catch (final Exception e) {
            throw new JwtGenerationException();
        }
    }

    private boolean checkJwtExpiryDateTime(final Instant expiryDateTime) {
        final Instant currentInstant = Instant.now();
        return currentInstant.isBefore(expiryDateTime);
    }
}
