package pl.pkociolek.zbik.service.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.DatabaseEntityIsNotExistException;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.model.dtos.request.CalendarRequestDto;
import pl.pkociolek.zbik.model.dtos.response.HuntingCalendarDto;
import pl.pkociolek.zbik.repository.HuntingCalendarRepository;
import pl.pkociolek.zbik.repository.entity.HuntingCalendarEntity;
import pl.pkociolek.zbik.service.HuntingCalendarService;

@Service
@Transactional
@RequiredArgsConstructor
public class HuntingCalendarServiceImpl implements HuntingCalendarService {
  private final HuntingCalendarRepository repository;
  private final ModelMapper modelMapper;
  private final Path root = Paths.get("uploads");

  @Override
  public void addDescription(final HuntingCalendarDto huntingCalendarDto) {
    final Optional<HuntingCalendarEntity> entity = repository.findById(huntingCalendarDto.getId());
    entity.ifPresentOrElse(
        x -> addDescriptionToEntity(x, huntingCalendarDto.getDescription()),
        DatabaseEntityIsNotExistException::new);
  }

  @Override
  public void addSpecies(final HuntingCalendarDto huntingCalendarDto) {
    final Optional<HuntingCalendarEntity> entity = repository.findById(huntingCalendarDto.getId());
    entity.ifPresentOrElse(
        x -> addSpeciesToEntity(x, huntingCalendarDto.getDescription()),
        DatabaseEntityIsNotExistException::new);
  }

  @Override
  public void editDescription(final HuntingCalendarDto huntingCalendarDto) {
    final Optional<HuntingCalendarEntity> entity = repository.findById(huntingCalendarDto.getId());
    entity.ifPresentOrElse(
        x -> editDescriptionToEntity(x, huntingCalendarDto.getDescription()),
        DatabaseEntityIsNotExistException::new);
  }

  @Override
  public void deleteFromTable(final String id) {
    repository.deleteById(id);
  }



  @Override
  public void save(MultipartFile file, CalendarRequestDto dto){
    try {
      final HuntingCalendarEntity hEntity= setIconDetails(file,dto);
      Files.copy(
              file.getInputStream(),
              this.root.resolve(Objects.requireNonNull(getFileNameANdExtension(hEntity))),
              StandardCopyOption.REPLACE_EXISTING);
      repository.save(hEntity);
    } catch (final Exception e) {
      throw new FileAlreadyExistsException();
    }
  }



  private void addDescriptionToEntity(
      final HuntingCalendarEntity entity, final String description) {}

  private void addSpeciesToEntity(final HuntingCalendarEntity entity, final String description) {}

  private void editDescriptionToEntity(
      final HuntingCalendarEntity entity, final String description) {}

  private HuntingCalendarEntity setIconDetails(
      final MultipartFile file, final CalendarRequestDto dto) {
    final HuntingCalendarEntity huntingCalendar = modelMapper.map(dto, HuntingCalendarEntity.class);
    huntingCalendar.setId(null);
    huntingCalendar.setObfuscatedFileName(generateFilename());
    return huntingCalendar;
  }

  private String generateFilename() {
    final byte[] array = new byte[32];
    new Random().nextBytes(array);
    return new String(array, StandardCharsets.UTF_8);
  }

  private String getFileNameANdExtension(final HuntingCalendarEntity hEntity) {
    final String filename = hEntity.getObfuscatedFileName();
    final String extension = hEntity.getFileExtension();

    return String.format("%s.%s", filename, extension);
  }
}
