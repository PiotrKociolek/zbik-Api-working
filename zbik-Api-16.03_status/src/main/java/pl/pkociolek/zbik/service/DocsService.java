package pl.pkociolek.zbik.service;

import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.request.DocsRequestDto;
import pl.pkociolek.zbik.model.dtos.request.ManagementImgDto;

public interface DocsService {
    void addDesc (DocsRequestDto dto);
    void save(MultipartFile file, DocsRequestDto dto);
    void editDescription(DocsRequestDto dto);
    void deleteById(String id);

}
