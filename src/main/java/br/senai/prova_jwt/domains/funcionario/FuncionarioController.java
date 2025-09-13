package br.senai.prova_jwt.domains.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping
    public ResponseEntity<FuncionarioDTO> criar(@RequestBody FuncionarioDTO funcionario) {
        FuncionarioDTO salvo = service.salvar(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Long id) {
        FuncionarioDTO funcionario = service.buscarPorId(id);
        if (funcionario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping
    public Page<FuncionarioDTO> buscarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.listar(page, size);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> atualizar(@PathVariable Long id, @RequestBody FuncionarioDTO funcionario) {
        FuncionarioDTO funcionarioBusca = service.buscarPorId(id);
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
    public Page<FuncionarioDTO> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String nomecargo,
            @RequestParam(required = false) BigDecimal salarioMin,
            @RequestParam(required = false) BigDecimal salarioMax,
            Pageable pageable
    ) {
        FuncionarioFiltroDTO filtro = new FuncionarioFiltroDTO();
        filtro.setNome(nome);
        filtro.setNomecargo(nomecargo);
        filtro.setSalarioMin(salarioMin);
        filtro.setSalarioMax(salarioMax);
        return service.listarComFiltros(filtro, pageable);
    }
}
