package br.senai.prova_jwt.controller;


import br.senai.prova_jwt.domain.role.Role;
import br.senai.prova_jwt.domain.role.RoleDto;
import br.senai.prova_jwt.domain.role.RoleMapper;
import br.senai.prova_jwt.domain.role.RoleService;
import br.senai.prova_jwt.helpers.bases.BaseController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("roles")
public class RoleController extends BaseController<Role, RoleDto, Long, RoleMapper> {

    public RoleController(RoleMapper mapper, RoleService roleService) {
        super(mapper, roleService);
    }
}
