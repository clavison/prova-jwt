package br.senai.prova_jwt.application.dto.filtros;

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
    private String cargoNome;

    public boolean hasFilters() {
        return nome != null || email != null || telefone != null ||
                cargoId != null || cargoNome != null;
    }
}