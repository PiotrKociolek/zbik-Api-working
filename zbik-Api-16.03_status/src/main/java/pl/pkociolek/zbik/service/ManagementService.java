package pl.pkociolek.zbik.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import pl.pkociolek.zbik.model.dtos.request.ManagementImgDto;
import pl.pkociolek.zbik.model.dtos.response.ManagementInfoDto;
@Service
@Transactional
public interface ManagementService {

    void deleteFromManagement(String id);
    void addToManagement(ManagementInfoDto dto);
    void addMgmtImg(MultipartFile file, ManagementImgDto mgmt);



}
