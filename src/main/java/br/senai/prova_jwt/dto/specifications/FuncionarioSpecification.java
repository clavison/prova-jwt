package br.senai.prova_jwt.dto.specifications;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.model.Funcionario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FuncionarioSpecification {

    private FuncionarioSpecification() {}

    public static Specification<Funcionario> comFiltros(FuncionarioDto filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filtro == null) {
                return predicate;
            }

            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("id"), filtro.getId()));
            }

            if (filtro.getCargo() != null && filtro.getCargo().getId() != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("cargo").get("id"), filtro.getCargo().getId()));
            }

            return predicate;
        };
    }
}
