package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pkociolek.zbik.model.TokenType;
import pl.pkociolek.zbik.repository.TokenRepository;
import pl.pkociolek.zbik.repository.entity.TokenEntity;
import pl.pkociolek.zbik.service.TokenService;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private static final int TOKEN_LENGTH = 16;

    @Override
    public String generateResetToken() {
        return generateToken();
    }

    @Override
    public String generateActivationToken() {
        return generateToken();
    }

    @Override
    public boolean verifyResetToken(String token) {
        TokenEntity resetToken = tokenRepository.findByTokenAndTokenType(token, TokenType.RESET);
        return resetToken != null && !resetToken.isExpired();
    }

    @Override
    public boolean verifyActivationToken(String token) {
        TokenEntity activationToken = tokenRepository.findByTokenAndTokenType(token, TokenType.ACTIVATION);
        return activationToken != null && !activationToken.isExpired();
    }

    private String generateToken() {
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        new SecureRandom().nextBytes(tokenBytes); // Generowanie losowych bajtów
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes); // Konwersja na Base64 URL-safe
    }
}
