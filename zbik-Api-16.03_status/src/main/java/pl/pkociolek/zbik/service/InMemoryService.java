package pl.pkociolek.zbik.service;

import pl.pkociolek.zbik.model.dtos.request.RIPRequestDto;
import pl.pkociolek.zbik.repository.entity.InMemoryEntity;

import java.util.List;

public interface InMemoryService {
    void addToMemorialPage(RIPRequestDto dto);
    void update(String id, String newFirstName, String newLastName);
    List<InMemoryEntity>getListOfAll(InMemoryEntity entity);
    void delete(String id);
}
