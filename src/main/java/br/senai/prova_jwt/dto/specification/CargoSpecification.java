package br.senai.prova_jwt.dto.specification;

import br.senai.prova_jwt.dto.filters.CargoFilterDto;
import br.senai.prova_jwt.model.Cargo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CargoSpecification {
    public static Specification<Cargo> comFiltros(CargoFilterDto filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("nome")), "%" +
                        filtro.getNome().toLowerCase() + "%"));
            }
            if (filtro.getSalarioMin() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("salario"), filtro.getSalarioMin()));
            }
            if (filtro.getSalarioMax() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("salario"), filtro.getSalarioMax()));
            }
            return predicate;
        };
    }
}
