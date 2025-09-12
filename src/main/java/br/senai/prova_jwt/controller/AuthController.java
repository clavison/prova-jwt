package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.LoginRequest;
import br.senai.prova_jwt.dto.LoginResponse;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.UsuarioRepository;
import br.senai.prova_jwt.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getSenha())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getLogin());
        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token, loginRequest.getLogin(), "Login realizado com sucesso"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest registerRequest) {
        if (usuarioRepository.existsByLogin(registerRequest.getLogin())) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        Usuario usuario = new Usuario();
        usuario.setLogin(registerRequest.getLogin());
        usuario.setSenha(passwordEncoder.encode(registerRequest.getSenha()));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuário registrado com sucesso");
    }
}