package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.mapper.CargoMapper;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository repository;

    public CargoDto salvar(CargoDto dto) {
        Cargo cargo = CargoMapper.toEntity(dto);
        Cargo salvo = repository.save(cargo);
        return CargoMapper.toDto(salvo);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public CargoDto buscarPorId(Long id) {
        Cargo cargo = repository.findById(id).get();
        return CargoMapper.toDto(cargo);
    }

    public List<CargoDto> buscarTodos() {
        List<Cargo> cargos = repository.findAll();
        return cargos.stream().map(CargoMapper::toDto).toList();
    }

    public Page<Cargo> getCargosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

}
