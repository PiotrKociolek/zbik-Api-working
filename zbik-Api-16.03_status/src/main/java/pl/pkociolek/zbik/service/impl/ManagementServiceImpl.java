package pl.pkociolek.zbik.service.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.model.dtos.request.ManagementImgDto;
import pl.pkociolek.zbik.model.dtos.response.ManagementInfoDto;
import pl.pkociolek.zbik.repository.ManagementRepository;
import pl.pkociolek.zbik.repository.entity.ManagementEntity;
import pl.pkociolek.zbik.service.ManagementService;

@Service
@RequiredArgsConstructor
@Transactional
public class ManagementServiceImpl implements ManagementService {
  private final ManagementRepository repository;
  private final ModelMapper modelMapper;

  private final Path root = Paths.get("uploads");

  @Override
  public void deleteFromManagement(final String id) {
    repository.deleteById(id);
  }

  @Override
  public void addToManagement(final ManagementInfoDto dto) {
    final ManagementEntity mgmtE = modelMapper.map(dto, ManagementEntity.class);
    mgmtE.setId(null);
    mgmtE.setSurname(dto.getSurname());
    mgmtE.setName(dto.getName());
    mgmtE.setRole(dto.getRole());
    mgmtE.setContact(dto.getContact());
    repository.save(mgmtE);
  }

  @Override
  public void addMgmtImg(final MultipartFile file, final ManagementImgDto dto) {
    try {
      final ManagementEntity mEntity = setMgmtDetails(file, dto);
      Files.copy(
          file.getInputStream(),
          this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(mEntity))),
          StandardCopyOption.REPLACE_EXISTING);
      repository.save(mEntity);
    } catch (final Exception e) {
      throw new FileAlreadyExistsException();
    }
  }

  private ManagementEntity setMgmtDetails(final MultipartFile file, final ManagementImgDto mgmt) {
    final ManagementEntity MgmtEntity = modelMapper.map(mgmt, ManagementEntity.class);
    MgmtEntity.setId(null);
    MgmtEntity.setObfuscatedFileName(generateFilename());
    return MgmtEntity;
  }

  private String generateFilename() {
    final byte[] array = new byte[33];
    new Random().nextBytes(array);
    return new String(array, StandardCharsets.UTF_8);
  }

  private String getFileNameAndExtension(final ManagementEntity mgmt) {
    final String filename = mgmt.getObfuscatedFileName();
    final String extension = mgmt.getFileExtension();

    return String.format("%s.%s", filename, extension);
  }
}
