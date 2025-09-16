package br.senai.prova_jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil() {
        String secret = "minha_chave_muito_secreta_que_deve_ser_long_aqui_32bytes_min";
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(UserDetails usuario) {
        long expiracaoMs = 1000L * 60 * 60;

        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .claim("perfis", usuario.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiracaoMs))
                .signWith(secretKey)
                .compact();
    }

    public String extrairUsuario(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        String username = extrairUsuario(token);
        return username != null && username.equals(userDetails.getUsername()) && !expirado(token);
    }

    private boolean expirado(String token) {
        try {
            Date exp = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return exp.before(new Date());
        } catch (Exception ex) {
            return true;
        }
    }
}