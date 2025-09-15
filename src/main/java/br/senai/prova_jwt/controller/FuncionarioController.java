package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.filtros.FuncionarioFiltroDto;
import br.senai.prova_jwt.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping
    public ResponseEntity<FuncionarioDto> criar(@RequestBody FuncionarioDto dto) {
        FuncionarioDto salvo = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> buscarPorId(@PathVariable Long id) {
        FuncionarioDto dto = service.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> atualizar(@PathVariable Long id, @RequestBody FuncionarioDto dto) {
        if (service.buscarPorId(id) == null) return ResponseEntity.notFound().build();
        dto.setId(id);
        return ResponseEntity.ok(service.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint original sem paginação
    @GetMapping("/todos")
    public ResponseEntity<List<FuncionarioDto>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    // Endpoint com filtros dinâmicos e paginação
    @GetMapping
    public Page<FuncionarioDto> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) Long cargoId,
            @RequestParam(required = false) String cargoNome,
            Pageable pageable
    ) {
        FuncionarioFiltroDto filtro = new FuncionarioFiltroDto();
        filtro.setNome(nome);
        filtro.setEmail(email);
        filtro.setTelefone(telefone);
        filtro.setCargoId(cargoId);
        filtro.setCargoNome(cargoNome);
        return service.listarComFiltros(filtro, pageable);
    }





}