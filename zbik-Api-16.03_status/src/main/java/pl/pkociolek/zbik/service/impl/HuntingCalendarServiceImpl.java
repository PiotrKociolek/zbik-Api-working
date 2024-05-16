package pl.pkociolek.zbik.service.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.CannotCreateUploadFolderException;
import pl.pkociolek.zbik.exception.DatabaseEntityIsNotExistException;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.model.dtos.calendar.UpdateCalendarDto;
import pl.pkociolek.zbik.model.dtos.request.CalendarRequestDto;
import pl.pkociolek.zbik.model.dtos.response.HuntingCalendarDto;
import pl.pkociolek.zbik.repository.HuntingCalendarRepository;
import pl.pkociolek.zbik.repository.entity.HuntingCalendarEntity;
import pl.pkociolek.zbik.repository.entity.ManagementEntity;
import pl.pkociolek.zbik.service.HuntingCalendarService;

@Service
@Transactional
@RequiredArgsConstructor
public class HuntingCalendarServiceImpl implements HuntingCalendarService {
  private final HuntingCalendarRepository repository;
  private final ModelMapper modelMapper;
  private final Path root = Paths.get("uploads");

  @Override
  public void addItemToCalendar(final HuntingCalendarDto dto, final MultipartFile file) {
    final HuntingCalendarEntity entity = modelMapper.map(dto, HuntingCalendarEntity.class);
    uploadFolderExists();
    try {
      final HuntingCalendarEntity calendarEntity = setCalendarDetails(dto,file);
      Files.copy(
              file.getInputStream(),
              this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(calendarEntity))),
              StandardCopyOption.REPLACE_EXISTING);
    } catch (final Exception e) {
      throw new FileAlreadyExistsException();
    }
    repository.save(entity);
  }

  @Override
  public void delete(String id) {
    repository.deleteById(id);
  }

  private HuntingCalendarEntity setCalendarDetails(HuntingCalendarDto dto, MultipartFile file) {
    final HuntingCalendarEntity entity = new HuntingCalendarEntity();
    entity.setId(null);
    entity.setAnimalSpecies(dto.getSpecies());
    entity.setDescription(dto.getDescription());
    entity.setObfuscatedFileName(generateUniqueFileName());
    String[] extension = file.getOriginalFilename().split("\\.");
    entity.setFileExtension(extension[extension.length-1]);
    return entity;
  }

  @Override
  public void updateCalendarItem(UpdateCalendarDto dto, MultipartFile file) {
    Optional<HuntingCalendarEntity> optionalCalendarEntity = repository.findById(dto.getId());

    if (optionalCalendarEntity.isPresent()) {
      HuntingCalendarEntity calendarEntity = optionalCalendarEntity.get();

      // Aktualizacja pól na podstawie danych z DTO
      calendarEntity.setAnimalSpecies(dto.getSpecies());
      calendarEntity.setDescription(dto.getDescription());

      // Aktualizacja pliku, jeśli taki został przesłany
      if (file != null && !file.isEmpty()) {
        try {
          Files.copy(
                  file.getInputStream(),
                  this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(calendarEntity))),
                  StandardCopyOption.REPLACE_EXISTING
          );
          // Aktualizacja nazwy pliku i rozszerzenia
          String[] extension = file.getOriginalFilename().split("\\.");
          calendarEntity.setObfuscatedFileName(generateUniqueFileName());
          calendarEntity.setFileExtension(extension[extension.length - 1]);
        } catch (Exception e) {
          throw new FileAlreadyExistsException();
        }
      }

      // Zapisanie zaktualizowanego obiektu kalendarza do bazy danych
      repository.save(calendarEntity);
    } else {
      throw new DatabaseEntityIsNotExistException();
    }
  }

  private static String generateUniqueFileName() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String timestamp = dateFormat.format(new Date());
    return "file_" + timestamp ;
  }

  private void uploadFolderExists() {
    if (!Files.exists(root)) {
      try {
        Files.createDirectories(root);
      } catch (Exception e) {
        throw new CannotCreateUploadFolderException();
      }
    }
  }

  private String getFileNameAndExtension(final HuntingCalendarEntity hEntity) {
    final String filename = hEntity.getObfuscatedFileName();
    final String extension = hEntity.getFileExtension();
    return String.format("%s.%s", filename, extension);
  }
}
