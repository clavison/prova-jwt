package br.senai.prova_jwt.controller;

import br.senai.prova_jwt.domain.usuario.Usuario;
import br.senai.prova_jwt.domain.usuario.UsuarioDto;
import br.senai.prova_jwt.domain.usuario.UsuarioMapper;
import br.senai.prova_jwt.domain.usuario.UsuarioService;
import br.senai.prova_jwt.helpers.bases.BaseController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
public class UsuarioController extends BaseController<Usuario, UsuarioDto, Long, UsuarioMapper> {

    public UsuarioController(UsuarioMapper mapper, UsuarioService service) {
        super(mapper, service);
    }

}
