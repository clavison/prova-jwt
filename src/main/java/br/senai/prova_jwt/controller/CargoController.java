package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService service;

    @PostMapping
    public ResponseEntity<CargoDto> criar(@RequestBody CargoDto dto) {
        CargoDto salvo = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDto> buscarPorId(@PathVariable Long id) {
        CargoDto dto = service.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CargoDto>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDto> atualizar(@PathVariable Long id, @RequestBody CargoDto dto) {
        if (service.buscarPorId(id) == null) return ResponseEntity.notFound().build();
        dto.setId(id);
        return ResponseEntity.ok(service.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}