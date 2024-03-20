package pl.pkociolek.zbik.service;

import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.GalleryRequestDto;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;

import java.util.List;

public interface GalleryService {
    void addNewGallery(MultipartFile file, GalleryRequestDto galleryDto);
    void deleteById(String id);
    void modify(GalleryRequestDto galleryRequestDto, MultipartFile file);
    List<GalleryEntity> getAllPhotos();

}
