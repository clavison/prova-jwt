package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.FuncionarioFiltroDto;
import br.senai.prova_jwt.dto.specifications.FuncionarioSpecification;
import br.senai.prova_jwt.mapper.CargoMapper;
import br.senai.prova_jwt.mapper.FuncionarioMapper;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import br.senai.prova_jwt.utils.AuthUtil;
import br.senai.prova_jwt.utils.RolesUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final CargoService cargoService;
    private final PasswordEncoder passwordEncoder;
    private final RolesUtil rolesUtil;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            CargoService cargoService,
            PasswordEncoder passwordEncoder, RolesUtil rolesUtil) {
        this.funcionarioRepository = funcionarioRepository;
        this.rolesUtil = rolesUtil;
        this.cargoService = cargoService;
        this.passwordEncoder = passwordEncoder;
    }

    public FuncionarioDto salvar(FuncionarioDto funcionarioDto) {
        Optional<CargoDto> cargoDto = cargoService.buscarPorId(funcionarioDto.getCargo().getId());
        if (cargoDto.isEmpty()) {
            throw new IllegalArgumentException("Não foi encontrado cargo vinculado ao id informado");
        }

        funcionarioDto.setCargo(cargoDto.get());
        funcionarioDto.setPassword(passwordEncoder.encode(funcionarioDto.getPassword()));

        Set<Role> roles = rolesUtil.validarRoles(funcionarioDto.getRoles());

        Funcionario funcionario = FuncionarioMapper.toEntity(funcionarioDto);
        funcionario.setRoles(roles);

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
        return FuncionarioMapper.toDto(funcionarioSalvo);
    }

    public Page<FuncionarioDto> listarComFiltros(FuncionarioFiltroDto filtro, Pageable pageable) {
        return funcionarioRepository.findAll(FuncionarioSpecification.comFiltros(filtro), pageable)
                .map(FuncionarioMapper::toDto);
    }

    public FuncionarioDto alterar(Long id, FuncionarioDto funcionarioDto) {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
        if (funcionarioOpt.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado");
        }

        Funcionario funcionarioExistente = funcionarioOpt.get();

        if (funcionarioDto.getCargo() != null && funcionarioDto.getCargo().getId() != null) {
            Optional<CargoDto> cargoDto = cargoService.buscarPorId(funcionarioDto.getCargo().getId());
            if (cargoDto.isEmpty()) {
                throw new IllegalArgumentException("Cargo não encontrado");
            }
        }

        alterarValoresDo(funcionarioDto, funcionarioExistente);

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionarioExistente);
        return FuncionarioMapper.toDto(funcionarioSalvo);
    }

    public FuncionarioDto buscarPorId(Long id, Authentication authentication) {
        String username = authentication.getName();

        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);

        if (funcionarioOpt.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado");
        }

        Funcionario funcionario = funcionarioOpt.get();

        if (!AuthUtil.isAdmin(authentication) && !funcionario.getLogin().equals(username)) {
            throw new SecurityException("Acesso negado: você só pode visualizar seus próprios dados");
        }

        return FuncionarioMapper.toDto(funcionario);
    }

    public void excluir(Long id) {
        funcionarioRepository.deleteById(id);
    }

    private void alterarValoresDo(FuncionarioDto funcionarioDto, Funcionario funcionario) {
        if (funcionarioDto.getUsername() != null) {
            funcionario.setLogin(funcionarioDto.getUsername());
        }

        if (funcionarioDto.getPassword() != null) {
            funcionario.setSenha(passwordEncoder.encode(funcionarioDto.getPassword()));
        }

        if (funcionarioDto.getCargo() != null) {
            funcionario.setCargo(CargoMapper.toEntity(funcionarioDto.getCargo()));
        }
    }
}
