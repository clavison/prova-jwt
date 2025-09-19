package com.prova.rayssa.controller;

import com.prova.rayssa.model.Usuario;
import com.prova.rayssa.dto.UsuarioDTO;
import com.prova.rayssa.dto.AuthResponseDTO;
import com.prova.rayssa.dto.mapper.UsuarioMapper;
import com.prova.rayssa.repository.UsuarioRepository;
import com.prova.rayssa.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/cadastrar")
    public ResponseEntity<AuthResponseDTO> cadastrar(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        // Verificar se a senha não é nula
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        String token = jwtUtil.generateToken(usuario.getLogin());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        // Verificar se a senha não é nula
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario novoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(novoUsuario);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == authentication.principal.id)")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }
        
        if (usuario.getLogin() != null) {
            usuarioExistente.setLogin(usuario.getLogin());
        }
        
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        
        if (usuario.getRoles() != null) {
            usuarioExistente.setRoles(usuario.getRoles());
        }
        
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}
