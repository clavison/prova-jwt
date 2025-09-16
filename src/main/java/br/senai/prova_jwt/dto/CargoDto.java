package br.senai.prova_jwt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CargoDto {
    private Long id;
    private String nome;
    private String descricao;
    private Double salarioBase;
}
