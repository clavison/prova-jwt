package br.senai.prova_jwt.domains.funcionario;

import br.senai.prova_jwt.domains.cargo.Cargo;
import br.senai.prova_jwt.domains.cargo.CargoMapper;
import br.senai.prova_jwt.domains.cargo.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private CargoService cargoService;

    public FuncionarioDTO salvar(FuncionarioDTO dto) {
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

    public FuncionarioDTO buscarPorId(Long id) {
        Funcionario funcionario = repository.findById(id).orElse(null);
        return FuncionarioMapper.toDto(funcionario);
    }

    public Page<FuncionarioDTO> listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Funcionario> funcionarios = repository.findAll(pageable);
        return funcionarios.map(FuncionarioMapper::toDto);
    }

    public Page<FuncionarioDTO> listarComFiltros(FuncionarioFiltroDTO filtro, Pageable pageable) {
        Page<Funcionario> funcionarios = repository.findAll(FuncionarioSpecification.comFiltros(filtro), pageable);
        return funcionarios.map(FuncionarioMapper::toDto);
    }
}
