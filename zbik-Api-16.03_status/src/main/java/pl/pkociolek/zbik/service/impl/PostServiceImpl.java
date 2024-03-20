package pl.pkociolek.zbik.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pkociolek.zbik.exception.DatabaseEntityIsNotExistException;
import pl.pkociolek.zbik.exception.PostNotFoundException;
import pl.pkociolek.zbik.model.PostVisibility;
import pl.pkociolek.zbik.model.dtos.request.CreatePostDto;
import pl.pkociolek.zbik.model.dtos.request.UpdatePostDto;
import pl.pkociolek.zbik.model.dtos.response.PostResponseDto;
import pl.pkociolek.zbik.repository.PostRepository;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;
import pl.pkociolek.zbik.repository.entity.PostEntity;
import pl.pkociolek.zbik.service.PostService;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository repository;
  private final ModelMapper modelMapper;

  @Override
  public void createPost(final CreatePostDto createPostDto) {
    final PostEntity postEntity = modelMapper.map(createPostDto, PostEntity.class);
    postEntity.setId(null);
    repository.save(postEntity);
  }

  @Override
  public void updatePost(final UpdatePostDto updatePostDto) {
    final Optional<PostEntity> postEntity =repository.findById(updatePostDto.getId());
    postEntity.ifPresentOrElse(x->updatePostFunction(x,updatePostDto), DatabaseEntityIsNotExistException::new);
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
  private void updatePostFunction (PostEntity postEntity, UpdatePostDto updatePostDto){
    postEntity.setTitle(updatePostDto.getTitle());
    postEntity.setContent(updatePostDto.getContent());
    repository.save(postEntity);
  }

  private String generateFilename() {
    final byte[] array = new byte[32];
    new Random().nextBytes(array);
    return new String(array, StandardCharsets.UTF_8);
  }

  private String getFileNameANdExtension(final PostEntity gallery) {
    final String filename = gallery.getObfuscatedFileName();
    final String extension = gallery.getFileExtension();

    return String.format("%s.%s", filename, extension);
  }
}
