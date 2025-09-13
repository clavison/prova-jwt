package br.senai.prova_jwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, message = "Username deve ter entre 3 e 50 caracteres")
    private String username;

    @NotBlank(message = "Password é obrigatório")
    @Size(min = 1, message = "Password deve ter entre 1 e 100 caracteres")
    private String password;
}
