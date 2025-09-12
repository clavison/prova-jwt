package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.CargoFiltroDto;
import br.senai.prova_jwt.service.CargoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CargoDto> salvar(@RequestBody CargoDto cargoDto) {
        CargoDto salvo = cargoService.salvar(cargoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CargoDto> alterar(@PathVariable Long id, @RequestBody CargoDto cargoDto) {
        try {
            CargoDto cargoAtualizado = cargoService.alterar(id, cargoDto);
            return ResponseEntity.ok(cargoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<CargoDto> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal salarioMin,
            @RequestParam(required = false) BigDecimal salarioMax,
            Pageable pageable
    ) {
        CargoFiltroDto filtro = new CargoFiltroDto(nome, salarioMin, salarioMax);
        return cargoService.listarComFiltros(filtro, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CargoDto> buscarPorId(@PathVariable Long id) {
        return cargoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        cargoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
