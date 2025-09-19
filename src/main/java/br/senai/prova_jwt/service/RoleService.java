package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.mapper.RoleMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleDto criar(Role roleDto) {
        if (roleDto.getDescricao() == null || roleDto.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Não pode ser vazio.");
        }
        Optional<Role> roleExistente = roleRepository.findByDescricao(roleDto.getDescricao());
        if (roleExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe uma role com a descricao: " + roleDto.getDescricao());
        }
        Role salvo = roleRepository.save(RoleMapper.toEntity(roleDto));
        return RoleMapper.toDto(salvo);
    }

    public List<RoleDto> listarTodas() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoleDto buscarPorId(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role não encontrada com o id: " + id));
        return RoleMapper.toDto(role);
    }

    public RoleDto buscarPordescricao(String descricao) {
        Role role = roleRepository.findByDescricao(descricao)
                .orElseThrow(() -> new RuntimeException("Role não encontrada com a descricao: " + descricao));
        return RoleMapper.toDto(role);
    }

    public RoleDto atualizar(Long id, Role roleDto) {
        return roleRepository.findById(id)
                .map(roleExistente -> {
                    roleExistente.setDescricao(roleDto.getDescricao());
                    return RoleMapper.toDto(roleRepository.save(roleExistente));
                })
                .orElseThrow(() -> new RuntimeException("Role não encontrada com o id: " + id));
    }

    public void deletar(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role não encontrada com o id: " + id);
        }
        roleRepository.deleteById(id);
    }
}
