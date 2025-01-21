package food_delivery.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import food_delivery.enumeration.ApplicationErrorEnum;
import io.jsonwebtoken.Claims;
import org.springframework.security.oauth2.jwt.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

@Component
@Primary
public class JwtCustomUtils implements JwtDecoder{
    @Value("${security.jwt.issuer}")
    private String serverIssuer;

    private final JwtUtils jwtUtils;

    private final ObjectMapper objectMapper; // Inject the ObjectMapper

    @Autowired
    public JwtCustomUtils(JwtUtils jwtUtils, ObjectMapper objectMapper) {
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        String issuer = jwtUtils.extractIssuerFromToken(token , objectMapper);
        if (issuer.contains(serverIssuer)) {
            return decodeJwt(token);
        } else {
            throw new JwtException(ApplicationErrorEnum.INVALID_TOKEN.getMessage());
        }
    }


    private Jwt decodeJwt(String token) throws JwtException {
        if (jwtUtils.validateToken(token)) {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtUtils.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();

            return new Jwt(token, claims.getIssuedAt().toInstant(), claims.getExpiration().toInstant(), claims, claims);
        } else {
            throw new JwtException(ApplicationErrorEnum.INVALID_TOKEN.getMessage());
        }
    }
}
