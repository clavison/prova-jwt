package com.prova.rayssa.repository;

import com.prova.rayssa.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {
    
    // Busca por nome contendo o texto (case insensitive)
    Page<Funcionario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
    
    // Busca por cargo contendo o texto (case insensitive)
    Page<Funcionario> findByCargo_NomeContainingIgnoreCase(String cargoNome, Pageable pageable);
    List<Funcionario> findByCargo_NomeContainingIgnoreCase(String cargoNome);
    
    // Busca combinada por nome do funcionário e nome do cargo
    @Query("SELECT f FROM Funcionario f WHERE " +
           "(:nome IS NULL OR :nome = '' OR LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
           "(:cargoNome IS NULL OR :cargoNome = '' OR LOWER(f.cargo.nome) LIKE LOWER(CONCAT('%', :cargoNome, '%')))")
    Page<Funcionario> findByFiltros(@Param("nome") String nome, 
                                   @Param("cargoNome") String cargoNome, 
                                   Pageable pageable);
    
    // Contagem por cargo
    @Query("SELECT COUNT(f) FROM Funcionario f WHERE f.cargo.id = :cargoId")
    long countByCargoId(@Param("cargoId") Long cargoId);
    
    // Busca funcionários por lista de IDs de cargo
    List<Funcionario> findByCargo_IdIn(List<Long> cargoIds);
}
