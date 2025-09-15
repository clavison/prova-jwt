package br.senai.prova_jwt.dto.filtros;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioFiltroDto {
    private String nome;
    private String email;
    private String telefone;
    private Long cargoId;
    private String cargoNome; // Para filtrar por nome do cargo
}