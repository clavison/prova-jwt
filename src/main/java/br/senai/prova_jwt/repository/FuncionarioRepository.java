package br.senai.prova_jwt.repository;

import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("SELECT f FROM Funcionario f WHERE f.cargo.salario BETWEEN :salarioMin AND :salarioMax")
    List<Funcionario> findByCargoSalarioBetween(@Param("salarioMin") Double salarioMin, @Param("salarioMax") Double salarioMax);

    List<Funcionario> findByCargo(Cargo cargo);
}
