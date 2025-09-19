package com.prova.rayssa.dto.mapper;

import com.prova.rayssa.dto.UsuarioDTO;
import com.prova.rayssa.model.Role;
import com.prova.rayssa.model.Usuario;
import com.prova.rayssa.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {
    @Autowired
    private RoleRepository roleRepository;

    public Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setLogin(dto.getLogin());
        usuario.setSenha(dto.getSenha());
        Set<Role> roles = dto.getRoles().stream()
            .map(roleRepository::findByDescricao)
            .filter(java.util.Objects::nonNull)
            .collect(Collectors.toSet());
        if (roles.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma role válida informada.");
        }
        usuario.setRoles(roles);
        return usuario;
    }
}
