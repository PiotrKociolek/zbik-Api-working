package pl.pkociolek.zbik.model.dtos.request;

import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import pl.pkociolek.zbik.model.Role;

@Getter
@Setter
@AllArgsConstructor
public class GalleryRequestDto {
    @Id
    private String Id;
    private String title;
    private Set<String> categoryId;
    private Instant createdAt;
    private Role role;
    private String fileExtension;
    private String fileName;

}

