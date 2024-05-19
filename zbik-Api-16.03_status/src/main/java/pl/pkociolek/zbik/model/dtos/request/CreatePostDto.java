package pl.pkociolek.zbik.model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pkociolek.zbik.model.Visibility;
import pl.pkociolek.zbik.repository.entity.ImgEntity;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {
    private String content;
    private String title;
    private String userId;
    private Instant creationDateTime;

    private String fileExtension;
    private String obfuscatedFileName;
    private String titleObfuscatedFileName;
    private String titleFileExtension;
    private Set<String> imgEntityList;
    private String setTitleImgId;
}
