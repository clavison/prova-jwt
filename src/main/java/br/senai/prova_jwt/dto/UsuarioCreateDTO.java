package br.senai.prova_jwt.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioCreateDTO {
    private String login;
    private String senha;
    private List<String> roles; // nomes das roles a atribuir
}
