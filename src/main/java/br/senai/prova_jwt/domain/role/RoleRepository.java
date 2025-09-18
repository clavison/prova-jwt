package br.senai.prova_jwt.domain.role;

import br.senai.prova_jwt.helpers.bases.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
}
