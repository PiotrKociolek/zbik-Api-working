package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.ManagementEntity;

public interface ManagementRepository extends MongoRepository<ManagementEntity, String> {}
