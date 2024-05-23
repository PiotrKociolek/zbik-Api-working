package pl.pkociolek.zbik.repository.entity;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pkociolek.zbik.model.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {
  @Id private String id;
  private String username;
  private String name;
  private String surname;
  private String phoneNumber;
  private String email;
  private String password;
  private Role role;
  private Boolean activated = false;
  private Boolean blocked = false;
  private Instant lastLoginDateTime;
  private Instant registrationDateTime;
  private Instant tokenIssueDateTime;
  private String resetToken;
  private String verifyAccToken;


}
