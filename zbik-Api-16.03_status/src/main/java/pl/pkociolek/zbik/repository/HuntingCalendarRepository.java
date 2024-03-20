package pl.pkociolek.zbik.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pkociolek.zbik.repository.entity.HuntingCalendarEntity;

public interface HuntingCalendarRepository extends MongoRepository<HuntingCalendarEntity, String> {}
