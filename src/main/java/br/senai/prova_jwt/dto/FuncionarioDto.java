package br.senai.prova_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDto {
    private Long id;

    @NotEmpty(message = "O nome do funcionário não pode ser vazio.")
    @Size(min = 3, max = 150, message = "O nome do funcionário deve ter entre 3 e 150 caracteres.")
    private String nome;

    @NotNull(message = "O ID do cargo não pode ser nulo.")
    private Long cargoId;

}
