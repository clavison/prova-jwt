package br.senai.prova_jwt.application.controller;

import br.senai.prova_jwt.application.dto.AuthRequestDto;
import br.senai.prova_jwt.application.dto.AuthResponseDto;
import br.senai.prova_jwt.infrastructure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    // Endpoint de login - recebe usuário e senha e retorna um token JWT se as credenciais forem válidas
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request) {
        try {
            // Autentica o usuário usando AuthenticationManager com username e password
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Carrega os detalhes do usuário autenticado
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            // Gera um token JWT para o usuário autenticado
            String token = jwtUtil.generateToken(userDetails);

            // Retorna o token no corpo da resposta
            return ResponseEntity.ok(new AuthResponseDto(token));

        } catch (BadCredentialsException e) {
            // Retorna erro 401 (não autorizado) se as credenciais forem inválidas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }


}

