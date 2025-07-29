package dashspace.fun.car_rental_server.infrastructure.service;

import dashspace.fun.car_rental_server.infrastructure.config.prop.JwtProperties;
import dashspace.fun.car_rental_server.infrastructure.constant.ClaimParams;
import dashspace.fun.car_rental_server.infrastructure.constant.DefaultValue;
import dashspace.fun.car_rental_server.infrastructure.constant.KeyPaths;
import dashspace.fun.car_rental_server.infrastructure.constant.TokenType;
import dashspace.fun.car_rental_server.infrastructure.util.KeyUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.publicKey = KeyUtil.loadPublicKey(KeyPaths.LOCAL_PUBLIC_KEY);
        this.privateKey = KeyUtil.loadPrivateKey(KeyPaths.LOCAL_PRIVATE_KEY);
    }

    public String generateAccessToken(String username) {
        Map<String, Object> claims = Map.of(ClaimParams.TOKEN_TYPE, TokenType.ACCESS_TOKEN.getType());
        return buildToken(username, claims, jwtProperties.getAccessTokenExpirationMs());
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = Map.of(ClaimParams.TOKEN_TYPE, TokenType.REFRESH_TOKEN.getType());
        return buildToken(username, claims, jwtProperties.getRefreshTokenExpirationMs());
    }

    private String buildToken(String subject, Map<String, Object> claims, long expirationMs) {
        var expiresAt = new Date(System.currentTimeMillis() + expirationMs);
        return Jwts.builder()
                .header().type(DefaultValue.JWT).and()
                .subject(subject)
                .claims(claims)
                .expiration(expiresAt)
                .signWith(privateKey)
                .issuedAt(new Date())
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

    @SneakyThrows
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String refreshAccessToken(String refreshToken) {
        Claims claims = extractClaims(refreshToken);
        verifyRefreshToken(claims);
        return generateAccessToken(claims.getSubject());
    }

    private void verifyRefreshToken(Claims claims) {
        if (isRefreshToken(claims)) {
            throw new RuntimeException("Invalid token type");
        }

        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("Token is expired");
        }
    }

    private boolean isRefreshToken(Claims claims) {
        return claims.get(ClaimParams.TOKEN_TYPE) == TokenType.REFRESH_TOKEN.getType();
    }

    public Instant extractExpiration(String refreshToken) {
        Claims claims = extractClaims(refreshToken);
        return claims.getExpiration().toInstant();
    }
}
