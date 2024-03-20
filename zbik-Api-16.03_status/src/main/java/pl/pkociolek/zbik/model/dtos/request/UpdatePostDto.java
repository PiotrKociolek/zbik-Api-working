package pl.pkociolek.zbik.model.dtos.request;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import pl.pkociolek.zbik.model.PostVisibility;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDto {

  @Id private String id;
  private String content;
  private String title;
  private String userId;
  private Instant modificationDateTime;
  private PostVisibility postVisibility;
}
