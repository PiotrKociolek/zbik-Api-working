package pl.pkociolek.zbik.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.Visibility;
import pl.pkociolek.zbik.model.dtos.request.CreatePostDto;
import pl.pkociolek.zbik.model.dtos.request.UpdatePostDto;
import pl.pkociolek.zbik.model.dtos.response.PostResponseDto;
import pl.pkociolek.zbik.repository.entity.PostEntity;

public interface PostService {

    void createPost( CreatePostDto dto, MultipartFile miniture,MultipartFile[] files);

    void updatePost(UpdatePostDto updatePostDto);

    void deletePost(String postId);

    PostEntity getPostById(String postId);

    Page<PostResponseDto> getAllPosts(Pageable pageable);

    Page<PostResponseDto> getPostsByVisibility(Visibility postVisibility, Pageable pageable);


  }


