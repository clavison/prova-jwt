package br.senai.prova_jwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDto {

    private Long id;
    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username deve conter apenas letras, números, pontos, hífens e underscores")
    private String username;

    @NotBlank(message = "Password é obrigatório")
    @Size(min = 8, max = 100, message = "Password deve ter entre 8 e 100 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password deve conter pelo menos: 1 letra minúscula, 1 maiúscula, 1 número e 1 caractere especial"
    )
    private String password;

    @NotNull(message = "Roles não podem ser nulas")
    @NotEmpty(message = "Funcionário deve ter pelo menos uma role")
    @Size(max = 10, message = "Máximo de 10 roles por funcionário")
    private Set<String> roles;

    @NotNull(message = "Cargo é obrigatório")
    private CargoDto cargo;
}
