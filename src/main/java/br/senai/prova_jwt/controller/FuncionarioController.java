package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.filtros.FuncionarioFiltroDto;
import br.senai.prova_jwt.service.FuncionarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<FuncionarioDto> salvar(@RequestBody FuncionarioDto dto) {
        return ResponseEntity.status(201).body(funcionarioService.salvar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> obterPorId(@PathVariable Long id) {
        FuncionarioDto dto = funcionarioService.buscarPorId(id);
        return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> editar(@PathVariable Long id, @RequestBody FuncionarioDto dto) {
        if (funcionarioService.buscarPorId(id) == null)
            return ResponseEntity.notFound().build();
        dto.setId(id);
        return ResponseEntity.ok(funcionarioService.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        funcionarioService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<FuncionarioDto>> listarTodos() {
        return ResponseEntity.ok(funcionarioService.listarTodos());
    }

    @GetMapping
    public Page<FuncionarioDto> pesquisar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) Long cargoId,
            @RequestParam(required = false) String cargoNome,
            Pageable pageable) {
        FuncionarioFiltroDto filtro = new FuncionarioFiltroDto();
        filtro.setNome(nome);
        filtro.setEmail(email);
        filtro.setTelefone(telefone);
        filtro.setCargoId(cargoId);
        filtro.setCargoNome(cargoNome);
        return funcionarioService.filtrar(filtro, pageable);
    }
}
