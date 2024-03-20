package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.InMemoryEntity;

import java.util.List;


public interface InMemoryRepository extends MongoRepository<InMemoryEntity, String> {
    List<InMemoryEntity> findAll();
}
