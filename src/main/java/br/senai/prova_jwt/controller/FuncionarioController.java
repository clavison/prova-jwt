package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.FuncionarioFiltroDto;
import br.senai.prova_jwt.service.FuncionarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FuncionarioDto> criar(@RequestBody FuncionarioDto funcionarioDto) {
        FuncionarioDto salvo = funcionarioService.salvar(funcionarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> alterar(@PathVariable Long id, @RequestBody FuncionarioDto funcionarioDto, Authentication authentication) {
        try {
            FuncionarioDto funcionarioAtualizado = funcionarioService.alterar(id, funcionarioDto, authentication);
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public Page<FuncionarioDto> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal salarioMin,
            @RequestParam(required = false) BigDecimal salarioMax,
            Pageable pageable,
            Authentication authentication
    ) {
        FuncionarioFiltroDto filtro = new FuncionarioFiltroDto(nome, salarioMin, salarioMax, null);
        return funcionarioService.listarComFiltros(filtro, pageable, authentication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> buscarPorId(@PathVariable Long id, Authentication authentication) {
        try {
            FuncionarioDto funcionario = funcionarioService.buscarPorId(id, authentication);
            return ResponseEntity.ok(funcionario);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        funcionarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
