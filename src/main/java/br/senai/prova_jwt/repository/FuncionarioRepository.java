package br.senai.prova_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FuncionarioRepository<Funcionario> extends JpaRepository<Funcionario, Long>,
        JpaSpecificationExecutor<Funcionario> {
}
