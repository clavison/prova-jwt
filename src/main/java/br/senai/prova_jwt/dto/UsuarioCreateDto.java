package br.senai.prova_jwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioCreateDto {
    private String login;
    private String senha;
    private List<String> roles;

}
