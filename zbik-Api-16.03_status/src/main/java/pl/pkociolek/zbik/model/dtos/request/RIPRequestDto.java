package pl.pkociolek.zbik.model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RIPRequestDto {
    private String name;
    private String Surname;
}
