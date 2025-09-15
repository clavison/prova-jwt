package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.dto.mapper.RoleMapper;
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

    public RoleDto criar(Role roleDTO) {
        if (roleDTO.getDescricao() == null || roleDTO.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("A descricao da role não pode ser vazio.");
        }
        Optional<Role> roleExistente = roleRepository.findByDescricao(roleDTO.getDescricao());
        if (roleExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe uma role com a descricao: " + roleDTO.getDescricao());
        }
        Role salvo = roleRepository.save(RoleMapper.toEntity(roleDTO));
        return RoleMapper.toDTO(salvo);
    }

    public List<RoleDto> listarTodas() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoleDto buscarPorId(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role não encontrada com o id: " + id));
        return RoleMapper.toDTO(role);
    }

    public RoleDto buscarPordescricao(String descricao) {
        Role role = roleRepository.findByDescricao(descricao)
                .orElseThrow(() -> new RuntimeException("Role não encontrada com a descricao: " + descricao));
        return RoleMapper.toDTO(role);
    }

    public RoleDto atualizar(Long id, Role roleDTO) {
        return roleRepository.findById(id)
                .map(roleExistente -> {
                    roleExistente.setDescricao(roleDTO.getDescricao());
                    return RoleMapper.toDTO(roleRepository.save(roleExistente));
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
