package br.senai.prova_jwt.controller;


import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDto> criar(@RequestBody UsuarioDto usuarioDto) {
        UsuarioDto salvo = usuarioService.salvar(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> alterar(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto, Authentication authentication) {
        try {
            UsuarioDto usuarioAtualizado = usuarioService.alterar(id, usuarioDto, authentication);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
