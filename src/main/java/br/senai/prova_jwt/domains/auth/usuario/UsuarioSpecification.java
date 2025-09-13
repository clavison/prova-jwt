package br.senai.prova_jwt.domains.auth.usuario;

import br.senai.prova_jwt.domains.auth.role.Role;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Join;

public class UsuarioSpecification {

    public static Specification<Usuario> comFiltros(UsuarioFiltroDTO filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filtro.getUsername() != null && !filtro.getUsername().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("login")), "%" +
                        filtro.getUsername().toLowerCase() + "%"));
            }

            if (filtro.getRole() != null && !filtro.getRole().isEmpty()) {
                Join<Usuario, Role> roleJoin = root.join("roles");
                predicate = cb.and(predicate, cb.like(cb.lower(roleJoin.get("descricao")), "%" +
                        filtro.getRole().toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}