package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.FuncionarioDto;
import br.senai.prova_jwt.dto.UsuarioDto;
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

    public FuncionarioDto buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(Mapper::toDto).orElseThrow();
    }

    public Usuario salvar(UsuarioDto dto) {
        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Set<Role> roles = dto.getRoles().stream()
                .map(nome -> roleRepository.findByNome(nome).orElseThrow())
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return usuarioRepository.save(user);
    }

    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll().stream().map(usuario -> {
                UsuarioDto dto = new UsuarioDto(usuario.getId(), usuario.getUsername(), usuario.getPassword(), usuario.getRoles());
        dto.setUsername(usuario.getUsername());

        dto.setRoles(usuario.getRoles().stream().map(Role ::getNome).collect(Collectors.toSet()));
        return dto;
        }).toList();
    }

    public void atualizar(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(nome -> roleRepository.findByNome(nome).orElseThrow())
                    .collect(Collectors.toSet());
            usuario.setRoles(roles);
        }
        usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
}
