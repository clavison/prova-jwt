package com.prova.rayssa.service;

import com.prova.rayssa.dto.CargoDTO;
import com.prova.rayssa.dto.mapper.CargoMapper;
import com.prova.rayssa.model.Cargo;
import com.prova.rayssa.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CargoService {
    
    @Autowired
    private CargoRepository cargoRepository;

    public CargoDTO salvar(CargoDTO dto) {
        Cargo cargo = CargoMapper.toEntity(dto);
        Cargo salvo = cargoRepository.save(cargo);
        return CargoMapper.toDto(salvo);
    }
    
    public CargoDTO atualizar(Long id, CargoDTO dto) {
        Optional<Cargo> cargoExistente = cargoRepository.findById(id);
        
        if (cargoExistente.isEmpty()) {
            throw new RuntimeException("Cargo não encontrado com ID: " + id);
        }
        
        Cargo cargo = cargoExistente.get();
        CargoMapper.updateEntityFromDto(dto, cargo);
        
        Cargo atualizado = cargoRepository.save(cargo);
        return CargoMapper.toDto(atualizado);
    }

    @Transactional(readOnly = true)
    public List<CargoDTO> listarTodos() {
        List<Cargo> cargos = cargoRepository.findAll();
        return cargos.stream()
                .map(CargoMapper::toDto)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Page<CargoDTO> listarTodosComPaginacao(Pageable pageable) {
        Page<Cargo> cargos = cargoRepository.findAll(pageable);
        return cargos.map(CargoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public CargoDTO buscarPorId(Long id) {
        Optional<Cargo> cargo = cargoRepository.findById(id);
        return cargo.map(CargoMapper::toDto).orElse(null);
    }
    
    public void excluir(Long id) {
        Optional<Cargo> cargo = cargoRepository.findById(id);
        
        if (cargo.isEmpty()) {
            throw new RuntimeException("Cargo não encontrado com ID: " + id);
        }
        
        // Verificar se há funcionários associados
        if (!cargo.get().getFuncionarios().isEmpty()) {
            throw new RuntimeException("Não é possível excluir cargo com funcionários associados");
        }
        
        cargoRepository.deleteById(id);
    }
}
