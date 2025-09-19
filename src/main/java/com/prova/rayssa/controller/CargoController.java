package com.prova.rayssa.controller;

import com.prova.rayssa.dto.CargoDTO;
import com.prova.rayssa.service.CargoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargos")
@CrossOrigin(origins = "*")
public class CargoController {
    
    @Autowired
    private CargoService cargoService;

    @PostMapping
    public ResponseEntity<CargoDTO> criar(@Valid @RequestBody CargoDTO dto) {
        try {
            CargoDTO cargoSalvo = cargoService.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cargoSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDTO> buscarPorId(@PathVariable Long id) {
        CargoDTO cargo = cargoService.buscarPorId(id);
        if (cargo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cargo);
    }

    @GetMapping
    public ResponseEntity<Page<CargoDTO>> listarComPaginacao(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        
        Page<CargoDTO> cargos = cargoService.listarTodosComPaginacao(pageable);
        return ResponseEntity.ok(cargos);
    }
    
    @GetMapping("/todos")
    public ResponseEntity<List<CargoDTO>> listarTodos() {
        List<CargoDTO> cargos = cargoService.listarTodos();
        return ResponseEntity.ok(cargos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDTO> atualizar(
            @PathVariable Long id, 
            @Valid @RequestBody CargoDTO dto) {
        try {
            CargoDTO cargoAtualizado = cargoService.atualizar(id, dto);
            return ResponseEntity.ok(cargoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            cargoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
