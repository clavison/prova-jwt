package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.UsuarioCreateDTO;
import br.senai.prova_jwt.dto.UsuarioDTO;
import br.senai.prova_jwt.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.ok(usuarioService.criarUsuario(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UsuarioCreateDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginAutenticado = auth.getName();

        // Se for USER e tentar alterar outro usuário:
        boolean isUser = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
        if (isUser) {
            UsuarioDTO usuarioAutenticado = usuarioService.buscarPorLogin(loginAutenticado);
            if (!usuarioAutenticado.getId().equals(id)) {
                return ResponseEntity.status(403).body("Você só pode editar seu próprio usuário");
            }
        }

        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dto));
    }
}

