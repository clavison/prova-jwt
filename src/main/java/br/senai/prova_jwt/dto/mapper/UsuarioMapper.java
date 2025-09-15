package br.senai.prova_jwt.dto.mapper;

import br.senai.prova_jwt.dto.UsuarioCreateDto;
import br.senai.prova_jwt.dto.UsuarioResponseDto;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioCreateDto dto, List<Role> roles) {
        Usuario usuario = new Usuario();
        usuario.setLogin(dto.getLogin());
        usuario.setSenha(dto.getSenha());
        usuario.setRoles(roles);
        return usuario;
    }

    public static UsuarioResponseDto toDTO(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setLogin(usuario.getLogin());
        dto.setRoles(
                usuario.getRoles()
                        .stream()
                        .map(Role::getDescricao)
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
