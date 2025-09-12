package br.senai.prova_jwt.dto.specifications;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.model.Cargo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CargoSpecification {

    private CargoSpecification() {}

    public static Specification<Cargo> comFiltros(CargoDto filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filtro == null) {
                return predicate;
            }

            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getSalario() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("salario"), filtro.getSalario()));
            }

            if (filtro.getId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("id"), filtro.getId()));
            }

            return predicate;
        };
    }
}
