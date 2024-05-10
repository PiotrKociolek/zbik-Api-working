package pl.pkociolek.zbik.service;

import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.CreatePostDto;
import pl.pkociolek.zbik.model.dtos.request.UpdatePostDto;
import pl.pkociolek.zbik.repository.entity.ImageEntity;

public interface PostImageService {
    void addPostImg(MultipartFile file, CreatePostDto dto);
    void updatePostImg(MultipartFile file, UpdatePostDto dto);
    void deleteImage(String imageId);
    ImageEntity getImageById(String imageId);
}
