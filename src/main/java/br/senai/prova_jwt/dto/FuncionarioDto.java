package br.senai.prova_jwt.dto;

import br.senai.prova_jwt.model.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDto {

    private Long id;
    private String nome;
    private Cargo cargo;
}
