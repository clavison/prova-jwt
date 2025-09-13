package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.FuncionarioFiltroDTO;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import br.senai.prova_jwt.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @PostMapping
    public Funcionario criar(@RequestBody Funcionario funcionario) {
        return funcionarioService.criar(funcionario);
    }

    @PostMapping("/lote")
    public List<Funcionario> criarEmLote(@RequestBody List<Funcionario> cargos) {
        return funcionarioRepository.saveAll(cargos);
    }

    @GetMapping
    public Page<Funcionario> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long cargoId,
            Pageable pageable
    ) {
        FuncionarioFiltroDTO filtro = new FuncionarioFiltroDTO();
        filtro.setNome(nome);
        filtro.setCargoId(cargoId);

        return funcionarioService.pegarFuncionariosPaginado(filtro, pageable);
    }
}
