package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.RoleDto;
import br.senai.prova_jwt.dto.mapper.RoleMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public RoleDto salvar(RoleDto dto) {
        Role role = RoleMapper.toEntity(dto);
        Role salvo = repository.save(role);
        return RoleMapper.toDto(salvo);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public RoleDto buscarPorId(Long id) {
        Role role = repository.findById(id).orElse(null);
        return RoleMapper.toDto(role);
    }

    public List<RoleDto> buscarTodos() {
        List<Role> roles = repository.findAll();
        return roles.stream().map(RoleMapper::toDto).toList();
    }

    public Page<Role> getRolesPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
}
