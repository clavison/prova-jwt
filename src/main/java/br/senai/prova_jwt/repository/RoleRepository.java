package br.senai.prova_jwt.repository;

import br.senai.prova_jwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByDescricao(String descricao);
    boolean existsByDescricao(String descricao);
}