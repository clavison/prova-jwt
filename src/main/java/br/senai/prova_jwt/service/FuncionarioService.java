package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.CargoDto;
import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.FuncionarioFiltroDto;
import br.senai.prova_jwt.dto.specifications.FuncionarioSpecification;
import br.senai.prova_jwt.mapper.FuncionarioMapper;
import br.senai.prova_jwt.model.Funcionario;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.repository.FuncionarioRepository;
import br.senai.prova_jwt.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final RoleRepository roleRepository;
    private final CargoService cargoService;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            RoleRepository roleRepository,
            CargoService cargoService,
            PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.roleRepository = roleRepository;
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

        Set<Role> roles = validarRoles(funcionarioDto);

        Funcionario funcionario = FuncionarioMapper.toEntity(funcionarioDto);
        funcionario.setRoles(roles);

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
        return FuncionarioMapper.toDto(funcionarioSalvo);
    }

    public Page<FuncionarioDto> listarComFiltros(FuncionarioFiltroDto filtro, Pageable pageable, Authentication authentication) {
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return funcionarioRepository.findAll(FuncionarioSpecification.comFiltros(filtro), pageable)
                    .map(FuncionarioMapper::toDto);
        } else {
            filtro.setUsername(username);
            return funcionarioRepository.findAll(FuncionarioSpecification.comFiltros(filtro), pageable)
                    .map(FuncionarioMapper::toDto);
        }
    }

    public FuncionarioDto alterar(Long id, FuncionarioDto funcionarioDto, Authentication authentication) {
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
        if (funcionarioOpt.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado");
        }

        Funcionario funcionarioExistente = funcionarioOpt.get();

        if (!isAdmin && !funcionarioExistente.getLogin().equals(username)) {
            throw new SecurityException("Acesso negado: você só pode alterar seus próprios dados");
        }

        if (funcionarioDto.getCargo() != null && funcionarioDto.getCargo().getId() != null) {
            Optional<CargoDto> cargoDto = cargoService.buscarPorId(funcionarioDto.getCargo().getId());
            if (cargoDto.isEmpty()) {
                throw new IllegalArgumentException("Cargo não encontrado");
            }
        }

        alterarValoresDo(funcionarioDto);

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionarioExistente);
        return FuncionarioMapper.toDto(funcionarioSalvo);
    }

    public FuncionarioDto buscarPorId(Long id, Authentication authentication) {
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
        if (funcionarioOpt.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado");
        }

        Funcionario funcionario = funcionarioOpt.get();

        if (!isAdmin && !funcionario.getLogin().equals(username)) {
            throw new SecurityException("Acesso negado: você só pode visualizar seus próprios dados");
        }

        return FuncionarioMapper.toDto(funcionario);
    }

    public void excluir(Long id) {
        funcionarioRepository.deleteById(id);
    }

    private Set<Role> validarRoles(FuncionarioDto funcionarioDto) {
        Set<Role> roles = new HashSet<>();

        funcionarioDto.getRoles().forEach(roleDto -> {
            Optional<Role> role = roleRepository.findByNome(roleDto);

            if (role.isEmpty()) {
                throw new IllegalArgumentException("Não foi encontrado a role vinculada: " + roleDto);
            }

            roles.add(role.get());
        });

        return roles;
    }

    private void alterarValoresDo(FuncionarioDto funcionarioDto) {
        if (funcionarioDto.getUsername() != null) {
            funcionarioDto.setUsername(funcionarioDto.getUsername());
        }

        if (funcionarioDto.getPassword() != null) {
            funcionarioDto.setPassword(passwordEncoder.encode(funcionarioDto.getPassword()));
        }

        if (funcionarioDto.getCargo() != null) {
            funcionarioDto.setCargo(funcionarioDto.getCargo());
        }
    }
}
