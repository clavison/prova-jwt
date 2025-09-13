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
        dto.setLogin(usuario.getLogin());
        dto.setSenha(usuario.getSenha());
        dto.setRoles(usuario.getRoles().stream()
                .map(Role::getNome)
                .collect(Collectors.toSet()));
        return dto;
    }

    public static Usuario toEntity(UsuarioDto usuarioDto) {
        if (usuarioDto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(usuarioDto.getId());
        usuario.setLogin(usuarioDto.getLogin());
        usuario.setSenha(usuarioDto.getSenha());

        return usuario;
    }
}
