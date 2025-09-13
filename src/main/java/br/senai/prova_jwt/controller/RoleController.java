package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> criar(@Valid @RequestBody RoleDto request) {
        try {
            RoleDto response = roleService.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> buscarPorId(@PathVariable Long id) {
        RoleDto role = roleService.buscarPorId(id);
        return ResponseEntity.ok(role);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<RoleDto>> listarComPaginacao(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<RoleDto> roles = roleService.listar(pageable);
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RoleDto request) {
        try {
            RoleDto response = roleService.atualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("não encontrada")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            roleService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
