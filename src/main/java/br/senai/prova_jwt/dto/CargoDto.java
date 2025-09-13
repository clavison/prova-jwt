package br.senai.prova_jwt.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoDto {

    private Long id;

    @NotBlank(message = "Nome do cargo é obrigatório")
    @Size(min = 2, max = 100, message = "Nome do cargo deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome do cargo deve conter apenas letras e espaços")
    private String nome;

    @NotNull(message = "Salário é obrigatório")
    @DecimalMin(value = "0.01", message = "Salário deve ser maior que zero")
    @DecimalMax(value = "999999.99", message = "Salário deve ser menor que 999.999,99")
    @Digits(integer = 6, fraction = 2, message = "Salário deve ter no máximo 6 dígitos inteiros e 2 decimais")
    private BigDecimal salario;
}
