package pl.pkociolek.zbik.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.PendingEmailEntity;

public interface PendingEmailRepository extends MongoRepository<PendingEmailEntity, String> {
  List<PendingEmailEntity> findAllByAlreadySent(boolean alreadySent);
}
