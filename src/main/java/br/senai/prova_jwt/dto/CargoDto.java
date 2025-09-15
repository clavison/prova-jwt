package br.senai.prova_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoDto {
    private Long id;

    @NotEmpty(message = "O nome do cargo não pode ser vazio.")
    @Size(min = 3, max = 100, message = "O nome do cargo deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotNull(message = "O salário não pode ser nulo.")
    @Positive(message = "O salário deve ser um valor positivo.")
    private double salario;

    private List<FuncionarioDto> funcionarios;

}
