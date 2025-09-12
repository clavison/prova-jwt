package br.senai.prova_jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret:myVerySecureSecretKeyThatIsLongEnoughForHS256Algorithm}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private Long expiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        if (keyBytes.length < 32) {
            byte[] newKeyBytes = new byte[32];
            System.arraycopy(keyBytes, 0, newKeyBytes, 0, keyBytes.length);
            keyBytes = newKeyBytes;
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        boolean expired = expiration.before(new Date());
        System.out.println("Token expirado? " + expired + ". Expira em: " + expiration);
        return expired;
    }

    public String generateToken(UserDetails userDetails) {
        System.out.println("Gerando token para: " + userDetails.getUsername());
        System.out.println("Com authorities: " + userDetails.getAuthorities());
        return createToken(userDetails);
    }

    private String createToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            boolean usernameMatches = username.equals(userDetails.getUsername());
            boolean notExpired = !isTokenExpired(token);

            return usernameMatches && notExpired;
        } catch (Exception e) {
            return false;
        }
    }
}
