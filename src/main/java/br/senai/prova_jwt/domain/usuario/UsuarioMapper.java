package br.senai.prova_jwt.domain.usuario;

import br.senai.prova_jwt.domain.role.RoleMapper;
import br.senai.prova_jwt.helpers.bases.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UsuarioMapper implements BaseMapper<Usuario, UsuarioDto> {

    private final RoleMapper roleMapper;

    public UsuarioMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public UsuarioDto toDTO(Usuario usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRoles()
                        .stream()
                        .map(roleMapper::toDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Usuario toEntity(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .id(usuarioDto.getId())
                .username(usuarioDto.getUsername())
                .password(usuarioDto.getPassword())
                .roles(usuarioDto.getRoles()
                        .stream()
                        .map(roleMapper::toEntity)
                        .collect(Collectors.toSet()))
                .build();
    }
}
