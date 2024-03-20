package pl.pkociolek.zbik.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameAndEmailAddress(String username, String emailAddress);

    Optional<UserEntity> findByEmailAddress(String email);
    List<UserEntity> findAllOrderBySurname(String surname);

}
