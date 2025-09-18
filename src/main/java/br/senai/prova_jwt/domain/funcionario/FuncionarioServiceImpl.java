package br.senai.prova_jwt.domain.funcionario;

import br.senai.prova_jwt.helpers.bases.BaseService;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioServiceImpl extends BaseService<Funcionario, Long, FuncionarioRepository> implements FuncionarioService {

    public FuncionarioServiceImpl(FuncionarioRepository repo) {
        super(repo, Funcionario.class);
    }

}
