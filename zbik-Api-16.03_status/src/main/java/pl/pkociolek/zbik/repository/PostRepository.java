package pl.pkociolek.zbik.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.model.PostVisibility;
import pl.pkociolek.zbik.repository.entity.PostEntity;

public interface PostRepository extends MongoRepository<PostEntity, String> {
    Page<PostEntity> findAllByPostVisibility(PostVisibility postVisibility, Pageable pageable);
}
