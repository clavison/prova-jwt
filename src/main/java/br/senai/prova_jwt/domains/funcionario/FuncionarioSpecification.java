package com.erp.funcionariocargo.domains.funcionario;

import com.erp.funcionariocargo.domains.cargo.Cargo;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Join;

public class FuncionarioSpecification {

    public static Specification<Funcionario> comFiltros(FuncionarioFiltroDTO filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("nome")), "%" +
                        filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getNomecargo() != null && !filtro.getNomecargo().isEmpty()) {
                Join<Funcionario, Cargo> cargoJoin = root.join("cargo");
                predicate = cb.and(predicate, cb.like(cb.lower(cargoJoin.get("nome")), "%" +
                        filtro.getNomecargo().toLowerCase() + "%"));
            }

            if (filtro.getSalarioMin() != null) {
                Join<Funcionario, Cargo> cargoJoin = root.join("cargo");
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cargoJoin.get("salario"), filtro.getSalarioMin()));
            }

            if (filtro.getSalarioMax() != null) {
                Join<Funcionario, Cargo> cargoJoin = root.join("cargo");
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(cargoJoin.get("salario"), filtro.getSalarioMax()));
            }

            return predicate;
        };
    }
}
