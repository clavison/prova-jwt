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

    private final RoleRepository repository;

    public RoleDto salvar(RoleDto dto) {
        Role role = RoleMapper.toEntity(dto);
        Role salvo = repository.save(role);
        return RoleMapper.toDto(salvo);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public RoleDto buscarPorId(Long id) {
        return repository.findById(id)
                .map(RoleMapper::toDto)
                .orElse(null);
    }

    public List<RoleDto> buscarTodos() {
        return repository.findAll()
                .stream().map(RoleMapper::toDto).toList();
    }
}
