package br.senai.prova_jwt.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UsuarioDto {
    private Long id;
    private String username;
    private Set<String> roles;
}
