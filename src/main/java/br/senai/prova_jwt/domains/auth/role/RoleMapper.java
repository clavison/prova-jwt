package br.senai.prova_jwt.domains.auth.role;

public class RoleMapper {

    public static RoleDTO toDto(Role role) {
        if (role == null) return null;
        return new RoleDTO(
                role.getId(),
                role.getDescricao()
        );
    }

    public static Role toEntity(RoleDTO dto) {
        if (dto == null) return null;
        Role role = new Role();
        role.setId(dto.getId());
        role.setDescricao(dto.getDescricao());
        return role;
    }
}