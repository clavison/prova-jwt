package br.senai.prova_jwt.domains.auth.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public RoleDTO salvar(RoleDTO dto) {
        Role role = RoleMapper.toEntity(dto);
        Role salvo = repository.save(role);
        return RoleMapper.toDto(salvo);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public RoleDTO buscarPorId(Long id) {
        Role role = repository.findById(id).orElse(null);
        return RoleMapper.toDto(role);
    }

    public Page<RoleDTO> listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> roles = repository.findAll(pageable);
        return roles.map(RoleMapper::toDto);
    }

    public RoleDTO atualizar(Long id, RoleDTO dto) {
        Role role = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        role.setDescricao(dto.getDescricao());

        Role salvo = repository.save(role);
        return RoleMapper.toDto(salvo);
    }

    public Page<RoleDTO> listarComFiltros(RoleFiltroDTO filtro, Pageable pageable) {
        Page<Role> roles = repository.findAll(RoleSpecification.comFiltros(filtro), pageable);
        return roles.map(RoleMapper::toDto);
    }

    public void criarRolesPadrao() {
        if (repository.count() > 0) return; 

        Role roleUser = new Role();
        roleUser.setDescricao("USER");
        repository.save(roleUser);

        Role roleAdmin = new Role();
        roleAdmin.setDescricao("ADMIN");
        repository.save(roleAdmin);
    }
}