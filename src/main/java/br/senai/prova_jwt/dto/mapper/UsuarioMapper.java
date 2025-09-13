package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.dto.UsuarioResponseDto;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario usuario) {
        if (usuario == null){
            return null;
        }
        return new UsuarioDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword(),
                Collections.singleton(usuario.getRoles().toString())
        );
    }

    public static Usuario toEntity(UsuarioDto usuarioDto) {
        if (usuarioDto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        Set<Role> roles = usuarioDto.getRoles().stream()
        .map(nome -> {
            Role role = new Role();
            role.setDescricao(nome);
            return role;
        })
        .collect(Collectors.toSet());
        usuario.setId(usuarioDto.getId());
        usuario.setUsername(usuarioDto.getUsername());
        usuario.setPassword(usuarioDto.getPassword());
        usuario.setRoles(roles);
        return usuario;
    }

    public static UsuarioResponseDto toResponseDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponseDto(
            usuario.getId(),
            usuario.getUsername(),
            usuario.getRoles()
                    .stream()
                    .map(Role::getDescricao)
                    .collect(Collectors.toSet())
        );
    }

}
