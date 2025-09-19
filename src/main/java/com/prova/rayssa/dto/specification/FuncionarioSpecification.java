package com.prova.rayssa.dto.specification;

import com.prova.rayssa.dto.FuncionarioFiltroDTO;
import com.prova.rayssa.model.Funcionario;
import com.prova.rayssa.model.Cargo;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioSpecification {
    
    public static Specification<Funcionario> comFiltros(FuncionarioFiltroDTO filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Filtro por nome do funcionário
            if (filtro.getNome() != null && !filtro.getNome().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nome")), 
                    "%" + filtro.getNome().toLowerCase() + "%"
                ));
            }
            
            // Join com cargo para filtros relacionados
            Join<Funcionario, Cargo> cargoJoin = root.join("cargo");
            
            // Filtro por nome do cargo
            if (filtro.getCargoNome() != null && !filtro.getCargoNome().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(cargoJoin.get("nome")), 
                    "%" + filtro.getCargoNome().toLowerCase() + "%"
                ));
            }
            
            // Filtro por ID do cargo
            if (filtro.getCargoId() != null) {
                predicates.add(criteriaBuilder.equal(cargoJoin.get("id"), filtro.getCargoId()));
            }
            
            // Filtro por salário mínimo
            if (filtro.getSalarioMinimo() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    cargoJoin.get("salario"), filtro.getSalarioMinimo()
                ));
            }
            
            // Filtro por salário máximo
            if (filtro.getSalarioMaximo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    cargoJoin.get("salario"), filtro.getSalarioMaximo()
                ));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    
    public static Specification<Funcionario> comNome(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (nome == null || nome.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("nome")), 
                "%" + nome.toLowerCase() + "%"
            );
        };
    }
    
    public static Specification<Funcionario> comCargo(String cargoNome) {
        return (root, query, criteriaBuilder) -> {
            if (cargoNome == null || cargoNome.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Funcionario, Cargo> cargoJoin = root.join("cargo");
            return criteriaBuilder.like(
                criteriaBuilder.lower(cargoJoin.get("nome")), 
                "%" + cargoNome.toLowerCase() + "%"
            );
        };
    }
    
    public static Specification<Funcionario> comSalarioEntre(BigDecimal salarioMin, BigDecimal salarioMax) {
        return (root, query, criteriaBuilder) -> {
            Join<Funcionario, Cargo> cargoJoin = root.join("cargo");
            List<Predicate> predicates = new ArrayList<>();
            
            if (salarioMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    cargoJoin.get("salario"), salarioMin
                ));
            }
            
            if (salarioMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    cargoJoin.get("salario"), salarioMax
                ));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}