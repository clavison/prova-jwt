package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.CargoFiltroDto;
import br.senai.prova_jwt.service.CargoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping
    public ResponseEntity<CargoDto> criar(@RequestBody CargoDto cargoDto) {
        CargoDto salvo = cargoService.salvar(cargoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDto> buscarPorId(@PathVariable Long id) {
        Optional<CargoDto> cargoDto =  cargoService.buscarPorId(id);
        return cargoDto.map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        cargoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDto> atualizar(
            @PathVariable Long id,
            @RequestBody CargoDto cargoDto
    ) {
        Optional<CargoDto> cargoBusca = cargoService.buscarPorId(id);
        if (cargoBusca.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            cargoBusca.get().setId(cargoDto.getId());
            return ResponseEntity.ok(cargoService.salvar(cargoDto));
        }
    }

    @GetMapping
    public Page<CargoDto> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Double salarioMin,
            @RequestParam(required = false) Double salarioMax,
            Pageable pageable
    ) {
        CargoFiltroDto filtro = new CargoFiltroDto(nome, salarioMin, salarioMax);
        return cargoService.listarComFiltros(filtro, pageable);
    }

}
