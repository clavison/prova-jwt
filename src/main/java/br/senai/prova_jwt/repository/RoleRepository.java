package br.senai.prova_jwt.repository;

import br.senai.prova_jwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNome(String name);

    @Query("SELECT COUNT(r) > 0 FROM Role r WHERE LOWER(r.nome) = LOWER(:nome)")
    boolean existsByNomeIgnoreCase(String nome);

    @Query("SELECT COUNT(r) > 0 FROM Role r WHERE LOWER(r.nome) = LOWER(:nome) AND r.id != :id")
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}
