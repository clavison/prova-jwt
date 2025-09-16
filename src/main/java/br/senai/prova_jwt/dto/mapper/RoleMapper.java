package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.model.Role;

public class RoleMapper {

    public static RoleDto converterParaDto(Role role) {
        if (role == null)
            return null;
        RoleDto dto = new RoleDto();
        dto.setId(role.getIdRole());
        dto.setNomeRole(role.getNomeRole());
        return dto;
    }

    public static Role converterParaEntidade(RoleDto dto) {
        if (dto == null)
            return null;
        Role r = new Role();
        r.setIdRole(dto.getId());
        r.setNomeRole(dto.getNomeRole());
        return r;
    }

    // aliases
    public static RoleDto toDto(Role r) {
        return converterParaDto(r);
    }

    public static Role toEntity(RoleDto d) {
        return converterParaEntidade(d);
    }
}
