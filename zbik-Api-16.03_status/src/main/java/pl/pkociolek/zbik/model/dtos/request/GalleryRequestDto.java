package pl.pkociolek.zbik.model.dtos.request;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import pl.pkociolek.zbik.model.Role;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GalleryRequestDto {
    @Id
    private String Id;
    private String title;
    private Instant createdAt;
    private String obfuscatedFileName;
    private String fileName;
    private Set<String> imgEntityList;
    private String setTitleImgId;
}

