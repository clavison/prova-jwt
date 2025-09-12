package br.senai.prova_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FuncionarioResponse {
    private Long id;
    private String nome;
    private String cargo;
    private Double salario;
}