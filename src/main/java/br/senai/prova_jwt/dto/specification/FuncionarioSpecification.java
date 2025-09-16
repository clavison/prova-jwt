package br.senai.prova_jwt.dto.specification;

import br.senai.prova_jwt.dto.filtros.FuncionarioFiltroDto;
import br.senai.prova_jwt.model.Funcionario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FuncionarioSpecification {

    public static Specification<Funcionario> aplicarFiltros(FuncionarioFiltroDto filtro) {
        return (root, query, cb) -> {
            Predicate restricoes = cb.conjunction();

            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                restricoes = cb.and(restricoes,
                        cb.like(cb.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
                restricoes = cb.and(restricoes,
                        cb.like(cb.lower(root.get("email")), "%" + filtro.getEmail().toLowerCase() + "%"));
            }

            if (filtro.getTelefone() != null && !filtro.getTelefone().isEmpty()) {
                restricoes = cb.and(restricoes,
                        cb.like(root.get("telefone"), "%" + filtro.getTelefone() + "%"));
            }

            if (filtro.getCargoId() != null) {
                restricoes = cb.and(restricoes,
                        cb.equal(root.get("cargo").get("id"), filtro.getCargoId()));
            }

            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                restricoes = cb.and(restricoes,
                        cb.like(cb.lower(root.get("cargo").get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
            }

            return restricoes;
        };
    }
}
