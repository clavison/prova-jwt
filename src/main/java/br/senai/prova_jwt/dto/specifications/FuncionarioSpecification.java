package br.senai.prova_jwt.dto.specifications;

import br.senai.prova_jwt.dto.FuncionarioFiltroDto;
import br.senai.prova_jwt.model.Funcionario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FuncionarioSpecification {

    public static Specification<Funcionario> comFiltros(FuncionarioFiltroDto filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getCargoId() != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("cargo").get("id"), filtro.getCargoId()));
            }

            return predicate;
        };
    }


}
