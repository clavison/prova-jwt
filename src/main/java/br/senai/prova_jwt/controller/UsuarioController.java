package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.dto.mapper.UsuarioMapper;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioDto> criar(@RequestBody UsuarioDto dto) {
        Usuario usuario = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(usuario));
    }

    @GetMapping
    public List<UsuarioDto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Long id) {
        UsuarioDto dto = service.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody UsuarioDto dto,
                                       Authentication authentication) {
        String requester = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        UsuarioDto usuario = service.buscarPorId(id);

        // USER só pode editar ele mesmo
        if (!isAdmin && !requester.equals(usuario.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        service.atualizar(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id, Authentication authentication) {
        String requester = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        UsuarioDto alvo = service.buscarPorId(id);

        // USER não pode deletar ninguém (nem ele mesmo)
        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

