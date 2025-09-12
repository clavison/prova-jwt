package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.mapper.UsuarioMapper;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Page<UsuarioDto> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(UsuarioMapper::toDto);
    }

    public UsuarioDto buscarPorId(Long id, Authentication authentication) {
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (!isAdmin && !usuario.getLogin().equals(username)) {
            throw new SecurityException("Acesso negado: você só pode visualizar seus próprios dados");
        }

        return UsuarioMapper.toDto(usuario);
    }
}

