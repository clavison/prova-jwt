package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.filters.FuncionarioFilterDto;
import br.senai.prova_jwt.dto.mapper.CargoMapper;
import br.senai.prova_jwt.dto.mapper.FuncionarioMapper;
import br.senai.prova_jwt.dto.specification.FuncionarioSpecification;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private CargoService cargoService;

    public FuncionarioDto salvar(FuncionarioDto dto) {
        Funcionario funcionario = FuncionarioMapper.toEntity(dto);

        // Buscar o cargo pelo ID se fornecido
        if (dto.getCargo() != null && dto.getCargo().getId() != null) {
            Cargo cargo = cargoService.buscarPorId(dto.getCargo().getId()) != null ?
                    CargoMapper.toEntity(cargoService.buscarPorId(dto.getCargo().getId())) : null;
            funcionario.setCargo(cargo);
        }

        Funcionario salvo = repository.save(funcionario);
        return FuncionarioMapper.toDto(salvo);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public FuncionarioDto buscarPorId(Long id) {
        Funcionario funcionario = repository.findById(id).orElse(null);
        return FuncionarioMapper.toDto(funcionario);
    }

//    public List<FuncionarioDto> buscarTodos() {
//        List<Funcionario> funcionarios = repository.findAll();
//        return funcionarios.stream().map(FuncionarioMapper::toDto).toList();
//    }

    public List<FuncionarioDto> buscarPorSalarioBetween(Double salarioMin, Double salarioMax) {
        List<Funcionario> funcionarios = repository.findByCargoSalarioBetween(salarioMin, salarioMax);
        return funcionarios.stream().map(FuncionarioMapper::toDto).toList();
    }

    public Page<Funcionario> getFuncionariosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    public Page<Funcionario> listarComFiltros(FuncionarioFilterDto filtro, Pageable pageable) {
        return repository.findAll(FuncionarioSpecification.comFiltros(filtro), pageable);
    }
}

