package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.dto.UsuarioResponseDto;
import br.senai.prova_jwt.dto.mapper.UsuarioMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.RoleRepository;
import br.senai.prova_jwt.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDto salvar(UsuarioDto dto) {
        log.info("Criando usuário: {}", dto.getUsername());
        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Set<Role> roles = dto.getRoles().stream()
                .map(nome -> roleRepository.findByDescricao(nome).orElseThrow(() -> {
                    log.error("Role não encontrada: {}", nome);
                    return new RuntimeException("Role não encontrada: " + nome);
                }))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        Usuario salvo = usuarioRepository.save(user);
        log.info("Usuário criado com sucesso. ID: {}", salvo.getId());
        return UsuarioMapper.toResponseDto(salvo);
    }

    public Optional<UsuarioResponseDto> buscarPorId(Long id) {
        log.info("Buscando usuário pelo ID: {}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            log.warn("Usuário não encontrado. ID: {}", id);
        }
        return usuario.map(UsuarioMapper::toResponseDto);
    }

    public List<UsuarioResponseDto> listar() {
        log.info("Listando todos os usuários");
        return usuarioRepository.findAll().stream().map(usuario -> {
            UsuarioResponseDto dto = new UsuarioResponseDto();
            dto.setId(usuario.getId());
            dto.setUsername(usuario.getUsername());
            dto.setRoles(usuario.getRoles().stream().map(Role::getDescricao).collect(Collectors.toSet()));
            return dto;
        }).toList();
    }

}
