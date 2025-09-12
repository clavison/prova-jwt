package br.senai.prova_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CargoFiltroDto {

    private String nome;
    private BigDecimal salarioMinimo;
    private BigDecimal salarioMaximo;
}
