package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.CargoFiltroDto;
import br.senai.prova_jwt.dto.specifications.CargoSpecification;
import br.senai.prova_jwt.mapper.CargoMapper;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.repository.CargoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public CargoDto salvar(CargoDto cargoDto) {
        Cargo cargoSalvo = cargoRepository.save(CargoMapper.toEntity(cargoDto));
        return CargoMapper.toDto(cargoSalvo);
    }

    public Page<CargoDto> listarComFiltros(CargoFiltroDto filtro, Pageable pageable) {
        return cargoRepository.findAll(CargoSpecification.comFiltros(filtro), pageable)
                .map(CargoMapper::toDto);
    }

    public Optional<CargoDto> buscarPorId(Long id) {
        Optional<Cargo> cargo = cargoRepository.findById(id);
        return cargo.map(CargoMapper::toDto);
    }

    public CargoDto alterar(Long id, CargoDto cargoDto) {
        Optional<Cargo> cargoOpt = cargoRepository.findById(id);
        if (cargoOpt.isEmpty()) {
            throw new RuntimeException("Cargo não encontrado");
        }

        Cargo cargoExistente = cargoOpt.get();

        if (cargoDto.getNome() != null) {
            cargoExistente.setNome(cargoDto.getNome());
        }
        if (cargoDto.getSalario() != null) {
            cargoExistente.setSalario(cargoDto.getSalario());
        }

        Cargo cargoSalvo = cargoRepository.save(cargoExistente);
        return CargoMapper.toDto(cargoSalvo);
    }

    public void excluir(Long id) {
        cargoRepository.deleteById(id);
    }
}
