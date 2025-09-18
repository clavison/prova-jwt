package br.senai.prova_jwt.domain.funcionario;

import br.senai.prova_jwt.domain.cargo.CargoDto;
import br.senai.prova_jwt.domain.usuario.UsuarioDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionarioDto {

    private Long id;
    private String nome;
    private CargoDto cargo;
    private UsuarioDto usuario;

}
