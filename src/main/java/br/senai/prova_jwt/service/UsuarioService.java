package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.dto.mapper.UsuarioMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.RoleRepository;
import br.senai.prova_jwt.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repoUsuario;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public Usuario salvar(UsuarioDto dto) {
        Set<Role> papeis = new HashSet<>();
        if (dto.getPerfis() != null) {
            for (String p : dto.getPerfis()) {
                Role r = roleRepo.findByNomeRole(p).orElseGet(() -> {
                    Role novo = new Role();
                    novo.setNomeRole(p);
                    return roleRepo.save(novo);
                });
                papeis.add(r);
            }
        }

        Usuario u = UsuarioMapper.converterParaEntidade(dto, papeis);
        if (u.getSenha() != null) {
            u.setSenha(encoder.encode(u.getSenha()));
        }

        return repoUsuario.save(u);
    }

    public List<UsuarioDto> listar() {
        return repoUsuario.findAll().stream().map(UsuarioMapper::toDto).collect(Collectors.toList());
    }

    public UsuarioDto buscarPorUsuario(String usuario) {
        return repoUsuario.findByUsuario(usuario).map(UsuarioMapper::toDto).orElse(null);
    }

    public UsuarioDto buscarPorId(Long id) {
        return repoUsuario.findById(id).map(UsuarioMapper::toDto).orElse(null);
    }

    public void excluir(Long id) {
        repoUsuario.deleteById(id);
    }
}