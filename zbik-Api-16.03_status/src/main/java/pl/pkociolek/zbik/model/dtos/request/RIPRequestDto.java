package pl.pkociolek.zbik.model.dtos.request;

import org.springframework.data.annotation.Id;

public class RIPRequestDto {
    @Id
    String Id;
    String name;
    String Surname;

}
