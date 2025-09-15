package br.senai.prova_jwt.dto.filtros;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CargoFiltroDto {
    private String nome;
    private String descricao;
    private Double salarioMin;
    private Double salarioMax;
}