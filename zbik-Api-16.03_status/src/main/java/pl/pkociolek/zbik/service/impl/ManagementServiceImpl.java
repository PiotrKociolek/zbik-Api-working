package pl.pkociolek.zbik.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
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
//@Transactional
public class ManagementServiceImpl implements ManagementService {
  private final ManagementRepository repository;
  private final ModelMapper modelMapper;

  private final Path root = Paths.get("uploads");

  @Override
  public void deleteFromManagement(final String id) {
    repository.deleteById(id);
  }

  @Override
  public void addToManagement(final ManagementInfoDto dto, final MultipartFile file){
      final ManagementEntity mgmtE = modelMapper.map(dto, ManagementEntity.class);
      uploadFolderExists();
    try {
      final ManagementEntity mEntity = setMgmtDetails(file, dto);
      Files.copy(
              file.getInputStream(),
              this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(mEntity))),
              StandardCopyOption.REPLACE_EXISTING);
    }

    catch (final Exception e) {
      throw new FileAlreadyExistsException();
    }
    repository.save(mgmtE);
  }


  private ManagementEntity setMgmtDetails(final MultipartFile file, final ManagementInfoDto dto) {
    final ManagementEntity entity = new ManagementEntity();
      entity.setId(null);
      entity.setName(dto.getName());
      entity.setSurname(dto.getSurname());
      entity.setRole(dto.getMgmtRole());
      entity.setContact(dto.getContact());
      entity.setObfuscatedFileName(generateUniqueFileName());
      String[] extension = file.getOriginalFilename().split("\\.");
      entity.setFileExtension(extension[extension.length-1]);
    return entity;
  }


  private static String generateUniqueFileName()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        return "file_" + timestamp ;
    }

  private String getFileNameAndExtension(final ManagementEntity mgmt) {
    final String filename = mgmt.getObfuscatedFileName();
    final String extension = mgmt.getFileExtension();

    return String.format("%s.%s", filename, extension);
  }
  private void uploadFolderExists() {
        if (!Files.exists(root)) {
            try {
                Files.createDirectories(root);
            } catch (Exception e) {
                throw new RuntimeException("Nie można utworzyć katalogu upload");
            }
        }
}
}
