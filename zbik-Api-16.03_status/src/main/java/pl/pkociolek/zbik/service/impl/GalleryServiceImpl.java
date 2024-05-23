package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.CannotCreateUploadFolderException;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.exception.GalleryNotFoundException;
import pl.pkociolek.zbik.model.dtos.Gallery.ModifyGalleryDto;
import pl.pkociolek.zbik.model.dtos.request.CreatePostDto;
import pl.pkociolek.zbik.model.dtos.request.GalleryRequestDto;
import pl.pkociolek.zbik.repository.GalleryRepository;
import pl.pkociolek.zbik.repository.ImageRepository;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;
import pl.pkociolek.zbik.repository.entity.ImgEntity;
import pl.pkociolek.zbik.repository.entity.PostEntity;
import pl.pkociolek.zbik.service.GalleryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
//@Transactional
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {
  private final GalleryRepository galleryRepository;
  private final Path root = Paths.get("uploads");
  private final ModelMapper modelMapper;
  private final ImageRepository repo;
  private final GalleryRepository repository;




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
  public void addNewGallery(GalleryRequestDto dto, MultipartFile miniature, MultipartFile[] files) {
    uploadFolderExists();
    setGalleryDetails(dto,miniature,files);
  }
  private ImgEntity addSingleItem(MultipartFile file){
    final ImgEntity entity = new ImgEntity();
    final GalleryEntity gallery = new GalleryEntity();
    entity.setId(null);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    String cretionDate = dateFormat.format(new Date());
    entity.setObfuscatedFileName(generateUniqueFileName());
    String[] extension = file.getOriginalFilename().split("\\.");
    entity.setFileExtension(extension[extension.length-1]);
    gallery.setMiniatureId(entity.getSetTitleImgId());
    try {
      // final ImgEntity foo = addSingleItem(file);
      Files.copy(
              file.getInputStream(),
              this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(entity))),
              StandardCopyOption.REPLACE_EXISTING);

    }catch (final Exception e) {
      throw new FileAlreadyExistsException();
    }
    return repo.save(entity);
  }
  @Override
  public List<GalleryEntity> getAllPhotos() {
    return galleryRepository.findAll();
  }

  @Override
  public void modify(ModifyGalleryDto dto, MultipartFile file) {
    Optional<GalleryEntity> optionalGallery = galleryRepository.findById(dto.getId());}

   /* if (optionalGallery.isPresent()) {
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
         // gallery.getImgEntityGalleryList().add(updateGalleryDetails(file, dto));
        } catch (IOException e) {
          throw new FileAlreadyExistsException();
        }
      }

      galleryRepository.save(gallery);
    } else {
      throw new GalleryNotFoundException();
    }
  }*/

  private GalleryEntity setGalleryDetails(
          GalleryRequestDto dto, MultipartFile miniature, MultipartFile[] files) {
    final GalleryEntity entity = new GalleryEntity();
    entity.setId(null);
    entity.setTitle(dto.getTitle());

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    String cretionDate = dateFormat.format(new Date());
    entity.setCreationDateTime(cretionDate);
    Set<ImgEntity> imgEntitySet = new HashSet<>();

    for(int i =0; i< files.length; i++){
      MultipartFile file = files[i];
      ImgEntity entity1 =  addSingleItem(file);
      imgEntitySet.add(entity1);
    }
    /*Set<String> addItemList = Arrays.stream(files).map(this::addSingleItem)
            .map(ImgEntity::getId)
                  .collect(Collectors.toSet());*/
    ImgEntity addMiniature = addMiniatureImg(miniature);
    entity.setMiniatureId(addMiniature.getId());
    entity.setImgEntityGalleryList(imgEntitySet.stream().map(ImgEntity::getId).collect(Collectors.toSet()));
    repository.save(entity);
    return entity;
  }
  private ImgEntity addMiniatureImg(MultipartFile file){
    final ImgEntity entity = new ImgEntity();
    entity.setSetTitleImgId(null);
    entity.setObfuscatedFileName(generateUniqueFileName());
    String[] extension = file.getOriginalFilename().split("\\.");
    entity.setFileExtension(extension[extension.length-1]);
    try {
      final ImgEntity foo = addSingleItem(file);
      Files.copy(
              file.getInputStream(),
              this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(foo))),
              StandardCopyOption.REPLACE_EXISTING);

    }catch (final Exception e) {
      throw new FileAlreadyExistsException();
    }
    return entity;
  }
  private GalleryEntity updateGalleryDetails(
          final MultipartFile file, final ModifyGalleryDto dto) {
    final GalleryEntity galleryEntity = modelMapper.map(dto, GalleryEntity.class);
    galleryEntity.setId(null);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String cretionDate = dateFormat.format(new Date());
    galleryEntity.setCreationDateTime(cretionDate);
    galleryEntity.setTitle(dto.getTitle());
    galleryEntity.setObfuscatedFileName(generateUniqueFileName());
    String[] extension = file.getOriginalFilename().split("\\.");
    galleryEntity.setFileExtension(extension[extension.length - 1]);
    return galleryEntity;
  }

  private String getFileNameAndExtension(final ImgEntity entity) {
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
