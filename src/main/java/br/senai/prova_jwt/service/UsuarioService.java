package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.dto.mapper.UsuarioMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.RoleRepository;
import br.senai.prova_jwt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UsuarioDto salvar(UsuarioDto dto) {
        Usuario user = UsuarioMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getRoles() != null) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(nome -> roleRepository.findByNome(nome).orElseThrow())
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
        Usuario salvo = usuarioRepository.save(user);
        return UsuarioMapper.toDto(salvo);
    }

    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDto)
                .toList();
    }

    public Page<Usuario> getUsuariosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usuarioRepository.findAll(pageable);
    }

    public UsuarioDto buscarPorId(Long id) {
        // Validação de segurança: usuário só pode ver seus próprios dados se não for admin
        if (!isAdmin()) {
            Long currentUserId = getCurrentUserId();
            if (!currentUserId.equals(id)) {
                throw new RuntimeException("Você só pode visualizar seus próprios dados");
            }
        }
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return UsuarioMapper.toDto(usuario);
    }

    public UsuarioDto atualizar(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        // Validação de segurança: usuário só pode editar seus próprios dados se não for admin
        if (!isAdmin()) {
            Long currentUserId = getCurrentUserId();
            if (!currentUserId.equals(id)) {
                throw new RuntimeException("Você só pode editar seus próprios dados");
            }
        }
        
        usuario.setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        if (dto.getRoles() != null) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(nome -> roleRepository.findByNome(nome).orElseThrow())
                    .collect(Collectors.toSet());
            usuario.setRoles(roles);
        }
        
        Usuario salvo = usuarioRepository.save(usuario);
        return UsuarioMapper.toDto(salvo);
    }

    public void excluir(Long id) {
        // Validação de segurança: usuário só pode excluir seus próprios dados se não for admin
        if (!isAdmin()) {
            Long currentUserId = getCurrentUserId();
            if (!currentUserId.equals(id)) {
                throw new RuntimeException("Você só pode excluir seus próprios dados");
            }
        }
        usuarioRepository.deleteById(id);
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuario.getId();
    }
}
