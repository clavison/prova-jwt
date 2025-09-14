package br.senai.prova_jwt.domains.auth.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO usuario) {
        UsuarioDTO salvo = service.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        UsuarioDTO usuario = service.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioDTO usuario) {
        UsuarioDTO usuarioBusca = service.buscarPorId(id);
        if (usuarioBusca == null) {
            return ResponseEntity.notFound().build();
        } else {
            usuario.setId(id);
            return ResponseEntity.ok(service.atualizar(id, usuario));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public Page<UsuarioDTO> listar(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String role,
            Pageable pageable
    ) {
        UsuarioFiltroDTO filtro = new UsuarioFiltroDTO();
        filtro.setUsername(username);
        filtro.setRole(role);
        return service.listarComFiltros(filtro, pageable);
    }

    /**
     * Cria os usuários padrão (admin/admin e user/user) no banco de dados.
     * Endpoint público - não requer autenticação.
     * Só funciona se não existirem usuários no sistema.
     */
    @PostMapping("/criar-usuarios-default")
    public ResponseEntity<String> criarUsuariosPadrao() {
        try {
            service.criarUsuariosPadrao();
            return ResponseEntity.ok("Usuários padrão criados com sucesso! (admin/admin e user/user)");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}