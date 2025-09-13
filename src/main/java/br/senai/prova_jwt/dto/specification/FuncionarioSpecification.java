package br.senai.prova_jwt.dto.specification;

import br.senai.prova_jwt.dto.filters.FuncionarioFilterDto;
import br.senai.prova_jwt.model.Funcionario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FuncionarioSpecification {
    public static Specification<Funcionario> comFiltros(FuncionarioFilterDto filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
            }
            if (filtro.getCargo() != null && !filtro.getCargo().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("cargo").get("nome")), "%" + filtro.getCargo().toLowerCase() + "%")
                );
            }

            return predicate;

        };

    }
}
