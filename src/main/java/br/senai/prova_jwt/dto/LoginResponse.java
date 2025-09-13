package br.senai.prova_jwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    @NotBlank(message = "Token não pode estar vazio")
    private String token;
}
