package pl.pkociolek.zbik.service.impl;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pkociolek.zbik.exception.EntityNotFoundException;
import pl.pkociolek.zbik.model.dtos.request.RIPRequestDto;
import pl.pkociolek.zbik.repository.InMemoryRepository;
import pl.pkociolek.zbik.repository.entity.InMemoryEntity;
import pl.pkociolek.zbik.service.InMemoryService;

@Service
@Transactional
@RequiredArgsConstructor
public class InMemoryServiceImpl implements InMemoryService {
  private final ModelMapper modelMapper;
  private final InMemoryRepository repository;

  @Override
  public void addToValhalla(final RIPRequestDto dto) {
    final InMemoryEntity legend = modelMapper.map(dto, InMemoryEntity.class);
    legend.setId(null);
    repository.save(legend);
  }

  @Override
  public void update(final String id, final String uName, final String uSurname) {
      Optional<InMemoryEntity> optionalEntity = repository.findById(id);

      if (optionalEntity.isPresent()) {
          InMemoryEntity legend = optionalEntity.get();
          legend.setSurname(uSurname);
          legend.setName(uName);
          repository.save(legend);}
          else {
              throw new EntityNotFoundException();
          }
  }

  @Override
  public List<InMemoryEntity> getListOfAll(final InMemoryEntity entity) {
    return repository.findAll();
  }

  @Override
  public void delete(final String id) {
    repository.deleteById(id);
  }
}
