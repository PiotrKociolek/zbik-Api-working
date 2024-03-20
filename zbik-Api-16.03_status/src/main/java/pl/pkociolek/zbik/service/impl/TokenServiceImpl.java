package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pkociolek.zbik.service.TokenService;
@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Override
    public String generateResetToken() {
        return null;
    }

    @Override
    public String generateActivationToken() {
        return null;
    }

    @Override
    public boolean verifyResetToken(String token) {
        return false;
    }

    @Override
    public boolean verifyActivationToken(String token) {
        return false;
    }
}
