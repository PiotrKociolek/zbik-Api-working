package pl.pkociolek.zbik.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pkociolek.zbik.model.PostVisibility;
import pl.pkociolek.zbik.model.dtos.request.CreateOrUpdatePostDto;
import pl.pkociolek.zbik.model.dtos.response.PostResponseDto;

public interface PostService {

    void createPost(CreateOrUpdatePostDto createPostDto);

    void updatePost(CreateOrUpdatePostDto updatePostDto);

    void deletePost(String postId);

    PostResponseDto getPostById(String postId);

    Page<PostResponseDto> getAllPosts(Pageable pageable);

    Page<PostResponseDto> getPostsByVisibility( PostVisibility postVisibility, Pageable pageable);


  }


