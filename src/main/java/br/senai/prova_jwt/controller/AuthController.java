package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.request.AuthRequestDto;
import br.senai.prova_jwt.dto.response.AuthResponseDto;
import br.senai.prova_jwt.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService userService;
    private final JwtUtil jwtHelper;

    public AuthController(AuthenticationManager authManager, UserDetailsService userService, JwtUtil jwtHelper) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtHelper = jwtHelper;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDto loginDto) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getSenha()));

            UserDetails user = userService.loadUserByUsername(loginDto.getUsername());
            String jwt = jwtHelper.gerarToken(user);

            return ResponseEntity.ok(new AuthResponseDto(jwt));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha incorretos");
        }
    }
}
