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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDto buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioMapper::toDto)
                .orElse(null);
    }

    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll()
                .stream().map(UsuarioMapper::toDto).toList();
    }

    public UsuarioDto buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .map(UsuarioMapper::toDto)
                .orElse(null);
    }
}