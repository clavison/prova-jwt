package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.UsuarioCreateDTO;
import br.senai.prova_jwt.dto.UsuarioResponseDTO;
import br.senai.prova_jwt.mapper.UsuarioMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.RoleRepository;
import br.senai.prova_jwt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@RequestBody UsuarioCreateDTO dto) {

        List<Role> roles = dto.getRoles()
                .stream()
                .map(role -> roleRepository.findByDescricao(role)
                        .orElseThrow(() -> new RuntimeException("Role não encontrada: " + role)))
                .collect(Collectors.toList());

        Usuario usuario = UsuarioMapper.toEntity(dto, roles);
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario = usuarioRepository.save(usuario);

        return ResponseEntity
                .created(URI.create("/usuarios/" + usuario.getId()))
                .body(UsuarioMapper.toDTO(usuario));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER') and #id == authentication.principal.username")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody UsuarioCreateDTO dto) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if (optUsuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = optUsuario.get();

        usuario.setLogin(dto.getLogin());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        List<Role> roles = dto.getRoles()
                .stream()
                .map(role -> roleRepository.findByDescricao(role)
                        .orElseThrow(() -> new RuntimeException("Role não encontrada: " + role)))
                .collect(Collectors.toList());

        usuario.setRoles(roles);

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(UsuarioMapper.toDTO(usuario));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
