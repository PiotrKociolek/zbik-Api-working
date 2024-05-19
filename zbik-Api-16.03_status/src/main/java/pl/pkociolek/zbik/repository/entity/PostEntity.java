package pl.pkociolek.zbik.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pkociolek.zbik.model.Visibility;

import java.util.List;
import java.util.Set;

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
  private String creationDateTime;
  private String modificationDateTime;
  private Visibility postVisibility;
  private Set<String> imgEntityList;
  private String miniatureId;

}
