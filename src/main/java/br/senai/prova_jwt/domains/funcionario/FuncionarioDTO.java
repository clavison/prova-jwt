package br.senai.prova_jwt.domains.funcionario;

import br.senai.prova_jwt.domains.cargo.CargoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String nome;
    private CargoDTO cargo;
}
