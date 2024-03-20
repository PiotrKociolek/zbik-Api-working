package pl.pkociolek.zbik.model.dtos.request;

import org.springframework.data.annotation.Id;

public class CalendarRequestDto {
  @Id private String id;
  private String fileExtension;
  private String fileName;
}
