package pl.pkociolek.zbik.model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pkociolek.zbik.model.PostVisibility;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {
    private String id;
    private String content;
    private String title;
    private String userId;
    private Instant creationDateTime;
    private Instant modificationDateTime;
    private PostVisibility postVisibility;
}
