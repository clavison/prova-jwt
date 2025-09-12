package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRoles()
        );
    }

    public static Usuario toEntity(UsuarioDto usuarioDto) {
        if (usuarioDto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDto.getId());
        usuario.setNome(usuarioDto.getNome());
        return usuario;
    }
}
