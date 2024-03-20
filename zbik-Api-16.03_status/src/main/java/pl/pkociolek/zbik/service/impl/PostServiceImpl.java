package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.exception.DatabaseEntityIsNotExistException;
import pl.pkociolek.zbik.exception.PostNotFoundException;
import pl.pkociolek.zbik.model.PostVisibility;
import pl.pkociolek.zbik.model.dtos.request.CreateOrUpdatePostDto;
import pl.pkociolek.zbik.model.dtos.response.PostResponseDto;
import pl.pkociolek.zbik.repository.PostRepository;
import pl.pkociolek.zbik.repository.entity.ImageEntity;
import pl.pkociolek.zbik.repository.entity.PostEntity;
import pl.pkociolek.zbik.service.PostImageService;
import pl.pkociolek.zbik.service.PostService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository repository;
  private final ModelMapper modelMapper;
  private final PostImageService postImageService;


  @Override
  public void createPost(final CreateOrUpdatePostDto createOrUpdatePostDto) {
    PostEntity postEntity = modelMapper.map(createOrUpdatePostDto, PostEntity.class);
    postEntity.setId(null);

    // Zapisanie treści posta
    if (createOrUpdatePostDto.getContent() != null) {
      postEntity.setContent(createOrUpdatePostDto.getContent());
    }

    // Zapisanie zdjęć
    if (createOrUpdatePostDto.getImages() != null && !createOrUpdatePostDto.getImages().isEmpty()) {
      for (MultipartFile file : createOrUpdatePostDto.getImages()) {
        postImageService.addPostImg(file, createOrUpdatePostDto);
      }
    }

    repository.save(postEntity);
  }



  @Override
  public void updatePost(final CreateOrUpdatePostDto createOrUpdatePostDto) {
    Optional<PostEntity> optionalPostEntity = repository.findById(createOrUpdatePostDto.getId());
    PostEntity postEntity = optionalPostEntity.orElseThrow(DatabaseEntityIsNotExistException::new);

    if (createOrUpdatePostDto.getContent() != null) {
      postEntity.setContent(createOrUpdatePostDto.getContent());
    }

    if (createOrUpdatePostDto.getImages() != null && !createOrUpdatePostDto.getImages().isEmpty()) {
      postEntity.getImages().clear();
      for (MultipartFile file : createOrUpdatePostDto.getImages()) {
        postImageService.addPostImg(file, createOrUpdatePostDto);
      }
    }

    repository.save(postEntity);
  }




  @Override
  public void deletePost(final String postId) {
    repository.deleteById(postId);
  }

  @Override
  public PostResponseDto getPostById(final String postId) {
    return repository
        .findById(postId)
        .map(x -> modelMapper.map(x, PostResponseDto.class))
        .orElseThrow(PostNotFoundException::new);
  }

  @Override
  public Page<PostResponseDto> getAllPosts(final Pageable pageable) {
    return repository.findAll(pageable).map(x -> modelMapper.map(x, PostResponseDto.class));
  }

  @Override
  public Page<PostResponseDto> getPostsByVisibility(
      final PostVisibility postVisibility, final Pageable pageable) {
    return repository
        .findAllByPostVisibility(postVisibility, pageable)
        .map(x -> modelMapper.map(x, PostResponseDto.class));
  }

}
