package pl.pkociolek.zbik.repository.entity;

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
@Document(collection = "management")
public class ManagementEntity {
    @Id private  String Id;
    private String name;
    private String surname;
    private String role;
    private String contact;
    /*--------------------------*/
    private String fileExtension;
    private String fileName;
    private String obfuscatedFileName;
}
