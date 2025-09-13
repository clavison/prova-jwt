package br.senai.prova_jwt.domains.cargo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService service;

    @PostMapping
    public ResponseEntity<CargoDTO> criar(@RequestBody CargoDTO cargo) {
        CargoDTO salvo = service.salvar(cargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDTO> buscarPorId(@PathVariable Long id) {
        CargoDTO cargo = service.buscarPorId(id);
        if (cargo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cargo);
    }

    @GetMapping
    public Page<CargoDTO> buscarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.listar(page, size);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDTO> atualizar(@PathVariable Long id, @RequestBody CargoDTO cargo) {
        CargoDTO cargoBusca = service.buscarPorId(id);
        if (cargoBusca == null) {
            return ResponseEntity.notFound().build();
        } else {
            cargo.setId(id);
            return ResponseEntity.ok(service.salvar(cargo));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public Page<CargoDTO> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal salarioMin,
            @RequestParam(required = false) BigDecimal salarioMax,
            Pageable pageable
    ) {
        CargoFiltroDTO filtro = new CargoFiltroDTO();
        filtro.setNome(nome);
        filtro.setSalarioMin(salarioMin);
        filtro.setSalarioMax(salarioMax);
        return service.listarComFiltros(filtro, pageable);
    }
}
