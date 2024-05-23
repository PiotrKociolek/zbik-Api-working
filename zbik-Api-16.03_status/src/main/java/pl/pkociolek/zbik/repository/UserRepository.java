package pl.pkociolek.zbik.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameAndEmail(String username, String emailAddress);

    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findAllOrderBySurname(String surname);

}
