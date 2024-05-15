package pl.pkociolek.zbik.model.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import pl.pkociolek.zbik.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {


    @NotBlank
    @NotNull
    //!!@ValidPassword
    private String password;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String surname;
    @NotBlank
    @NotNull
    @Email
    private String email;

/*    @NotNull
    private Role role;*/
 /*   @NotNull
    private Boolean blocked;*/

 //   @NotBlank
   // @NotNull
    //private String signedPolicyId;

}
