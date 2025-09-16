package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.dto.mapper.RoleMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleDto salvar(RoleDto roleDto) {
        Role role = roleRepository.save(RoleMapper.toEntity(roleDto));
        return RoleMapper.toDto(role);
    }

    public Optional<RoleDto> buscarPorId(Long id) {
        Optional<Role> cargo = roleRepository.findById(id);
        return cargo.map(RoleMapper::toDto);
    }

    public void excluir(Long id) {
        roleRepository.deleteById(id);
    }

}
