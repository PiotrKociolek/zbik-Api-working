package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.InMemoryEntity;
import pl.pkociolek.zbik.repository.entity.MembersEntity;

import java.util.List;

public interface MemberRepository extends MongoRepository<MembersEntity, String> {
    List<MembersEntity> findAll();

}
