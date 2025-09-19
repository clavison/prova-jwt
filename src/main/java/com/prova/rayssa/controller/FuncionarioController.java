package com.prova.rayssa.controller;

import com.prova.rayssa.dto.FuncionarioDTO;
import com.prova.rayssa.dto.FuncionarioFiltroDTO;
import com.prova.rayssa.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {
    
    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<FuncionarioDTO> criar(@Valid @RequestBody FuncionarioDTO dto) {
        try {
            FuncionarioDTO funcionarioSalvo = funcionarioService.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Long id) {
        FuncionarioDTO funcionario = funcionarioService.buscarPorId(id);
        if (funcionario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping
    public ResponseEntity<Page<FuncionarioDTO>> listarComPaginacao(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cargoNome,
            @RequestParam(required = false) Long cargoId,
            @RequestParam(required = false) BigDecimal salarioMinimo,
            @RequestParam(required = false) BigDecimal salarioMaximo,
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        
        FuncionarioFiltroDTO filtro = new FuncionarioFiltroDTO(
            nome, cargoNome, cargoId, salarioMinimo, salarioMaximo
        );
        
        Page<FuncionarioDTO> funcionarios = funcionarioService.buscarComFiltros(filtro, pageable);
        return ResponseEntity.ok(funcionarios);
    }
    
    @GetMapping("/todos")
    public ResponseEntity<List<FuncionarioDTO>> listarTodos() {
        List<FuncionarioDTO> funcionarios = funcionarioService.listarTodos();
        return ResponseEntity.ok(funcionarios);
    }
    
    @GetMapping("/filtrar")
    public ResponseEntity<List<FuncionarioDTO>> listarComFiltros(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cargoNome,
            @RequestParam(required = false) Long cargoId,
            @RequestParam(required = false) BigDecimal salarioMinimo,
            @RequestParam(required = false) BigDecimal salarioMaximo) {
        
        FuncionarioFiltroDTO filtro = new FuncionarioFiltroDTO(
            nome, cargoNome, cargoId, salarioMinimo, salarioMaximo
        );
        
        List<FuncionarioDTO> funcionarios = funcionarioService.buscarComFiltrosSemPaginacao(filtro);
        return ResponseEntity.ok(funcionarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> atualizar(
            @PathVariable Long id, 
            @Valid @RequestBody FuncionarioDTO dto) {
        try {
            FuncionarioDTO funcionarioAtualizado = funcionarioService.atualizar(id, dto);
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            funcionarioService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

