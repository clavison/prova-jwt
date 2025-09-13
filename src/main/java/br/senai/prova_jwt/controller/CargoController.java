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
    public ResponseEntity<CargoDto> criar(@RequestBody CargoDto cargo) {
        CargoDto salvo = service.salvar(cargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDto> buscarPorId(@PathVariable Long id) {
        CargoDto cargo = service.buscarPorId(id);
        if(cargo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cargo);
    }


    @GetMapping
    public ResponseEntity<List<CargoDto>> buscarTodos() {
        List<CargoDto> cargos = service.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(cargos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDto> atualizar(@PathVariable Long id, @RequestBody CargoDto cargo) {
        CargoDto cargoBusca = service.buscarPorId(id);
        if (cargoBusca == null) {
            return ResponseEntity.notFound().build();
        } else {
            cargo.setId(id);
            return ResponseEntity.ok(service.salvar(cargo));
        }
    }

    @DeleteMapping
    public ResponseEntity<CargoDto> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
