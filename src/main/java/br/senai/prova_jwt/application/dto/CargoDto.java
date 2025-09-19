package br.senai.prova_jwt.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CargoDto {
    private Long id;
    private String nome;
    private String descricao;
    private Double salario;

}