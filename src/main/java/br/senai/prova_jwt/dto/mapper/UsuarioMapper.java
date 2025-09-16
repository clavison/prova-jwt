package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static UsuarioDto converterParaDto(Usuario usuario) {
        if (usuario == null)
            return null;

        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getIdUsuario());
        dto.setUsuario(usuario.getUsuario());
        dto.setSenha(null);
        dto.setPerfis(usuario.getPerfis() != null
                ? usuario.getPerfis().stream().map(Role::getNomeRole).collect(Collectors.toList())
                : null);
        return dto;
    }

    public static Usuario converterParaEntidade(UsuarioDto dto, Set<Role> papeis) {
        if (dto == null)
            return null;

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getId());
        usuario.setUsuario(dto.getUsuario());
        usuario.setSenha(dto.getSenha());
        usuario.setPerfis(papeis != null ? papeis : new HashSet<>());

        return usuario;
    }

    // aliases
    public static UsuarioDto toDto(Usuario usuario) {
        return converterParaDto(usuario);
    }

    public static Usuario toEntity(UsuarioDto dto, Set<Role> papeis) {
        return converterParaEntidade(dto, papeis);
    }
}
