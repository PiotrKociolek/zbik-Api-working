package pl.pkociolek.zbik.model.dtos.members;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembersUpdateDto {
    @Id private String id;
    private String name;
    private String surname;
}
