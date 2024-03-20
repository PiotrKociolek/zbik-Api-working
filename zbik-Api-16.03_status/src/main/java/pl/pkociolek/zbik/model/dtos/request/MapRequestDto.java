package pl.pkociolek.zbik.model.dtos.request;

import org.springframework.data.annotation.Id;

public class MapRequestDto {
  @Id private String id;
  private String description;
  private String fileName;
  private String extension;
}
