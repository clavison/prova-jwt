package br.senai.prova_jwt.controller;


import br.senai.prova_jwt.dto.CargoFiltroDTO;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.repository.CargoRepository;
import br.senai.prova_jwt.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @Autowired
    private CargoRepository cargoRepository;

    @PostMapping
    public Cargo criar(@RequestBody Cargo cargo) {
        return cargoService.criar(cargo);
    }

    @PostMapping("/lote")
    public List<Cargo> criarEmLote(@RequestBody List<Cargo> cargos) {
        return cargoRepository.saveAll(cargos);
    }

    @GetMapping
    public Page<Cargo> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Double salarioMin,
            @RequestParam(required = false) Double salarioMax,
            Pageable pageable
    ) {
        CargoFiltroDTO filtro = new CargoFiltroDTO();
        filtro.setNome(nome);
        filtro.setSalarioMin(salarioMin);
        filtro.setSalarioMax(salarioMax);

        return cargoService.pegarCargosPaginado(filtro, pageable);
    }

}
