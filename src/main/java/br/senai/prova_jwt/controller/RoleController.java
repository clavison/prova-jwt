package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> criar(@RequestBody RoleDto roleDto) {
        RoleDto salvo = roleService.salvar(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> buscarPorId(@PathVariable Long id) {
        Optional<RoleDto> roleDto =  roleService.buscarPorId(id);
        return roleDto.map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        roleService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> atualizar(
            @PathVariable Long id,
            @RequestBody RoleDto roleDto
    ) {
        Optional<RoleDto> roleBusca = roleService.buscarPorId(id);
        if (roleBusca.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            roleBusca.get().setId(roleDto.getId());
            return ResponseEntity.ok(roleService.salvar(roleDto));
        }
    }

}
