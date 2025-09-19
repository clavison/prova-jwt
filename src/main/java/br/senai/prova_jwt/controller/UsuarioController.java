package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.UsuarioCreateDto;
import br.senai.prova_jwt.dto.UsuarioResponseDto;
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
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody UsuarioCreateDto dto) {

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
                .body(UsuarioMapper.toDto(usuario));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UsuarioResponseDto> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Long id,
                                                        @RequestBody UsuarioCreateDto dto) {
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

        return ResponseEntity.ok(UsuarioMapper.toDto(usuario));
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
