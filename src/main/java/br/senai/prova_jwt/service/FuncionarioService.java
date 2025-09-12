package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.mapper.FuncionarioMapper;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import br.senai.prova_jwt.dto.specifications.FuncionarioSpecification;
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

    public FuncionarioDto criar(FuncionarioDto dto) {
        Funcionario entity = new Funcionario();
        entity.setNome(dto.getNome());
        entity.setCargo(dto.getCargo());
        entity = repository.save(entity);
        return FuncionarioMapper.toDto(entity);
    }

    public FuncionarioDto atualizar(Long id, FuncionarioDto dto) {
        Funcionario entity = repository.findById(id).orElseThrow();
        entity.setNome(dto.getNome());
        entity.setCargo(dto.getCargo());
        entity = repository.save(entity);
        return FuncionarioMapper.toDto(entity);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public FuncionarioDto buscarPorId(Long id) {
        return repository.findById(id).map(FuncionarioMapper::toDto).orElseThrow();
    }

    public List<FuncionarioDto> buscarTodos() {
        return repository.findAll().stream().map(FuncionarioMapper::toDto).toList();
    }

    public Page<FuncionarioDto> buscarPaginado(Pageable pageable) {
        return repository.findAll(pageable)
                .map(FuncionarioMapper::toDto);
    }

    public Page<FuncionarioDto> listarComFiltros(FuncionarioDto filtro, Pageable pageable) {
        return repository.findAll(
                FuncionarioSpecification.comFiltros(filtro),
                pageable
        ).map(FuncionarioMapper::toDto);
    }

}
