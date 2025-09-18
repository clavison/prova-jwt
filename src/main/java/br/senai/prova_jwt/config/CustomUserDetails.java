package br.senai.prova_jwt.config;

import br.senai.prova_jwt.domain.usuario.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id;

    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Usuario usuario,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.authorities = authorities;
    }

}