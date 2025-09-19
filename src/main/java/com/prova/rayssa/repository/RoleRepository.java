package com.prova.rayssa.repository;

import com.prova.rayssa.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByDescricao(String descricao);
}
