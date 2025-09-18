package br.senai.prova_jwt.domain.role;

import br.senai.prova_jwt.helpers.bases.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements BaseMapper<Role, RoleDto> {

    @Override
    public RoleDto toDTO(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .descricao(role.getDescricao())
                .build();
    }

    @Override
    public Role toEntity(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .descricao(roleDto.getDescricao())
                .build();
    }
}
