package pl.pkociolek.zbik.repository.entity;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pkociolek.zbik.model.PostVisibility;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class PostEntity {
  @Id private String id;
  private String content;
  private String title;
  private String userId;
  private Instant creationDateTime;
  private Instant modificationDateTime;
  private PostVisibility postVisibility;
  private String fileName;
  private String obfuscatedFileName;
  private String fileExtension;
}
