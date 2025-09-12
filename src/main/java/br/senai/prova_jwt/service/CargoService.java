package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.mapper.CargoMapper;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private final CargoRepository repository;

    public CargoService(CargoRepository repository) {
        this.repository = repository;
    }

    public CargoDto salvar(CargoDto cargoDto) {
        Cargo cargo = CargoMapper.toEntity(cargoDto);
        Cargo cargoNovo = repository.save(cargo);
        return CargoMapper.toDto(cargoNovo);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public CargoDto buscarPorId(Long id) {
        Cargo cargo = repository.findById(id).get();
        return CargoMapper.toDto(cargo);
    }

    public List<CargoDto> buscarTodos() {
        return repository.findAll().stream().map(CargoMapper::toDto).toList();
    }
}
