package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.filtros.CargoFiltroDto;
import br.senai.prova_jwt.service.CargoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping
    public ResponseEntity<CargoDto> salvar(@RequestBody CargoDto dto) {
        return ResponseEntity.status(201).body(cargoService.salvar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDto> obterPorId(@PathVariable Long id) {
        CargoDto dto = cargoService.buscarPorId(id);
        return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<CargoDto>> listarTodos() {
        return ResponseEntity.ok(cargoService.listarTodos());
    }

    @GetMapping
    public Page<CargoDto> pesquisar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) Double salarioMin,
            @RequestParam(required = false) Double salarioMax,
            Pageable pageable) {
        CargoFiltroDto filtro = new CargoFiltroDto();
        filtro.setNome(nome);
        filtro.setDescricao(descricao);
        filtro.setSalarioMin(salarioMin);
        filtro.setSalarioMax(salarioMax);
        return cargoService.filtrar(filtro, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDto> editar(@PathVariable Long id, @RequestBody CargoDto dto) {
        if (cargoService.buscarPorId(id) == null)
            return ResponseEntity.notFound().build();
        dto.setId(id);
        return ResponseEntity.ok(cargoService.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        cargoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
