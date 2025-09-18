package br.senai.prova_jwt.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String senha;
}
