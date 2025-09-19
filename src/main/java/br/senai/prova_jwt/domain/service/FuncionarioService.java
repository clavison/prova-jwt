package br.senai.prova_jwt.domain.service;


import br.senai.prova_jwt.application.dto.FuncionarioDto;
import br.senai.prova_jwt.application.dto.filtros.FuncionarioFiltroDto;
import br.senai.prova_jwt.domain.specifications.FuncionarioSpecification;
import br.senai.prova_jwt.mapper.FuncionarioMapper;
import br.senai.prova_jwt.domain.model.Cargo;
import br.senai.prova_jwt.domain.model.Funcionario;
import br.senai.prova_jwt.domain.repository.CargoRepository;
import br.senai.prova_jwt.domain.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final CargoRepository cargoRepository;

    public FuncionarioDto salvar(FuncionarioDto dto) {
        Cargo cargo = cargoRepository.findById(dto.getCargoId()).orElse(null);
        Funcionario funcionario = FuncionarioMapper.toEntity(dto, cargo);
        Funcionario salvo = funcionarioRepository.save(funcionario);
        return FuncionarioMapper.toDto(salvo);
    }

    public void excluir(Long id) {
        funcionarioRepository.deleteById(id);
    }

    public FuncionarioDto buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .map(FuncionarioMapper::toDto)
                .orElse(null);
    }

    public List<FuncionarioDto> buscarTodos() {
        return funcionarioRepository.findAll()
                .stream().map(FuncionarioMapper::toDto).toList();
    }

    // paginacao
    public Page<FuncionarioDto> listarComFiltros(FuncionarioFiltroDto filtro, Pageable pageable) {
        // Busca funcionários com filtros aplicados e paginação
        Page<Funcionario> funcionarios = funcionarioRepository.findAll(
                FuncionarioSpecification.comFiltros(filtro), // Aplica filtros dinâmicos
                pageable // Controla paginação (page, size, sort)
        );

        // Converte entidades para DTOs mantendo a estrutura paginada
        return funcionarios.map(FuncionarioMapper::toDto);
    }
}