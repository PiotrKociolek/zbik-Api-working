package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.ImageEntity;
import pl.pkociolek.zbik.repository.entity.ImgEntity;

public interface ImageRepository extends MongoRepository<ImgEntity,String> {
}
