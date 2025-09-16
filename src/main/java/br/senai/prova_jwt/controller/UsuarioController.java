package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody UsuarioDto dto) {
        Usuario novoUsuario = usuarioService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obterPorId(@PathVariable Long id) {
        UsuarioDto usuarioDto = usuarioService.buscarPorId(id);
        if (usuarioDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioDto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioDto> obterPorUsername(@PathVariable String username) {
        UsuarioDto usuarioDto = usuarioService.buscarPorUsuario(username);
        if (usuarioDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editar(@PathVariable Long id, @RequestBody UsuarioDto dto) {
        if (usuarioService.buscarPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        return ResponseEntity.ok(usuarioService.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}