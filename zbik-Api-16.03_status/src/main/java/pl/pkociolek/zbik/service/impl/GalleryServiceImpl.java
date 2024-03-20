package pl.pkociolek.zbik.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.exception.GalleryNotFoundException;
import pl.pkociolek.zbik.model.dtos.request.GalleryRequestDto;
import pl.pkociolek.zbik.repository.GalleryRepository;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;
import pl.pkociolek.zbik.service.GalleryService;

@Service
@Transactional
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {
  private final GalleryRepository galleryRepository;
  private final Path root = Paths.get("uploads");
  private final ModelMapper modelMapper;
  private final List<GalleryEntity> photoList = new ArrayList<>();
  @Override
  public void addNewGallery(final MultipartFile file, final GalleryRequestDto galleryRequestDto) {
    try {
      final GalleryEntity gallery = setGalleryDetails(file, galleryRequestDto);
      Files.copy(
              file.getInputStream(),
              this.root.resolve(Objects.requireNonNull(getFileNameANdExtension(gallery))),
              StandardCopyOption.REPLACE_EXISTING);
      galleryRepository.save(gallery);
  photoList.add(gallery);
    } catch (final Exception e) {
      throw new FileAlreadyExistsException();
    }
  }

  @Override
  public void deleteById(String id) {
    galleryRepository.deleteById(id);
  }

  @Override
  public void modify(GalleryRequestDto galleryRequestDto, MultipartFile file) {
    Optional<GalleryEntity> optionalGallery = galleryRepository.findById(galleryRequestDto.getId());

    if (optionalGallery.isPresent()) {
      GalleryEntity gallery = optionalGallery.get();
      gallery.setTitle(galleryRequestDto.getTitle());
      if (file != null && !file.isEmpty()) {
        try {
          Files.copy(
                  file.getInputStream(),
                  this.root.resolve(Objects.requireNonNull(getFileNameANdExtension(gallery))),
                  StandardCopyOption.REPLACE_EXISTING
          );
        } catch (IOException e) {
          throw new FileAlreadyExistsException();
        }
      }
      galleryRepository.save(gallery);
    } else {
      throw new GalleryNotFoundException();
    }
  }

  @Override
  public List<GalleryEntity> getAllPhotos() {
    return photoList;
  }

  private GalleryEntity setGalleryDetails(
          final MultipartFile file, final GalleryRequestDto galleryRequestDto) {
    final GalleryEntity galleryEntity = modelMapper.map(galleryRequestDto, GalleryEntity.class);
    galleryEntity.setId(null);
    galleryEntity.getCreatedAt();
    galleryEntity.setTitle(galleryRequestDto.getTitle());
    galleryEntity.setObfuscatedFileName(generateFilename());

    return galleryEntity;
  }

  private String generateFilename() {
    final byte[] array = new byte[31];
    new Random().nextBytes(array);
    return new String(array, StandardCharsets.UTF_8);
  }

  private String getFileNameANdExtension(final GalleryEntity gallery) {
    final String filename = gallery.getObfuscatedFileName();
    final String extension = gallery.getFileExtension();

    return String.format("%s.%s", filename, extension);
  }
  private List<GalleryEntity> getPhotoList(){
    return getPhotoList();
  }
}
