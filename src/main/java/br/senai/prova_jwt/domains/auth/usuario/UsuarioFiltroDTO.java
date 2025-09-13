package br.senai.prova_jwt.domains.auth.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFiltroDTO {
    private String username;
    private String role;
}