package br.senai.prova_jwt.utils;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class RolesUtil {

    private final RoleRepository roleRepository;

    public RolesUtil(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> validarRoles(Set<String> roles) {
        Set<Role> rolesValidadas = new HashSet<>();

        roles.forEach(roleDto -> {
            Optional<Role> role = roleRepository.findByNome(roleDto);

            if (role.isEmpty()) {
                throw new IllegalArgumentException("Não foi encontrado a role vinculada: " + roleDto);
            }

            rolesValidadas.add(role.get());
        });

        return rolesValidadas;
    }
}
