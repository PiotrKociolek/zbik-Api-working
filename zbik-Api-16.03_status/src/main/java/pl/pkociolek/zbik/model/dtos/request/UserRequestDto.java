package pl.pkociolek.zbik.model.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @Id
    private String id;
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
    @NotNull
    private Boolean activated;
    @NotNull
    private Boolean blocked;
    @NotBlank
    @NotNull
    private String signedPolicyId;

    private Instant lastLogin;
    private Instant joinDate;
    private Instant tokenIssueDate;




}
