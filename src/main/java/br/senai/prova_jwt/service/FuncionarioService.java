package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.FuncionarioFiltroDto;
import br.senai.prova_jwt.dto.mapper.FuncionarioMapper;
import br.senai.prova_jwt.dto.specifications.FuncionarioSpecification;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public FuncionarioDto salvar(FuncionarioDto funcionarioDto) {
        log.info("Salvando funcionário: {}", funcionarioDto.getNome());
        Funcionario funcionario = funcionarioRepository.save(FuncionarioMapper.toEntity(funcionarioDto));
        log.info("Funcionário salvo com sucesso. ID: {}", funcionario.getId());
        return FuncionarioMapper.toDto(funcionario);
    }

    public Optional<FuncionarioDto> buscarPorId(Long id) {
        log.info("Buscando funcionário pelo ID: {}", id);
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        if (funcionario.isEmpty()) {
            log.warn("Funcionário não encontrado. ID: {}", id);
        }
        return funcionario.map(FuncionarioMapper::toDto);
    }

    public void excluir(Long id) {
        log.info("Excluindo funcionário com ID: {}", id);
        funcionarioRepository.deleteById(id);
        log.info("Funcionário excluído com sucesso. ID: {}", id);
    }

    public Page<FuncionarioDto> listarComFiltros(FuncionarioFiltroDto filtro, Pageable pageable) {
        return funcionarioRepository.findAll(FuncionarioSpecification.comFiltros(filtro), pageable)
                .map(FuncionarioMapper::toDto);
    }

}
