package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;

import java.util.stream.Collectors;

public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDto(
                usuario.getId(),
                usuario.getLogin(),
                null,
                usuario.getRoles().stream()
                        .map(Role::getNome)
                        .collect(Collectors.toSet())
        );
    }

    public static Usuario toEntity(UsuarioDto dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setLogin(dto.getLogin());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }
}
