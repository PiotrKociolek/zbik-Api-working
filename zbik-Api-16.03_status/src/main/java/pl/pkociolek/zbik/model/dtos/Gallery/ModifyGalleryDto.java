package pl.pkociolek.zbik.model.dtos.Gallery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import pl.pkociolek.zbik.model.Role;
import pl.pkociolek.zbik.repository.entity.GalleryEntity;

import java.time.Instant;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyGalleryDto {
    @Id
    private String Id;
    private String title;
    private Set<String> categoryId;
    private Instant createdAt;
    private Role role;
    private String fileExtension;
    private String fileName;
    private List<GalleryEntity> entityList;

}
