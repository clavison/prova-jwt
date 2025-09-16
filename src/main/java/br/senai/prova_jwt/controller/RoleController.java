package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> salvar(@RequestBody RoleDto dto) {
        return ResponseEntity.status(201).body(roleService.salvar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> obterPorId(@PathVariable Long id) {
        RoleDto dto = roleService.buscarPorId(id);
        return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> listarTodos() {
        return ResponseEntity.ok(roleService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> editar(@PathVariable Long id, @RequestBody RoleDto dto) {
        if (roleService.buscarPorId(id) == null)
            return ResponseEntity.notFound().build();
        dto.setId(id);
        return ResponseEntity.ok(roleService.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        roleService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
