package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.UsuarioCreateDTO;
import br.senai.prova_jwt.dto.UsuarioDTO;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.RoleRepository;
import br.senai.prova_jwt.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO criarUsuario(UsuarioCreateDTO dto) {
        List<Role> roles = dto.getRoles().stream()
                .map(roleRepository::findByDescricao)
                .collect(Collectors.toList());

        Usuario usuario = Usuario.builder()
                .login(dto.getLogin())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .roles(roles)
                .build();

        Usuario salvo = usuarioRepository.save(usuario);

        return toDTO(salvo);
    }

    public UsuarioDTO buscarPorId(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return toDTO(u);
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setLogin(u.getLogin());
        dto.setRoles(u.getRoles().stream().map(Role::getDescricao).collect(Collectors.toList()));
        return dto;
    }
    public UsuarioDTO buscarPorLogin(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return toDTO(usuario);
    }

    public UsuarioDTO atualizarUsuario(Long id, UsuarioCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setLogin(dto.getLogin());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            List<Role> roles = dto.getRoles().stream()
                    .map(roleRepository::findByDescricao)
                    .collect(Collectors.toList());
            usuario.setRoles(roles);
        }

        Usuario salvo = usuarioRepository.save(usuario);
        return toDTO(salvo);
    }
}

