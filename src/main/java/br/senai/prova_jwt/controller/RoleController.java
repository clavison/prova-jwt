package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.RoleDTO;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public RoleDTO criar(@RequestBody Role role) {
        return roleService.criar(role);
    }

    @GetMapping
    public List<RoleDTO> listarTodas() {
        return roleService.listarTodas();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> atualizar(@PathVariable Long id, @RequestBody Role role) {
        RoleDTO roleAtualizada = roleService.atualizar(id, role);
        return ResponseEntity.ok(roleAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        roleService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> buscarPorId(@PathVariable Long id) {
        RoleDTO role = roleService.buscarPorId(id);
        return ResponseEntity.ok(role);
    }


    @GetMapping("/buscar")
    public ResponseEntity<RoleDTO> buscarPorNome(@RequestParam String descricao) {
        RoleDTO role = roleService.buscarPordescricao(descricao);
        return ResponseEntity.ok(role);
    }
}
