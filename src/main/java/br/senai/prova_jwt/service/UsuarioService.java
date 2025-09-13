package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.dto.UsuarioResponseDto;
import br.senai.prova_jwt.dto.mapper.UsuarioMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.RoleRepository;
import br.senai.prova_jwt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public UsuarioResponseDto salvar(UsuarioDto dto) {
        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Set<Role> roles = dto.getRoles().stream()
                .map(nome -> roleRepository.findByDescricao(nome).orElseThrow())
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return UsuarioMapper.toResponseDto(usuarioRepository.save(user));
    }

    public Optional<UsuarioResponseDto> buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(UsuarioMapper::toResponseDto);
    }

    public List<UsuarioResponseDto> listar() {
        return usuarioRepository.findAll().stream().map(usuario -> {
            UsuarioResponseDto dto = new UsuarioResponseDto();
            dto.setId(usuario.getId());
            dto.setUsername(usuario.getUsername());
            dto.setRoles(usuario.getRoles().stream().map(Role::getDescricao).collect(Collectors.toSet()));
            return dto;
        }).toList();
    }

}
