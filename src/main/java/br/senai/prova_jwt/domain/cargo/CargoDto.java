package br.senai.prova_jwt.domain.cargo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargoDto {

    private Long id;
    private String nome;
    private BigDecimal salario;

}
