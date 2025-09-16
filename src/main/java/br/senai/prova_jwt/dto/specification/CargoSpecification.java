package br.senai.prova_jwt.dto.specification;

import br.senai.prova_jwt.dto.filtros.CargoFiltroDto;
import br.senai.prova_jwt.model.Cargo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CargoSpecification {

    public static Specification<Cargo> aplicarFiltros(CargoFiltroDto filtro) {
        return (root, query, cb) -> {
            Predicate restricoes = cb.conjunction();

            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                restricoes = cb.and(restricoes,
                        cb.like(cb.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getDescricao() != null && !filtro.getDescricao().isEmpty()) {
                restricoes = cb.and(restricoes,
                        cb.like(cb.lower(root.get("descricao")), "%" + filtro.getDescricao().toLowerCase() + "%"));
            }

            if (filtro.getSalarioMin() != null) {
                restricoes = cb.and(restricoes,
                        cb.greaterThanOrEqualTo(root.get("salario"), filtro.getSalarioMin()));
            }

            if (filtro.getSalarioMax() != null) {
                restricoes = cb.and(restricoes,
                        cb.lessThanOrEqualTo(root.get("salario"), filtro.getSalarioMax()));
            }

            return restricoes;
        };
    }
}
