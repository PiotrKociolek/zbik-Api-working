package pl.pkociolek.zbik.repository.entity;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pkociolek.zbik.model.CategoryVisibility;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "categories")
public class CategoryEntity {
  @Id private String id;
  private String name;
  private String displayName;
  private Set<String> postIds;
  private CategoryVisibility categoryVisibility;
}
