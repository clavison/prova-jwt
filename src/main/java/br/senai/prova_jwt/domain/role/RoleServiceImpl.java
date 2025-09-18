package br.senai.prova_jwt.domain.role;

import br.senai.prova_jwt.helpers.bases.BaseService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseService<Role, Long, RoleRepository> implements RoleService {

    public RoleServiceImpl(RoleRepository repo) {
        super(repo, Role.class);
    }

}
