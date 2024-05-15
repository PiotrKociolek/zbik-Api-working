package pl.pkociolek.zbik.model.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagementInfoDto {
    private String name;
    private String surname;
    private String mgmtRole;
    private String contact;
}