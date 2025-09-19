package br.senai.prova_jwt.domain.repository;

import br.senai.prova_jwt.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>  {
    Optional<Usuario> findByUsername(String username);
}