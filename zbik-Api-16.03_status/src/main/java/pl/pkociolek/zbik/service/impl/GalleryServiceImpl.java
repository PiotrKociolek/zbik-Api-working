package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.CannotCreateUploadFolderException;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.exception.GalleryNotFoundException;
import pl.pkociolek.zbik.model.dtos.Gallery.ModifyGalleryDto;
import pl.pkociolek.zbik.model.dtos.request.GalleryRequestDto;
import pl.pkociolek.zbik.repository.GalleryRepository;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;
import pl.pkociolek.zbik.service.GalleryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
//@Transactional
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {
  private final GalleryRepository galleryRepository;
  private final Path root = Paths.get("uploads");
  private final ModelMapper modelMapper;
  private final List<GalleryEntity> photoList = new ArrayList<>();
  private static String generateUniqueFileName() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String timestamp = dateFormat.format(new Date());
    return "file_" + timestamp;
  }

  @Override
  public void deleteById(String id) {
    galleryRepository.deleteById(id);
  }

  @Override
  public void addNewGallery(final MultipartFile file, final GalleryRequestDto galleryRequestDto) {
    try {
      final GalleryEntity gallery = setGalleryDetails(file, galleryRequestDto);
      Files.copy(
              file.getInputStream(),
              this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(gallery))),
              StandardCopyOption.REPLACE_EXISTING);

      GalleryEntity savedGallery = galleryRepository.save(gallery);

      photoList.add(savedGallery);

      savedGallery.setEntityList(photoList);
      galleryRepository.save(savedGallery);

    } catch (final Exception e) {
      throw new FileAlreadyExistsException();
    }
  }

  @Override
  public List<GalleryEntity> getAllPhotos() {
    return galleryRepository.findAll();
  }

  @Override
  public void modify(ModifyGalleryDto dto, MultipartFile file) {
    Optional<GalleryEntity> optionalGallery = galleryRepository.findById(dto.getId());

    if (optionalGallery.isPresent()) {
      GalleryEntity gallery = optionalGallery.get();
      gallery.setTitle(dto.getTitle());

      // Aktualizacja listy zdjęć, jeśli przesłano nowy plik
      if (file != null && !file.isEmpty()) {
        try {
          Files.copy(
                  file.getInputStream(),
                  this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(gallery))),
                  StandardCopyOption.REPLACE_EXISTING
          );
          // Dodanie nowego zdjęcia do listy
          gallery.getEntityList().add(updateGalleryDetails(file, dto));
        } catch (IOException e) {
          throw new FileAlreadyExistsException();
        }
      }

      galleryRepository.save(gallery);
    } else {
      throw new GalleryNotFoundException();
    }
  }

  private GalleryEntity setGalleryDetails(
          final MultipartFile file, final GalleryRequestDto dto) {
    final GalleryEntity galleryEntity = modelMapper.map(dto, GalleryEntity.class);
    galleryEntity.setId(null);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String creationDate = dateFormat.format(new Date());
    galleryEntity.setCreatedAt(creationDate);
    galleryEntity.setTitle(dto.getTitle());
    galleryEntity.setObfuscatedFileName(generateUniqueFileName());
    String[] extension = file.getOriginalFilename().split("\\.");
    galleryEntity.setFileExtension(extension[extension.length - 1]);
    return galleryEntity;
  }

  private GalleryEntity updateGalleryDetails(
          final MultipartFile file, final ModifyGalleryDto dto) {
    final GalleryEntity galleryEntity = modelMapper.map(dto, GalleryEntity.class);
    galleryEntity.setId(null);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String cretionDate = dateFormat.format(new Date());
    galleryEntity.setCreatedAt(cretionDate);
    galleryEntity.setTitle(dto.getTitle());
    galleryEntity.setObfuscatedFileName(generateUniqueFileName());
    String[] extension = file.getOriginalFilename().split("\\.");
    galleryEntity.setFileExtension(extension[extension.length - 1]);
    return galleryEntity;
  }

  private String getFileNameAndExtension(final GalleryEntity entity) {
    final String filename = entity.getObfuscatedFileName();
    final String extension = entity.getFileExtension();

    return String.format("%s.%s", filename, extension);
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

}
