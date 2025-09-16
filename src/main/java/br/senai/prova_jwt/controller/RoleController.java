package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<RoleDto> criar(@RequestBody RoleDto role) {
        RoleDto salvo = service.salvar(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> buscarPorId(@PathVariable Long id) {
        RoleDto role = service.buscarPorId(id);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public Page<Role> getRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return service.getRolesPaginados(page, size);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<RoleDto>> buscarTodos() {
        List<RoleDto> roles = service.buscarTodos();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> atualizar(@PathVariable Long id, @RequestBody RoleDto role) {
        RoleDto roleBusca = service.buscarPorId(id);
        if (roleBusca == null) {
            return ResponseEntity.notFound().build();
        } else {
            role.setId(id);
            return ResponseEntity.ok(service.salvar(role));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
