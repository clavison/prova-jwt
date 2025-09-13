package br.senai.prova_jwt.domains.funcionario;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FuncionarioFiltroDTO {
    private String nome;
    private String nomecargo;
    private BigDecimal salarioMin;
    private BigDecimal salarioMax;
}