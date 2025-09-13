package br.senai.prova_jwt.dto.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioFilterDto {
    private String nome;
    private String cargo;
}
