package pl.pkociolek.zbik.service;

import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.dtos.docs.DocsUpdate;
import pl.pkociolek.zbik.model.dtos.request.DocsRequestDto;

public interface DocsService {
    void addDocs(MultipartFile file, DocsRequestDto dto);
    void update(DocsUpdate dto);
    void deleteById(String id);

}
