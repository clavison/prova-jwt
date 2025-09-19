package com.prova.rayssa.service;

import com.prova.rayssa.dto.FuncionarioDTO;
import com.prova.rayssa.dto.FuncionarioFiltroDTO;
import com.prova.rayssa.dto.mapper.FuncionarioMapper;
import com.prova.rayssa.dto.specification.FuncionarioSpecification;
import com.prova.rayssa.model.Cargo;
import com.prova.rayssa.model.Funcionario;
import com.prova.rayssa.repository.CargoRepository;
import com.prova.rayssa.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FuncionarioService {
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @Autowired
    private CargoRepository cargoRepository;

    public FuncionarioDTO salvar(FuncionarioDTO dto) {
        Funcionario funcionario = FuncionarioMapper.toEntity(dto);
        
        // Buscar e setar o cargo
        if (dto.getCargoId() != null) {
            Optional<Cargo> cargo = cargoRepository.findById(dto.getCargoId());
            if (cargo.isPresent()) {
                funcionario.setCargo(cargo.get());
            } else {
                throw new RuntimeException("Cargo não encontrado com ID: " + dto.getCargoId());
            }
        }
        
        Funcionario salvo = funcionarioRepository.save(funcionario);
        return FuncionarioMapper.toDto(salvo);
    }
    
    public FuncionarioDTO atualizar(Long id, FuncionarioDTO dto) {
        Optional<Funcionario> funcionarioExistente = funcionarioRepository.findById(id);
        
        if (funcionarioExistente.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado com ID: " + id);
        }
        
        Funcionario funcionario = funcionarioExistente.get();
        FuncionarioMapper.updateEntityFromDto(dto, funcionario);
        
        // Atualizar cargo se necessário
        if (dto.getCargoId() != null && !dto.getCargoId().equals(funcionario.getCargo().getId())) {
            Optional<Cargo> cargo = cargoRepository.findById(dto.getCargoId());
            if (cargo.isPresent()) {
                funcionario.setCargo(cargo.get());
            } else {
                throw new RuntimeException("Cargo não encontrado com ID: " + dto.getCargoId());
            }
        }
        
        Funcionario atualizado = funcionarioRepository.save(funcionario);
        return FuncionarioMapper.toDto(atualizado);
    }

    @Transactional(readOnly = true)
    public List<FuncionarioDTO> listarTodos() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .map(FuncionarioMapper::toDto)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Page<FuncionarioDTO> listarTodosComPaginacao(Pageable pageable) {
        Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
        return funcionarios.map(FuncionarioMapper::toDto);
    }
    
    @Transactional(readOnly = true)
    public Page<FuncionarioDTO> buscarComFiltros(FuncionarioFiltroDTO filtro, Pageable pageable) {
        Page<Funcionario> funcionarios = funcionarioRepository.findAll(
            FuncionarioSpecification.comFiltros(filtro), 
            pageable
        );
        return funcionarios.map(FuncionarioMapper::toDto);
    }
    
    @Transactional(readOnly = true)
    public List<FuncionarioDTO> buscarComFiltrosSemPaginacao(FuncionarioFiltroDTO filtro) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll(
            FuncionarioSpecification.comFiltros(filtro)
        );
        return funcionarios.stream()
                .map(FuncionarioMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public FuncionarioDTO buscarPorId(Long id) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return funcionario.map(FuncionarioMapper::toDto).orElse(null);
    }
    
    public void excluir(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado com ID: " + id);
        }
        funcionarioRepository.deleteById(id);
    }
}
