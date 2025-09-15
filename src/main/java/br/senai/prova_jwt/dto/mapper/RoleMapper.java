package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.model.Role;

public class RoleMapper {

    public static RoleDto toDTO(Role role) {
        if (role == null) return null;
        return new RoleDto(role.getId(), role.getDescricao());
    }

    public static Role toEntity(Role dto) {
        if (dto == null) return null;
        return new Role(dto.getId(), dto.getDescricao());
    }
}
