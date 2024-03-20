package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.MapsEntity;

public interface MapsRepository extends MongoRepository<MapsEntity, String>{}
