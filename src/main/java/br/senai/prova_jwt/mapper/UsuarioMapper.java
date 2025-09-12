package br.senai.prova_jwt.mapper;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;

import java.util.stream.Collectors;

public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getLogin());
        dto.setRoles(usuario.getRoles().stream()
                .map(Role::getNome)
                .collect(Collectors.toSet()));
        return dto;
    }
}
