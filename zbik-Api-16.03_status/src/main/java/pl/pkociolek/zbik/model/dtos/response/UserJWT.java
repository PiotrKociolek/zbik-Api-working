package pl.pkociolek.zbik.model.dtos.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJWT {
    private String id;
    private String email;
    private Instant expirationDate;
}
