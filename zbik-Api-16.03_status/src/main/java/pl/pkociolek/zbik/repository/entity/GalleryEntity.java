package pl.pkociolek.zbik.repository.entity;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pkociolek.zbik.model.Visibility;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "gallery")
public class GalleryEntity {

    @Id private String id;
    private String title;
    private Set<String> categoryId;
    private String creationDateTime;
    private String fileExtension;
    private String fileName;
    private String obfuscatedFileName;
    private Set<String> imgEntityGalleryList;
    private String miniatureId;
}
