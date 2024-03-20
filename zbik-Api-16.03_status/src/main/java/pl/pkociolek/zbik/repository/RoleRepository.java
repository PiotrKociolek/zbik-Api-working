package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.RoleEntity;

public interface RoleRepository extends MongoRepository<RoleEntity, String> {}
