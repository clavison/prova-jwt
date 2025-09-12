package br.senai.prova_jwt.dto.mapper;



import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) return null;


        List<String> roles = usuario.getRoles() != null ?
                usuario.getRoles().stream().map(Role::getNome).collect(Collectors.toList()) : null;

        return new UsuarioDto(
                usuario.getId(),
                usuario.getUsername(),
                null, // Nunca retornar a senha no DTO
                roles
        );
    }

    public static Usuario toEntity(UsuarioDto dto, Set<Role> roles) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(dto.getPassword());
        usuario.setRoles(roles != null ? roles : new HashSet<>());

        return usuario;
    }

}