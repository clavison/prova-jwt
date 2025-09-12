package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UsuarioDto dto) {
        service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<UsuarioDto> listar() {
        return service.listar();
    }
}

