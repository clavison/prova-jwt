package br.senai.prova_jwt.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioDTO {
    private Long id;
    private String login;
    private List<String> roles;
}
