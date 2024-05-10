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
import pl.pkociolek.zbik.model.dtos.request.CreatePostDto;
import pl.pkociolek.zbik.model.dtos.request.UpdatePostDto;
import pl.pkociolek.zbik.model.dtos.response.PostResponseDto;
import pl.pkociolek.zbik.repository.PostRepository;
import pl.pkociolek.zbik.repository.entity.PostEntity;
import pl.pkociolek.zbik.service.PostImageService;
import pl.pkociolek.zbik.service.PostService;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository repository;
  private final ModelMapper modelMapper;
  private final PostImageService postImageService;


  @Override
  public void createPost(final CreatePostDto createPostDto) {
    PostEntity postEntity = modelMapper.map(createPostDto, PostEntity.class);
    postEntity.setId(null);

    // Zapisanie treści posta
    if (createPostDto.getContent() != null) {
      postEntity.setContent(createPostDto.getContent());
    }

    // Zapisanie zdjęć
    if (createPostDto.getImages() != null && !createPostDto.getImages().isEmpty()) {
      for (MultipartFile file : createPostDto.getImages()) {
        postImageService.addPostImg(file, createPostDto);
      }
    }

    repository.save(postEntity);
  }

  @Override
  public void updatePost(UpdatePostDto updatePostDto) {
    Optional<PostEntity> optionalPostEntity = repository.findById(updatePostDto.getId());
    PostEntity postEntity = optionalPostEntity.orElseThrow(DatabaseEntityIsNotExistException::new);

    if (updatePostDto.getContent() != null) {
      postEntity.setContent(updatePostDto.getContent());
    }
    if (updatePostDto.getImages() != null && !updatePostDto.getImages().isEmpty()) {
      postEntity.getImages().clear();
      for (MultipartFile file : updatePostDto.getImages()) {
        postImageService.updatePostImg(file, updatePostDto);
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
