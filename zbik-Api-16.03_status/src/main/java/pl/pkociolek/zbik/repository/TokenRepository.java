package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.model.TokenType;
import pl.pkociolek.zbik.repository.entity.RoleEntity;
import pl.pkociolek.zbik.repository.entity.TokenEntity;

public interface TokenRepository extends MongoRepository<TokenEntity, String> {
    TokenEntity findByTokenAndTokenType(String token, TokenType tokenType);
}