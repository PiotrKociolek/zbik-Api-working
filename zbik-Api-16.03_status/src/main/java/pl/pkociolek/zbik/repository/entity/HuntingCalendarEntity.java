package pl.pkociolek.zbik.repository.entity;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "huntingCalendar")
public class HuntingCalendarEntity {
  @Id private String id;
  private String animalSpecies;
  private String description;
  private Instant modificationDate;

  private String fileExtension;
  private String fileName;
  private String obfuscatedFileName;

}
