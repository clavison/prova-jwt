package br.senai.prova_jwt.domain.funcionario;

import br.senai.prova_jwt.helpers.bases.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends BaseRepository<Funcionario, Long> {
}
