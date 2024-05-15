package pl.pkociolek.zbik.service.impl;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pkociolek.zbik.exception.EntityNotFoundException;
import pl.pkociolek.zbik.model.dtos.request.RIPRequestDto;
import pl.pkociolek.zbik.model.dtos.request.RIPUpdateDto;
import pl.pkociolek.zbik.repository.InMemoryRepository;
import pl.pkociolek.zbik.repository.entity.InMemoryEntity;
import pl.pkociolek.zbik.service.InMemoryService;

@Service
@RequiredArgsConstructor
public class InMemoryServiceImpl implements InMemoryService {
  private final ModelMapper modelMapper;
  private final InMemoryRepository repository;

  @Override
  public void addToMemorialPage(final RIPRequestDto dto) {
    final InMemoryEntity entity = modelMapper.map(dto, InMemoryEntity.class);
    entity.setId(null);
    entity.setName(dto.getName());
    entity.setSurname(dto.getSurname());
    repository.save(entity);
  }

    @Override
    public void updateInMemory(String id, RIPUpdateDto dto) {
        Optional<InMemoryEntity> optionalEntity = repository.findById(id);

        if (optionalEntity.isPresent()) {
            InMemoryEntity inMemoryEntity = optionalEntity.get();
            inMemoryEntity.setName(dto.getName());
            inMemoryEntity.setSurname(dto.getSurname());
            repository.save(inMemoryEntity);
        } else {
            throw new EntityNotFoundException();
        }
    }


    @Override
  public List<InMemoryEntity> getListOfAll() {
    return repository.findAll();
  }

  @Override
  public void delete(final String id) {
    repository.deleteById(id);
  }
}
