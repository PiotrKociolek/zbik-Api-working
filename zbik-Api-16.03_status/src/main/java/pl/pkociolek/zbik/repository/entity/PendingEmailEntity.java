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
@Document(collection = "pendingEmails")
public class PendingEmailEntity {
  @Id private String id;
  private String recipient;
  private String header;
  private String topic;
  private String content;
  private Instant creatingDateTime;
  private String template;
  private Boolean alreadySent;
}
