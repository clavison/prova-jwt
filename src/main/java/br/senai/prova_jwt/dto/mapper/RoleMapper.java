package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.model.Role;

public class RoleMapper {

    public static RoleDto toDto(Role role) {
        if (role == null){
            return null;
        }
        return new RoleDto(
                role.getId(),
                role.getDescricao()
        );
    }

    public static Role toEntity(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }
        Role role = new Role();
        role.setId(role.getId());
        role.setDescricao(role.getDescricao());
        return role;
    }

}
