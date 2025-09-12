package br.senai.prova_jwt.repository;

import br.senai.prova_jwt.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Page<Funcionario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Query("SELECT f FROM Funcionario f WHERE f.cargo.nome LIKE %:cargoNome%")
    Page<Funcionario> findByCargoNomeContaining(@Param("cargoNome") String cargoNome, Pageable pageable);

    @Query("SELECT f FROM Funcionario f WHERE f.nome LIKE %:nome% AND f.cargo.nome LIKE %:cargoNome%")
    Page<Funcionario> findByNomeAndCargoNome(@Param("nome") String nome,
                                             @Param("cargoNome") String cargoNome,
                                             Pageable pageable);
}