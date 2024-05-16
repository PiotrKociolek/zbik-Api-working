package pl.pkociolek.zbik.model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.Visibility;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDto {
    private String id;
    private String content;
    private String title;
    private String userId;
    private Instant creationDateTime;
    private Instant modificationDateTime;
    private Visibility postVisibility;
    private List<MultipartFile> images;
}


