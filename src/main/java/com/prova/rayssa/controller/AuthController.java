package com.prova.rayssa.controller;

import com.prova.rayssa.dto.LoginRequest;
import com.prova.rayssa.model.Usuario;
import com.prova.rayssa.repository.UsuarioRepository;
import com.prova.rayssa.service.UsuarioService;
import com.prova.rayssa.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar o usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getLogin(),
                            loginRequest.getSenha()
                    )
            );

            // Buscar o usuário no banco
            Usuario usuario = usuarioService.buscarPorLogin(loginRequest.getLogin());

            // Gerar token JWT real
            String token = jwtUtil.generateToken(usuario.getLogin());

            // Criar resposta com dados do usuário
            return ResponseEntity.ok(Map.of(
                "token", token,
                "type", "Bearer",
                "login", usuario.getLogin(),
                "roles", usuario.getRoles().stream()
                    .map(role -> role.getDescricao())
                    .collect(Collectors.toList())
            ));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Credenciais inválidas"));
        }
    }
}
