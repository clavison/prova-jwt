package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.mapper.RoleMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.repository.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleDto criar(@Valid RoleDto requestDTO) {
        if (roleRepository.existsByNomeIgnoreCase(requestDTO.getNome())) {
            throw new IllegalArgumentException("Já existe uma role com o nome: " + requestDTO.getNome());
        }

        Role role = RoleMapper.toEntity(requestDTO);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toDto(savedRole);
    }

    public RoleDto buscarPorId(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role não encontrada com ID: " + id));
        return RoleMapper.toDto(role);
    }

    public Page<RoleDto> listar(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(RoleMapper::toDto);
    }

    public RoleDto atualizar(Long id, @Valid RoleDto requestDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role não encontrada com ID: " + id));

        if (roleRepository.existsByNomeIgnoreCaseAndIdNot(requestDTO.getNome(), id)) {
            throw new IllegalArgumentException("Já existe uma role com o nome: " + requestDTO.getNome());
        }

        alterarValoresDo(role, requestDTO);
        Role updatedRole = roleRepository.save(role);
        return RoleMapper.toDto(updatedRole);
    }

    public void deletar(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("Role não encontrada com ID: " + id);
        }

        try {
            roleRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Não é possível deletar esta role pois ela está sendo utilizada", e);
        }
    }

    private void alterarValoresDo(Role role, RoleDto roleDto) {
        if (role == null || roleDto == null) {
            return;
        }

        role.setNome(roleDto.getNome());
    }
}
