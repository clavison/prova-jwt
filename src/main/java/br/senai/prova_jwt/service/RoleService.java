package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.dto.mapper.RoleMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repoRole;

    public RoleDto salvar(RoleDto dto) {
        Role entity = RoleMapper.toEntity(dto);
        return RoleMapper.toDto(repoRole.save(entity));
    }

    public void remover(Long idRole) {
        repoRole.deleteById(idRole);
    }

    public RoleDto buscarPorId(Long idRole) {
        return repoRole.findById(idRole)
                .map(RoleMapper::toDto)
                .orElse(null);
    }

    public List<RoleDto> listarTodos() {
        return repoRole.findAll().stream()
                .map(RoleMapper::toDto)
                .toList();
    }
}
