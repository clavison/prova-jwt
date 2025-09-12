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
    public ResponseEntity<CargoDto> inserir(@RequestBody CargoDto cargo){
        CargoDto salvo = service.salvar(cargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDto> atualizar(@PathVariable Long id, @RequestBody CargoDto cargo) {
        CargoDto cargoBusca = service.buscarPorId(id);
        if (cargoBusca == null) return ResponseEntity.notFound().build();

        cargo.setId(id);
        return ResponseEntity.ok(service.salvar(cargo));
    }

    @GetMapping
    public ResponseEntity<List<CargoDto>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
