package br.senai.prova_jwt.dto.specifications;

import br.senai.prova_jwt.dto.FuncionarioFiltroDto;
import br.senai.prova_jwt.model.Funcionario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FuncionarioSpecification {

    public static Specification<Funcionario> comFiltros(FuncionarioFiltroDto filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (temNome(filtro.getNome())) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("nome")), likePattern(filtro.getNome())));
            }

            if (temSalarioMinimo(filtro.getSalarioMinimo())) {
                predicate = cb.and(predicate,
                        cb.greaterThanOrEqualTo(root.get("cargo").get("salario"), filtro.getSalarioMinimo()));
            }

            if (temSalarioMaximo(filtro.getSalarioMaximo())) {
                predicate = cb.and(predicate,
                        cb.lessThanOrEqualTo(root.get("cargo").get("salario"), filtro.getSalarioMaximo()));
            }

            return predicate;
        };
    }

    private static boolean temNome(String nome) {
        return nome != null && !nome.isBlank();
    }

    private static boolean temSalarioMinimo(Double salarioMin) {
        return salarioMin != null;
    }

    private static boolean temSalarioMaximo(Double salarioMax) {
        return salarioMax != null;
    }

    private static String likePattern(String value) {
        return "%" + value.toLowerCase() + "%";
    }

}
