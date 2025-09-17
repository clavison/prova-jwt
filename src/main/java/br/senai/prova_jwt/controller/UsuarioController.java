package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.dto.UsuarioResponseDto;
import br.senai.prova_jwt.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UsuarioDto dto) {
        service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody UsuarioDto usuarioDto,
            Authentication authentication
    ) {
        Optional<UsuarioResponseDto> usuarioExistente = service.buscarPorId(id);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && usuarioExistente.isPresent()
                && !usuarioExistente.get().getUsername().equals(authentication.getName())) {
            log.warn("Acesso negado: usuário '{}' tentou atualizar o usuário com id {} sem permissão.",
                    authentication.getName(), id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (usuarioExistente.isEmpty()) {
            log.info("Tentativa de atualizar usuário inexistente: id {}", id);
            return ResponseEntity.notFound().build();
        } else {
            log.info("Usuário '{}' atualizou os dados do usuário id {}.", authentication.getName(), id);
            usuarioExistente.get().setId(usuarioDto.getId());
            return ResponseEntity.ok(service.salvar(usuarioDto));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id) {
        Optional<UsuarioResponseDto> usuarioResponseDto =  service.buscarPorId(id);
        return usuarioResponseDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<UsuarioResponseDto> listar() {
        return service.listar();
    }

}
