package com.prova.rayssa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioFiltroDTO {
    private String nome;
    private String cargoNome;
    private Long cargoId;
    private BigDecimal salarioMinimo;
    private BigDecimal salarioMaximo;
}