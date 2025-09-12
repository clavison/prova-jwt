package br.senai.prova_jwt.dto.specifications;

import br.senai.prova_jwt.dto.FuncionarioFiltroDto;
import br.senai.prova_jwt.model.Funcionario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FuncionarioSpecification {

    public static Specification<Funcionario> comFiltros(FuncionarioFiltroDto filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filtro.getSalarioMinimo() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("cargo").get("salario"),
                        filtro.getSalarioMinimo()));
            }

            if (filtro.getSalarioMaximo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("cargo").get("salario"),
                        filtro.getSalarioMaximo()));
            }

            if (filtro.getUsername() != null && !filtro.getUsername().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("username"), filtro.getUsername()));
            }

            return predicate;
        };
    }
}
