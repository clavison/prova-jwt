package br.senai.prova_jwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UsuarioDto {
    private String username;
    private String password;
    private Set<String> roles;
}
