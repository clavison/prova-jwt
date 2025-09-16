package br.senai.prova_jwt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDto {
    private Long id;
    private String nomeCompleto;
    private String emailContato;
    private String telefone;
    private Long cargoId;
    private String cargoNome;
}
