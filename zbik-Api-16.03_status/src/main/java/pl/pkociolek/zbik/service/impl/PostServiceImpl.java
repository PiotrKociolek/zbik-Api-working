package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.CannotCreateUploadFolderException;
import pl.pkociolek.zbik.exception.DatabaseEntityIsNotExistException;
import pl.pkociolek.zbik.exception.FileAlreadyExistsException;
import pl.pkociolek.zbik.exception.PostNotFoundException;
import pl.pkociolek.zbik.model.Visibility;
import pl.pkociolek.zbik.model.dtos.request.CreatePostDto;
import pl.pkociolek.zbik.model.dtos.request.UpdatePostDto;
import pl.pkociolek.zbik.model.dtos.response.PostResponseDto;
import pl.pkociolek.zbik.repository.PostRepository;
import pl.pkociolek.zbik.repository.entity.PostEntity;
import pl.pkociolek.zbik.service.PostImageService;
import pl.pkociolek.zbik.service.PostService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository repository;
  private final ModelMapper modelMapper;

  private final Path root = Paths.get("uploads");

  @Override
  public void createPost(final CreatePostDto dto,final MultipartFile file) {
    final PostEntity postEntity = modelMapper.map(dto, PostEntity.class);
    postEntity.setId(null);
    uploadFolderExists();
    try {
      final PostEntity entity = setPostDetails(dto, file);
      Files.copy(
              file.getInputStream(),
              this.root.resolve(Objects.requireNonNull(getTitleFileNameAndExtension(entity))),
              StandardCopyOption.REPLACE_EXISTING);


    }catch (final Exception e) {
      throw new FileAlreadyExistsException();
    }
    /*try{
      final PostEntity entity = setPostDetails(dto, file);
      Files.copy(
              file.getInputStream(),
              this.root.resolve(Objects.requireNonNull(getFileNameAndExtension(entity))),
              StandardCopyOption.REPLACE_EXISTING);
    }catch (final Exception e) {
      throw new FileAlreadyExistsException();
    }*/

    repository.save(postEntity);
  }
private PostEntity setPostDetails(final CreatePostDto dto,final MultipartFile file){
    final PostEntity entity = new PostEntity();
    entity.setId(null);
    entity.setTitle(dto.getTitle());
    entity.setContent(dto.getContent());

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    String cretionDate = dateFormat.format(new Date());
    entity.setCreationDateTime(cretionDate);
    entity.setTitleObfuscatedFileName(generateUniqueFileName());
    entity.setObfuscatedFileName(generateUniqueFileName());
  String[] extension = file.getOriginalFilename().split("\\.");
  entity.setTitleFileExtension(extension[extension.length-1]);
  entity.setFileExtension(extension[extension.length-1]);
  return entity;
}

  @Override
  public void updatePost(UpdatePostDto dto) {
    Optional<PostEntity> optionalPostEntity = repository.findById(dto.getId());
    PostEntity postEntity = optionalPostEntity.orElseThrow(DatabaseEntityIsNotExistException::new);
    postEntity.setTitle(dto.getTitle());
    postEntity.setContent(dto.getContent());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    String cretionDate = dateFormat.format(new Date());
    postEntity.setModificationDateTime(cretionDate);    repository.save(postEntity);
  }
  private static String generateUniqueFileName()
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String timestamp = dateFormat.format(new Date());
    return "file_" + timestamp ;
  }
  private String getFileNameAndExtension(final PostEntity entity) {
    final String filename = entity.getObfuscatedFileName();
    final String extension = entity.getFileExtension();

    return String.format("%s.%s", filename, extension);
  }
  private String getTitleFileNameAndExtension(final PostEntity entity){
    final String filename = entity.getTitleObfuscatedFileName();
    final String extension = entity.getTitleFileExtension();
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

  @Override
  public void deletePost(final String postId) {
    repository.deleteById(postId);
  }

  @Override
  public PostResponseDto getPostById(final String postId) {
    Optional<PostEntity> postEntityOptional = repository.findById(postId);

    if (postEntityOptional.isPresent()) {
      PostEntity postEntity = postEntityOptional.get();
      return modelMapper.map(postEntity, PostResponseDto.class);
    } else {
      throw new PostNotFoundException();
    }
  }

  @Override
  public Page<PostResponseDto> getAllPosts(final Pageable pageable) {
    Page<PostEntity> postEntityPage = repository.findAll(pageable);
    List<PostResponseDto> dtos = postEntityPage.getContent()
            .stream()
            .map(postEntity -> modelMapper.map(postEntity, PostResponseDto.class))
            .collect(Collectors.toList());
    return new PageImpl<>(dtos, pageable, postEntityPage.getTotalElements());
  }

  @Override
  public Page<PostResponseDto> getPostsByVisibility(
          final Visibility postVisibility, final Pageable pageable) {
    return repository
        .findAllByPostVisibility(postVisibility, pageable)
        .map(x -> modelMapper.map(x, PostResponseDto.class));
  }

}
