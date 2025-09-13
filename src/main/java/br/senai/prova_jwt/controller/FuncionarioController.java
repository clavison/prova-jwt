package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.filters.FuncionarioFilterDto;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping
    public ResponseEntity<FuncionarioDto> criar(@RequestBody FuncionarioDto funcionario) {
        FuncionarioDto salvo = service.salvar(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> buscarPorId(@PathVariable Long id) {
        FuncionarioDto funcionario = service.buscarPorId(id);
        if (funcionario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping
    public Page<Funcionario> getFuncionarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return service.getFuncionariosPaginados(page, size);
    }

    @GetMapping("/salario")
    public ResponseEntity<List<FuncionarioDto>> buscarPorSalarioBetween(
            @RequestParam Double salarioMin,
            @RequestParam Double salarioMax) {
        List<FuncionarioDto> funcionarios = service.buscarPorSalarioBetween(salarioMin, salarioMax);
        return ResponseEntity.ok(funcionarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> atualizar(@PathVariable Long id, @RequestBody FuncionarioDto funcionario) {
        FuncionarioDto funcionarioBusca = service.buscarPorId(id);
        if (funcionarioBusca == null) {
            return ResponseEntity.notFound().build();
        } else {
            funcionario.setId(id);
            return ResponseEntity.ok(service.salvar(funcionario));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public Page<Funcionario> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cargo,
            Pageable pageable
    ) {
        FuncionarioFilterDto filtro = new FuncionarioFilterDto();
        filtro.setNome(nome);
        filtro.setCargo(cargo);
        return service.listarComFiltros(filtro, pageable);
    }

}

