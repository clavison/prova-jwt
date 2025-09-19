package br.senai.prova_jwt.domain.specifications;

import br.senai.prova_jwt.application.dto.filtros.CargoFiltroDto;
import br.senai.prova_jwt.domain.model.Cargo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CargoSpecification {

    public static Specification<Cargo> comFiltros(CargoFiltroDto filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            // Verifica se o filtro de nome foi informado
            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                // Adiciona à predicate uma condição "LIKE" (contendo) ignorando maiúsculas/minúsculas
                // cb.lower(root.get("nome")) transforma o campo 'nome' em minúsculas
                // filtro.getNome().toLowerCase() transforma o valor do filtro em minúsculas
                // "%" + ... + "%" permite que o nome contenha o valor informado em qualquer posição
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("nome")), "%" +
                        filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getDescricao() != null && !filtro.getDescricao().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("descricao")), "%" +
                        filtro.getDescricao().toLowerCase() + "%"));
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