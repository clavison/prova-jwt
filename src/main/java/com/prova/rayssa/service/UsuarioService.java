package com.prova.rayssa.service;

import com.prova.rayssa.model.Usuario;
import com.prova.rayssa.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
