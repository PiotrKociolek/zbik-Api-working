package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.pkociolek.zbik.repository.entity.CategoryEntity;

public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {}
