package br.senai.prova_jwt.mapper;


import br.senai.prova_jwt.application.dto.RoleDto;
import br.senai.prova_jwt.domain.model.Role;

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