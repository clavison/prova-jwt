package br.senai.prova_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioFiltroDto {

    private String nome;
    private BigDecimal salarioMinimo;
    private BigDecimal salarioMaximo;
    private String username;
}
