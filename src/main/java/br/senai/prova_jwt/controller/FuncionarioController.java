package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping
    public ResponseEntity<FuncionarioDto> criar(@RequestBody FuncionarioDto dto) {
        FuncionarioDto salvo = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> atualizar(@PathVariable Long id, @RequestBody FuncionarioDto dto) {
        FuncionarioDto atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public Page<FuncionarioDto> listarComFiltros(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long cargoId,
            Pageable pageable) {

        FuncionarioDto filtro = new FuncionarioDto();
        filtro.setNome(nome);
        if (cargoId != null) {
            // só precisa setar o cargoId no DTO
            var cargo = new Cargo();
            cargo.setId(cargoId);
            filtro.setCargo(cargo);
        }
        return service.listarComFiltros(filtro, pageable);
    }

    @GetMapping
    public Page<FuncionarioDto> buscarPaginado(Pageable pageable) {
        return service.buscarPaginado(pageable);
    }
}
