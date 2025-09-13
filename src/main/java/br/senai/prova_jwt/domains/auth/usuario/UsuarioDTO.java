package br.senai.prova_jwt.domains.auth.usuario;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String username;
    private String password;

    private Set<String> roles;
}
