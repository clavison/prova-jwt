package br.senai.prova_jwt.domain.usuario;

import br.senai.prova_jwt.domain.role.RoleDto;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto {

    private Long id;
    private String username;
    private String password;
    private Set<RoleDto> roles;

}
