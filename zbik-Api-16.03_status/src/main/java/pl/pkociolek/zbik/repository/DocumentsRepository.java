package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.DocsEntity;

public interface DocumentsRepository extends MongoRepository<DocsEntity, String> {}
