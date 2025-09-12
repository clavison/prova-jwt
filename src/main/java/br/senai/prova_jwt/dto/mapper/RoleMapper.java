package br.senai.prova_jwt.dto.mapper;


import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.model.Role;

public class RoleMapper {

    public static RoleDto toDto(Role role) {
        if (role == null) return null;
        return new RoleDto(role.getId(), role.getNome());
    }

    public static Role toEntity(RoleDto dto) {
        if (dto == null) return null;
        Role role = new Role();
        role.setId(dto.getId());
        role.setNome(dto.getNome());
        return role;
    }
}
