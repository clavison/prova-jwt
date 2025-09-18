package br.senai.prova_jwt.repository;

import br.senai.prova_jwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByDescricao(String descricao);
}
