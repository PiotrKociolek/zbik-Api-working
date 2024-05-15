package pl.pkociolek.zbik.model.dtos.user;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String id;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Boolean activated;
    private Boolean blocked;
    private String signedPolicyId;
    private Instant lastLogin;
    private Instant joinDate;
    private Instant tokenIssueDate;
}
