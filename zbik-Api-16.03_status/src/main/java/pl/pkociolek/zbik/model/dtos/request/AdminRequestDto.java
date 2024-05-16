package pl.pkociolek.zbik.model.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import pl.pkociolek.zbik.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDto {

   // @Id
 //   private String id;
    @NotBlank
    @NotNull
    private String password;
    @NotBlank
    @NotNull
    @Email
    private String email;
}
