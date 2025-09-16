package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.filtros.CargoFiltroDto;
import br.senai.prova_jwt.dto.mapper.CargoMapper;
import br.senai.prova_jwt.dto.specification.CargoSpecification;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepo;

    public CargoDto salvar(CargoDto cargoDto) {
        Cargo entity = CargoMapper.toEntity(cargoDto);
        Cargo salvo = cargoRepo.save(entity);
        return CargoMapper.toDto(salvo);
    }

    public void remover(Long idCargo) {
        cargoRepo.deleteById(idCargo);
    }

    public CargoDto buscarPorId(Long idCargo) {
        return cargoRepo.findById(idCargo)
                .map(CargoMapper::toDto)
                .orElse(null);
    }

    public List<CargoDto> listarTodos() {
        return cargoRepo.findAll()
                .stream()
                .map(CargoMapper::toDto)
                .toList();
    }

    public Page<CargoDto> paginar(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return cargoRepo.findAll(pageable).map(CargoMapper::toDto);
    }

    public Page<CargoDto> filtrar(CargoFiltroDto filtro, Pageable pageable) {
        return cargoRepo.findAll(CargoSpecification.aplicarFiltros(filtro), pageable)
                .map(CargoMapper::toDto);
    }
}
