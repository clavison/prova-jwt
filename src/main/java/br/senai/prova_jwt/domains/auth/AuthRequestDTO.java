package br.senai.prova_jwt.domains.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class AuthRequestDTO {
    private String username;
    private String password;
}

