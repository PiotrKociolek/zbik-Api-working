package pl.pkociolek.zbik.repository.entity;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pkociolek.zbik.model.Permission;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class RoleEntity {
  @Id private String id;
  private String name;
  private String displayName;
  private Set<Permission> permissions;
  private Set<String> userIds;
  private Boolean isBuiltIn;
  private Boolean isProtected;
  private Boolean isDefaultForGuest;
}
