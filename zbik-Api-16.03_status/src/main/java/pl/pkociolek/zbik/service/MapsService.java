package pl.pkociolek.zbik.service;

import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.CreatePostDto;
import pl.pkociolek.zbik.model.dtos.request.MapRequestDto;

public interface MapsService {
    void save(MultipartFile file, MapRequestDto mapRequestDto);
    void deleteMapById(String id);
    void addDescription(MapRequestDto mapRequestDto);
}