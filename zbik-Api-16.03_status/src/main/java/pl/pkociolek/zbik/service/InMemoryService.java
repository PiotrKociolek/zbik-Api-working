package pl.pkociolek.zbik.service;

import pl.pkociolek.zbik.model.dtos.request.RIPRequestDto;
import pl.pkociolek.zbik.model.dtos.request.RIPUpdateDto;
import pl.pkociolek.zbik.repository.entity.InMemoryEntity;

import java.util.List;

public interface InMemoryService {
    void addToMemorialPage(RIPRequestDto dto);
    void updateInMemory(String id, RIPUpdateDto dto);
    List<InMemoryEntity>getListOfAll();
    void delete(String id);
}
