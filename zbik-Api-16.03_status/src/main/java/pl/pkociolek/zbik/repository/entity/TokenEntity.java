package pl.pkociolek.zbik.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pkociolek.zbik.model.TokenType;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tokens")
public class TokenEntity {

    @Id
    private String id;

    private String token;

    private TokenType tokenType;

    private Date expiryDate;
    private boolean isExpired;

}