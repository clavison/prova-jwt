package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @PostMapping
    public ResponseEntity<RoleDto> criar(@RequestBody RoleDto dto) {
        RoleDto salvo = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> buscarPorId(@PathVariable Long id) {
        RoleDto dto = service.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> atualizar(@PathVariable Long id, @RequestBody RoleDto dto) {
        if (service.buscarPorId(id) == null) return ResponseEntity.notFound().build();
        dto.setId(id);
        return ResponseEntity.ok(service.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
