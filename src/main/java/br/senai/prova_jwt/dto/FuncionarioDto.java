package br.senai.prova_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDto {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Long cargoId; // referência ao cargo


}