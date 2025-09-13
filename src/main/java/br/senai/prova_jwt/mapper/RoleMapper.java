package br.senai.prova_jwt.mapper;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public static Role toEntity(RoleDto dto) {
        if (dto == null) {
            return null;
        }

        Role role = new Role();
        role.setNome(dto.getNome());
        return role;
    }

    public static RoleDto toDto(Role role) {
        if (role == null) {
            return null;
        }

        return RoleDto.builder()
                .id(role.getId())
                .nome(role.getNome())
                .build();
    }
}
