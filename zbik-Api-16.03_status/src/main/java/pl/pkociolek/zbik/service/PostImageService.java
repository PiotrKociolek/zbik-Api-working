package pl.pkociolek.zbik.service;

import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.CreateOrUpdatePostDto;
import pl.pkociolek.zbik.repository.entity.ImageEntity;

public interface PostImageService {
    void addPostImg(MultipartFile file, CreateOrUpdatePostDto dto);
    void deleteImage(String imageId);
    ImageEntity getImageById(String imageId);
}
