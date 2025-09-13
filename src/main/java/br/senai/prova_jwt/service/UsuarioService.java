package br.senai.prova_jwt.service;

import br.senai.prova_jwt.dto.UsuarioDto;
import br.senai.prova_jwt.mapper.UsuarioMapper;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.UsuarioRepository;
import br.senai.prova_jwt.utils.AuthUtil;
import br.senai.prova_jwt.utils.RolesUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesUtil rolesUtil;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, RolesUtil rolesUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesUtil = rolesUtil;
    }

    public UsuarioDto salvar(UsuarioDto usuarioDto) {
        usuarioDto.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));

        Set<Role> roles = rolesUtil.validarRoles(usuarioDto.getRoles());

        Usuario usuario = UsuarioMapper.toEntity(usuarioDto);
        usuario.setRoles(roles);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return UsuarioMapper.toDto(usuarioSalvo);
    }

    public UsuarioDto alterar(Long id, UsuarioDto usuarioDto, Authentication authentication) {
        String username = authentication.getName();

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado");
        }

        Usuario usuarioExistente = usuarioOpt.get();

        if (!AuthUtil.isAdmin(authentication) && !usuarioExistente.getLogin().equals(username)) {
            throw new SecurityException("Acesso negado: você só pode visualizar seus próprios dados");
        }

        alterarValoresDo(usuarioDto, usuarioExistente);

        Usuario usuarioSalvo = usuarioRepository.save(usuarioExistente);
        return UsuarioMapper.toDto(usuarioSalvo);
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

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    private void alterarValoresDo(UsuarioDto usuarioDto, Usuario usuario) {
        if (usuarioDto.getLogin() != null) {
            usuario.setLogin(usuarioDto.getLogin());
        }

        if (usuarioDto.getSenha() != null) {
            usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        }
    }
}

