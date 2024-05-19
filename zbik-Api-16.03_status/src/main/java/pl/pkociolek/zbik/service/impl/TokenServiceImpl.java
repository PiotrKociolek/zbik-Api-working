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
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private static final int TOKEN_LENGTH = 16;

}
