package br.senai.prova_jwt.domain.usuario;

import br.senai.prova_jwt.helpers.bases.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends BaseService<Usuario, Long, UsuarioRepository> implements UsuarioService {

    public UsuarioServiceImpl(UsuarioRepository repo) {
        super(repo, Usuario.class);
    }

}
