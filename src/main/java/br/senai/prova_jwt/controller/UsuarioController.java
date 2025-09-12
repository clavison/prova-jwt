package br.senai.prova_jwt.controller;


import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UsuarioDto> listar(Pageable pageable) {
        return usuarioService.listarTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Long id, Authentication authentication) {
        try {
            UsuarioDto usuario = usuarioService.buscarPorId(id, authentication);
            return ResponseEntity.ok(usuario);
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
