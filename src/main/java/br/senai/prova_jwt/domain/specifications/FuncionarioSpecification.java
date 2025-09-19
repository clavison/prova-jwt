package br.senai.prova_jwt.domain.specifications;

import br.senai.prova_jwt.application.dto.filtros.FuncionarioFiltroDto;
import br.senai.prova_jwt.domain.model.Funcionario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FuncionarioSpecification {

    public static Specification<Funcionario> comFiltros(FuncionarioFiltroDto filtro) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("nome")), "%" +
                        filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%" +
                        filtro.getEmail().toLowerCase() + "%"));
            }

            if (filtro.getTelefone() != null && !filtro.getTelefone().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("telefone"), "%" +
                        filtro.getTelefone() + "%"));
            }

            if (filtro.getCargoId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("cargo").get("id"), filtro.getCargoId()));
            }

            if (filtro.getCargoNome() != null && !filtro.getCargoNome().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("cargo").get("nome")), "%" +
                        filtro.getCargoNome().toLowerCase() + "%"));
            }
            return predicate;
        };
    }
}