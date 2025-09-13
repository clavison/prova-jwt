package br.senai.prova_jwt.domains.auth.usuario;


import br.senai.prova_jwt.domains.auth.role.Role;
import br.senai.prova_jwt.domains.auth.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UsuarioDTO salvar(UsuarioDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(dto.getPassword()));

        // Buscar roles pelo nome se fornecidas
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(nomeRole -> roleRepository.findByDescricao(nomeRole)
                            .orElseThrow(() -> new RuntimeException("Role não encontrada: " + nomeRole)))
                    .collect(Collectors.toSet());
            usuario.setRoles(roles);
        }

        Usuario salvo = usuarioRepository.save(usuario);
        return UsuarioMapper.toDto(salvo);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return UsuarioMapper.toDto(usuario);
    }

    public Page<UsuarioDTO> listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(UsuarioMapper::toDto);
    }

    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setLogin(dto.getUsername());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(nomeRole -> roleRepository.findByDescricao(nomeRole)
                            .orElseThrow(() -> new RuntimeException("Role não encontrada: " + nomeRole)))
                    .collect(Collectors.toSet());
            usuario.setRoles(roles);
        }

        Usuario salvo = usuarioRepository.save(usuario);
        return UsuarioMapper.toDto(salvo);
    }

    public Page<UsuarioDTO> listarComFiltros(UsuarioFiltroDTO filtro, Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(UsuarioSpecification.comFiltros(filtro), pageable);
        return usuarios.map(UsuarioMapper::toDto);
    }

}