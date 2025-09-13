package br.senai.prova_jwt.domains.auth.usuario;

import br.senai.prova_jwt.domains.auth.role.Role;

import java.util.stream.Collectors;

public class UsuarioMapper {

    public static UsuarioDTO toDto(Usuario usuario) {
        if (usuario == null) return null;
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .username(usuario.getLogin())
                .roles(usuario.getRoles().stream()
                        .map(Role::getDescricao)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setLogin(dto.getUsername());
        // Nota: password e roles são tratados no service para criptografia e busca no BD
        return usuario;
    }
}