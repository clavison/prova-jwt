package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.FuncionarioFiltroDto;
import br.senai.prova_jwt.service.FuncionarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {


    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<FuncionarioDto> criar(@RequestBody FuncionarioDto funcionarioDto) {
        FuncionarioDto salvo = funcionarioService.salvar(funcionarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> buscarPorId(@PathVariable Long id) {
        Optional<FuncionarioDto> funcionarioDtoDto =  funcionarioService.buscarPorId(id);
        return funcionarioDtoDto.map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        funcionarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> atualizar(
            @PathVariable Long id,
            @RequestBody FuncionarioDto funcionarioDto
    ) {
        Optional<FuncionarioDto> funcionarioBusca = funcionarioService.buscarPorId(id);
        if (funcionarioBusca.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            funcionarioBusca.get().setId(funcionarioDto.getId());
            return ResponseEntity.ok(funcionarioService.salvar(funcionarioDto));
        }
    }

    @GetMapping
    public Page<FuncionarioDto> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Double salarioMin,
            @RequestParam(required = false) Double salarioMax,
            Pageable pageable
    ) {
        FuncionarioFiltroDto filtro = new FuncionarioFiltroDto(nome, salarioMin, salarioMax);
        return funcionarioService.listarComFiltros(filtro, pageable);
    }

}
