package br.senai.prova_jwt.domains.auth.role;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class RoleSpecification {

    public static Specification<Role> comFiltros(RoleFiltroDTO filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filtro.getDescricao() != null && !filtro.getDescricao().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("descricao")), "%" +
                        filtro.getDescricao().toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}