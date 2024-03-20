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
@Document(collection = "ResetPassAndActiveAcc")
public class ResetPasswordAndActiveAccEntity {
    @Id
    private String id;
    private String UserId;
    private String UserAddress;

    private String activationLink;

    private String resetLink;

}
