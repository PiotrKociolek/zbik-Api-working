package pl.pkociolek.zbik.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pkociolek.zbik.model.PostVisibility;
import pl.pkociolek.zbik.model.dtos.request.CreatePostDto;
import pl.pkociolek.zbik.model.dtos.request.UpdatePostDto;
import pl.pkociolek.zbik.model.dtos.response.PostResponseDto;
import pl.pkociolek.zbik.repository.entity.PostEntity;

public interface PostService {

    void createPost(CreatePostDto createPostDto);

    void updatePost(UpdatePostDto updatePostDto);

    void deletePost(String postId);

    PostResponseDto getPostById(String postId);

    Page<PostResponseDto> getAllPosts(Pageable pageable);

    Page<PostResponseDto> getPostsByVisibility( PostVisibility postVisibility, Pageable pageable);


  }


