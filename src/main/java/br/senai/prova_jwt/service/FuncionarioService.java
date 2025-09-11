package br.senai.prova_jwt.service;


import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.mapper.FuncionarioMapper;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.repository.CargoRepository;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
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
}