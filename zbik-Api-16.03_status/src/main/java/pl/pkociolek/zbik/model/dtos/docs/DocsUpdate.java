package pl.pkociolek.zbik.model.dtos.docs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocsUpdate {

    private String Id;
    private String description;
    private String fileExtension;
    private String fileName;
    private String obfuscatedFileName;
}
