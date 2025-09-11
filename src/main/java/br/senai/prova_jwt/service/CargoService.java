package br.senai.prova_jwt.service;


import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.mapper.CargoMapper;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository repository;

    public CargoDto salvar(CargoDto dto) {
        Cargo cargo = CargoMapper.toEntity(dto);
        Cargo salvo = repository.save(cargo);
        return CargoMapper.toDto(salvo);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public CargoDto buscarPorId(Long id) {
        return repository.findById(id)
                .map(CargoMapper::toDto)
                .orElse(null);
    }

    public List<CargoDto> buscarTodos() {
        return repository.findAll()
                .stream().map(CargoMapper::toDto).toList();
    }
}