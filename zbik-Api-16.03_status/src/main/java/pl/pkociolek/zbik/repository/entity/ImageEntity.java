package pl.pkociolek.zbik.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "images")
public class ImageEntity {
    @Id
    private String id;
    private String fileExtension;
    private String fileName;
    private String obfuscatedFileName;
    private String postId;
}
