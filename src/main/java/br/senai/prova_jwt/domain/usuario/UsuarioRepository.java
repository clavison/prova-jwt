package br.senai.prova_jwt.domain.usuario;

import br.senai.prova_jwt.helpers.bases.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario,Long> {
    Optional<Usuario> findByUsername(String username);
}