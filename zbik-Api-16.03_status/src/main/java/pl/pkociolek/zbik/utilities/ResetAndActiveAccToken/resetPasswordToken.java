package pl.pkociolek.zbik.utilities.ResetAndActiveAccToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class resetPasswordToken {
    /*
        private static final String SECRET_KEY = "your-secret-key";
        private static final long EXPIRATION_TIME = 86400000; // 24 godziny w milisekundach


    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static String extractSubjectFromToken(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    public static boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static Jws<Claims> parseClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    } */
}
