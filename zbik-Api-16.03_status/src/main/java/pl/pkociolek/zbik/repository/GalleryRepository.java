package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;

public interface GalleryRepository extends MongoRepository<GalleryEntity, String> {}
