package br.senai.prova_jwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioResponseDto {
    private Long id;
    private String login;
    private List<String> roles;

}
