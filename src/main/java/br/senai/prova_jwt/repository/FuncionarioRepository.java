package br.senai.prova_jwt.repository;

import br.senai.prova_jwt.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>,
        JpaSpecificationExecutor<Funcionario> {
}
