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
import pl.pkociolek.zbik.repository.ImageRepository;
import pl.pkociolek.zbik.repository.PostRepository;
import pl.pkociolek.zbik.repository.entity.ImgEntity;
import pl.pkociolek.zbik.repository.entity.PostEntity;
import pl.pkociolek.zbik.service.PostService;

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
public class PostServiceImpl implements PostService {
  private final PostRepository repository;
  private final ModelMapper modelMapper;
private final ImageRepository repo;
  private final Path root = Paths.get("uploads");

  @Override
  public void createPost(CreatePostDto dto, MultipartFile miniature,MultipartFile[] files) {
    /*final PostEntity postEntity = modelMapper.map(dto, PostEntity.class);
    postEntity.setId(null);*/
    uploadFolderExists();
    setPostDetails(dto,miniature,files);
    //repository.save(postEntity);
  }
private PostEntity setPostDetails(final CreatePostDto dto,final MultipartFile miniature, final MultipartFile[] files){
    final PostEntity entity = new PostEntity();
    entity.setId(null);
    entity.setTitle(dto.getTitle());
    entity.setContent(dto.getContent());

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
    entity.setImgEntityList(imgEntitySet.stream().map(ImgEntity::getId).collect(Collectors.toSet()));
  repository.save(entity);
  return entity;
}
private ImgEntity addSingleItem( MultipartFile file){
    final ImgEntity entity = new ImgEntity();
    entity.setId(null);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    String cretionDate = dateFormat.format(new Date());
    entity.setObfuscatedFileName(generateUniqueFileName());
    String[] extension = file.getOriginalFilename().split("\\.");
    entity.setFileExtension(extension[extension.length-1]);
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
  private String getFileNameAndExtension(final ImgEntity entity) {
    final String filename = entity.getObfuscatedFileName();
    final String extension = entity.getFileExtension();

    return String.format("%s.%s", filename, extension);
  }
/*  private String getTitleFileNameAndExtension(final ImgEntity entity){
    final String filename = entity.getTitleObfuscatedFileName();
    final String extension = entity.getTitleFileExtension();
    return String.format("%s.%s", filename, extension);
  }*/
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
