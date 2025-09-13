package br.senai.prova_jwt.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private String username;
    private String password;

    private Set<String> roles;
}
