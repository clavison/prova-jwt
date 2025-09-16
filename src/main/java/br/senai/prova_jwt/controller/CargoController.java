package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.CargoDTO;
import br.senai.prova_jwt.dto.CargoFiltroDTO;
import br.senai.prova_jwt.mapper.CargoMapper;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.service.CargoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @PostMapping
    public CargoDTO criar(@Valid @RequestBody CargoDTO cargoDTO) {
        Cargo cargo = CargoMapper.toEntity(cargoDTO);
        Cargo salvo = cargoService.criar(cargo);
        return CargoMapper.toDTO(salvo);
    }

    @PostMapping("/lote")
    public List<CargoDTO> criarEmLote(@Valid @RequestBody List<CargoDTO> cargosDTO) {
        List<Cargo> cargos = cargosDTO.stream()
                .map(CargoMapper::toEntity)
                .collect(Collectors.toList());

        List<Cargo> salvos = cargoService.criarEmLote(cargos);

        return salvos.stream()
                .map(CargoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public Page<CargoDTO> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Double salarioMin,
            @RequestParam(required = false) Double salarioMax,
            Pageable pageable
    ) {
        CargoFiltroDTO filtro = new CargoFiltroDTO();
        filtro.setNome(nome);
        filtro.setSalarioMin(salarioMin);
        filtro.setSalarioMax(salarioMax);

        Page<Cargo> page = cargoService.pegarCargosPaginado(filtro, pageable);
        return page.map(CargoMapper::toDTO);
    }

    @PutMapping("/{id}")
    public CargoDTO atualizar(@PathVariable Long id, @Valid @RequestBody CargoDTO cargoDTO) {
        Cargo cargo = CargoMapper.toEntity(cargoDTO);
        Cargo atualizado = cargoService.atualizar(id, cargo);
        return CargoMapper.toDTO(atualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        cargoService.deletar(id);
    }
}

