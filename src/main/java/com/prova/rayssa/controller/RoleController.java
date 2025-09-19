package com.prova.rayssa.controller;

import com.prova.rayssa.model.Role;
import com.prova.rayssa.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping
    public ResponseEntity<Role> criarRole(@RequestBody Role role) {
        Role novaRole = roleRepository.save(role);
        return ResponseEntity.ok(novaRole);
    }
    
    @GetMapping
    public ResponseEntity<List<Role>> listarRoles() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }
}
