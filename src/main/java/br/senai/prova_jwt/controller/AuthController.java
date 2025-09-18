package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.domain.login.LoginDto;
import br.senai.prova_jwt.domain.login.LoginFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final LoginFacade loginFacade;

    public AuthController(LoginFacade loginFacade) {
        this.loginFacade = loginFacade;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto request) {
        String token = loginFacade.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(token);
    }

}