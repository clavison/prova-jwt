package br.senai.prova_jwt.domains.funcionario;


import br.senai.prova_jwt.domains.cargo.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {
    
    List<Funcionario> findByCargo(Cargo cargo);
}
