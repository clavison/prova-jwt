package br.senai.prova_jwt.domains.cargo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CargoFiltroDTO {
    private String nome;
    private BigDecimal salarioMin;
    private BigDecimal salarioMax;
}
