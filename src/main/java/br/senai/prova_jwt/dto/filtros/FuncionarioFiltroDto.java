package br.senai.prova_jwt.dto.filtros;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioFiltroDto {
    private String nome;
    private String email;
    private String telefone;
    private Long cargoId;
    private String cargoNome;
}