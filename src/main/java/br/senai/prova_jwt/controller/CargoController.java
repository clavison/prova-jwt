package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.CargoFiltroDto;
import br.senai.prova_jwt.dto.mapper.CargoMapper;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.service.CargoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @PostMapping
    public CargoDto criar(@Valid @RequestBody CargoDto cargoDTO) {
        Cargo cargo = CargoMapper.toEntity(cargoDTO);
        Cargo salvo = cargoService.criar(cargo);
        return CargoMapper.toDTO(salvo);
    }

    @PostMapping("/lote")
    public List<CargoDto> criarEmLote(@Valid @RequestBody List<CargoDto> cargosDTO) {
        List<Cargo> cargos = cargosDTO.stream()
                .map(CargoMapper::toEntity)
                .collect(Collectors.toList());

        List<Cargo> salvos = cargoService.criarEmLote(cargos);

        return salvos.stream()
                .map(CargoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public Page<CargoDto> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Double salarioMin,
            @RequestParam(required = false) Double salarioMax,
            Pageable pageable
    ) {
        CargoFiltroDto filtro = new CargoFiltroDto();
        filtro.setNome(nome);
        filtro.setSalarioMin(salarioMin);
        filtro.setSalarioMax(salarioMax);

        Page<Cargo> page = cargoService.pegarCargosPaginado(filtro, pageable);
        return page.map(CargoMapper::toDTO);
    }

    @PutMapping("/{id}")
    public CargoDto atualizar(@PathVariable Long id, @Validated @RequestBody CargoDto cargoDTO) {
        Cargo cargo = CargoMapper.toEntity(cargoDTO);
        Cargo atualizado = cargoService.atualizar(id, cargo);
        return CargoMapper.toDTO(atualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        cargoService.deletar(id);
    }
}
