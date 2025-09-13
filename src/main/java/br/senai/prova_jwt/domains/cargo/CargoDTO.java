package br.senai.prova_jwt.domains.cargo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO {
    private Long id;
    private String nome;
    private BigDecimal salario;
}
