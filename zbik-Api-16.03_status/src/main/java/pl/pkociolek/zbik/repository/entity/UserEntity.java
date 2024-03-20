package pl.pkociolek.zbik.repository.entity;

import java.time.Instant;
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
@Document(collection = "users")
public class UserEntity {
  @Id private String id;
  private String username;
  private String name;
  private String surname;
  private String phoneNumber;
  private String emailAddress;
  private String password;
  private Enum Role;
  private Boolean activated;
  private Boolean blocked;
  private Instant lastLoginDateTime;
  private Instant registrationDateTime;
  private Instant tokenIssueDateTime;
  private String resetToken;
  private String verifyAccToken;


}
