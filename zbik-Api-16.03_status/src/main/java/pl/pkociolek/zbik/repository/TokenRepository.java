package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.model.TokenType;
import pl.pkociolek.zbik.repository.entity.RoleEntity;
import pl.pkociolek.zbik.repository.entity.TokenEntity;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<TokenEntity, String> {
    Optional<TokenEntity> findByTokenAndTokenType(String token, TokenType type);
}