package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.dto.mapper.UsuarioMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.RoleRepository;
import br.senai.prova_jwt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDto buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(UsuarioMapper::toDto).orElseThrow();
    }

    public Usuario salvar(UsuarioDto dto) {
        if (usuarioRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Login já está em uso.");
        }

        if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Usuário deve ter pelo menos uma role.");
        }

        Usuario user = new Usuario();
        user.setLogin(dto.getLogin());
        user.setSenha(passwordEncoder.encode(dto.getSenha()));

        Set<Role> roles = dto.getRoles().stream()
                .map(nome -> roleRepository.findByNome(nome).orElseThrow())
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return usuarioRepository.save(user);
    }

    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll().stream().map(usuario -> {
            UsuarioDto dto = new UsuarioDto(
                    usuario.getId(),
                    usuario.getLogin(),
                    null,
                    usuario.getRoles().stream()
                            .map(Role::getNome)
                            .collect(Collectors.toSet())
            );
            return dto;
        }).toList();
    }

    public UsuarioDto atualizar(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setLogin(dto.getLogin());
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(nome -> roleRepository.findByNome(nome).orElseThrow())
                    .collect(Collectors.toSet());
            usuario.setRoles(roles);
        }
        return UsuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
}
