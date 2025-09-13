package br.senai.prova_jwt.dto.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoFilterDto {
    private String nome;
    private Double salarioMin;
    private Double salarioMax;

}
