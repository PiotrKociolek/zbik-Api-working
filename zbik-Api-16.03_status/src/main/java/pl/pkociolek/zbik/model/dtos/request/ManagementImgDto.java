package pl.pkociolek.zbik.model.dtos.request;

import org.springframework.data.annotation.Id;

public class ManagementImgDto {
    @Id private String id;
    private String filename;
    private String fileExtension;
}
