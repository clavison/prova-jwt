package br.senai.prova_jwt.service;

import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.RoleRepository;
import br.senai.prova_jwt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario criar(Usuario usuario, List<String> roleNames) {
        if (usuarioRepository.findByLogin(usuario.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Utilizador com este login já existe.");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        List<Role> roles = roleNames.stream()
                .map(roleName -> roleRepository.findByDescricao(roleName)
                        .orElseThrow(() -> new RuntimeException("Perfil não encontrado: " + roleName)))
                .collect(Collectors.toList());
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado com o id: " + id));
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado, List<String> roleNames) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUsuarioAutenticado = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            Usuario usuarioAutenticado = usuarioRepository.findByLogin(loginUsuarioAutenticado)
                    .orElseThrow(() -> new RuntimeException("Falha ao encontrar dados do utilizador autenticado."));

            if (!usuarioAutenticado.getId().equals(id)) {
                throw new AccessDeniedException("Acesso negado: Utilizadores podem alterar apenas os seus próprios dados.");
            }

            if (roleNames != null && !roleNames.isEmpty()) {
                throw new AccessDeniedException("Acesso negado: Você não tem permissão para alterar os seus perfis de acesso.");
            }
        }

        Usuario usuarioExistente = buscarPorId(id);

        if (usuarioAtualizado.getLogin() != null && !usuarioAtualizado.getLogin().isEmpty()) {
            usuarioExistente.setLogin(usuarioAtualizado.getLogin());
        }

        if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
        }

        if (roleNames != null && !roleNames.isEmpty()) {
            List<Role> roles = roleNames.stream()
                    .map(roleName -> roleRepository.findByDescricao(roleName)
                            .orElseThrow(() -> new RuntimeException("Perfil não encontrado: " + roleName)))
                    .collect(Collectors.toList());
            usuarioExistente.setRoles(roles);
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Utilizador não encontrado com o id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
