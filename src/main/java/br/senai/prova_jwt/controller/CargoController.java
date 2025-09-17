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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping
    public ResponseEntity<CargoDto> criar(@RequestBody CargoDto cargoDto) {
        log.info("Requisição recebida para criar cargo: {}", cargoDto.getNome());
        CargoDto salvo = cargoService.salvar(cargoDto);
        log.info("Cargo criado com sucesso. ID: {}", salvo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDto> buscarPorId(@PathVariable Long id) {
        log.info("Buscando cargo pelo ID: {}", id);
        Optional<CargoDto> cargoDto = cargoService.buscarPorId(id);
        if (cargoDto.isPresent()) {
            log.info("Cargo encontrado: {}", cargoDto.get().getNome());
            return ResponseEntity.ok(cargoDto.get());
        } else {
            log.warn("Cargo não encontrado. ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.info("Excluindo cargo com ID: {}", id);
        cargoService.excluir(id);
        log.info("Cargo excluído com sucesso. ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDto> atualizar(@PathVariable Long id, @RequestBody CargoDto cargoDto) {
        log.info("Requisição para atualizar cargo. ID: {}", id);
        Optional<CargoDto> cargoBusca = cargoService.buscarPorId(id);
        if (cargoBusca.isEmpty()) {
            log.warn("Tentativa de atualizar cargo inexistente. ID: {}", id);
            return ResponseEntity.notFound().build();
        } else {
            CargoDto atualizado = cargoService.salvar(cargoDto);
            log.info("Cargo atualizado com sucesso. ID: {}", atualizado.getId());
            return ResponseEntity.ok(atualizado);
        }
    }

    @GetMapping
    public Page<CargoDto> listar(@RequestParam(required = false) String nome,
                                 @RequestParam(required = false) Double salarioMin,
                                 @RequestParam(required = false) Double salarioMax,
                                 Pageable pageable) {
        log.info("Listando cargos com filtros -> nome: {}, salarioMin: {}, salarioMax: {}", nome, salarioMin, salarioMax);
        return cargoService.listarComFiltros(new CargoFiltroDto(nome, salarioMin, salarioMax), pageable);
    }
}
