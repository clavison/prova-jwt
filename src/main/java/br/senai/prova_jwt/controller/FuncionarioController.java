package br.senai.prova_jwt.controller;


import br.senai.prova_jwt.domain.funcionario.Funcionario;
import br.senai.prova_jwt.domain.funcionario.FuncionarioDto;
import br.senai.prova_jwt.domain.funcionario.FuncionarioMapper;
import br.senai.prova_jwt.domain.funcionario.FuncionarioService;
import br.senai.prova_jwt.helpers.bases.BaseController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("funcionarios")
public class FuncionarioController extends BaseController<Funcionario, FuncionarioDto, Long, FuncionarioMapper> {

    public FuncionarioController(FuncionarioMapper mapper,
                                 FuncionarioService service) {
        super(mapper, service);
    }

}
