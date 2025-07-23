package dashspace.fun.car_rental_server.security;

import dashspace.fun.car_rental_server.config.prop.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static dashspace.fun.car_rental_server.security.JwtConstants.TOKEN_TYPE;

@Service
public class JwtHandler {

    private final JwtProperties jwtProperties;
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public JwtHandler(JwtProperties jwtProperties) throws Exception {
        this.jwtProperties = jwtProperties;
        this.publicKey = KeyUtils.loadPublicKey("keys/local-only/public_key.pem");
        this.privateKey = KeyUtils.loadPrivateKey("keys/local-only/private_key.pem");
    }

    public String generateAccessToken(String username) {
        Map<String, Object> claims = Map.of(JwtConstants.TOKEN_TYPE,
                JwtConstants.ACCESS_TOKEN);
        return buildToken(username, claims,
                this.jwtProperties.getAccessTokenExpirationMs());
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = Map.of(JwtConstants.TOKEN_TYPE,
                JwtConstants.REFRESH_TOKEN);
        return buildToken(username, claims,
                this.jwtProperties.getRefreshTokenExpirationMs());
    }

    private String buildToken(String subject, Map<String, Object> claims,
                              long expirationMs) {

        return Jwts.builder()
                .header().type("JWT").and()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .claims(claims)
                .signWith(this.privateKey)
                .compact();
    }

    public boolean isTokenValid(String token, String expectedUsername) {
        String username = extractUsername(token);
        return username.equals(expectedUsername) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(this.publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException ex) {
            throw new RuntimeException("Invalid token", ex);
        }
    }

    public String refreshAccessToken(String refreshToken) {
        Claims claims = extractClaims(refreshToken);
        validateRefreshToken(claims);
        return generateAccessToken(claims.getSubject());
    }

    private void validateRefreshToken(Claims claims) {
        if (!JwtConstants.REFRESH_TOKEN.equals(claims.get(TOKEN_TYPE))) {
            throw new RuntimeException("Invalid token type");
        }
        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("Token is expired");
        }
    }

    public Instant extractExpiration(String refreshToken) {
        Claims claims = extractClaims(refreshToken);
        return claims.getExpiration().toInstant();
    }
}
