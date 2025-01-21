package food_delivery.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import food_delivery.enumeration.ApplicationErrorEnum;
import food_delivery.exception.BusinessException;
import food_delivery.model.User;
import food_delivery.service.UserRoleService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Data
public class JwtUtils {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long expiration;

    @Value("${security.jwt.issuer}")
    private String issuer;

    private UserRoleService userRoleService;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userRoleService.getUserRoles(user.getId())
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("email" , user.getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setIssuer(issuer)
                .compact();

    }
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String extractIssuerFromToken(String token, ObjectMapper objectMapper) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                throw new BusinessException(ApplicationErrorEnum.INVALID_TOKEN);
            }
            String payload = new String(java.util.Base64.getDecoder().decode(parts[1]));
            JsonNode payloadJson = objectMapper.readTree(payload);
            return payloadJson.get("iss").asText();
        } catch (Exception e) {
            throw new BusinessException(ApplicationErrorEnum.INVALID_TOKEN);
        }
    }


}
