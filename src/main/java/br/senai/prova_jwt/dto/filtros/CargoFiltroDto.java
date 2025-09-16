package br.senai.prova_jwt.dto.filtros;

import lombok.*;

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