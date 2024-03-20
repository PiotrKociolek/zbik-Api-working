package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.ImageEntity;

public interface ImageRepository extends MongoRepository<ImageEntity,String> {
}
