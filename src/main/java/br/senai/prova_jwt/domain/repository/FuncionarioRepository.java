package br.senai.prova_jwt.domain.repository;

import br.senai.prova_jwt.domain.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends
        JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {
}