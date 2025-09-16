package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.filtros.FuncionarioFiltroDto;
import br.senai.prova_jwt.dto.mapper.FuncionarioMapper;
import br.senai.prova_jwt.dto.specification.FuncionarioSpecification;
import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.repository.CargoRepository;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository repoFuncionario;
    private final CargoRepository repoCargo;

    public FuncionarioDto salvar(FuncionarioDto dto) {
        Funcionario entity = FuncionarioMapper.toEntity(dto);

        if (dto.getCargoId() != null) {
            Cargo cargo = repoCargo.findById(dto.getCargoId()).orElse(null);
            entity.setCargo(cargo);
        }

        Funcionario salvo = repoFuncionario.save(entity);
        return FuncionarioMapper.toDto(salvo);
    }

    public void remover(Long id) {
        repoFuncionario.deleteById(id);
    }

    public FuncionarioDto buscarPorId(Long id) {
        return repoFuncionario.findById(id)
                .map(FuncionarioMapper::toDto)
                .orElse(null);
    }

    public List<FuncionarioDto> listarTodos() {
        return repoFuncionario.findAll().stream()
                .map(FuncionarioMapper::toDto)
                .toList();
    }

    public Page<FuncionarioDto> paginar(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return repoFuncionario.findAll(pageable).map(FuncionarioMapper::toDto);
    }

    public Page<FuncionarioDto> filtrar(FuncionarioFiltroDto filtro, Pageable pageable) {
        return repoFuncionario.findAll(FuncionarioSpecification.aplicarFiltros(filtro), pageable)
                .map(FuncionarioMapper::toDto);
    }
}