package br.senai.prova_jwt.service;

import br.senai.prova_jwt.model.Cargo;
import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.CargoRepository;
import br.senai.prova_jwt.repository.RoleRepository;
import br.senai.prova_jwt.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Inicializar roles
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setDescricao("ROLE_ADMIN");

            Role userRole = new Role();
            userRole.setDescricao("ROLE_USER");

            roleRepository.saveAll(Arrays.asList(adminRole, userRole));
        }

        // Inicializar usuário admin
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setLogin("admin");
            admin.setSenha(passwordEncoder.encode("admin"));

            Role adminRole = roleRepository.findByDescricao("ROLE_ADMIN").orElseThrow();
            admin.getRoles().add(adminRole);

            usuarioRepository.save(admin);
        }

        // Inicializar cargos de exemplo
        if (cargoRepository.count() == 0) {
            Cargo desenvolvedor = new Cargo();
            desenvolvedor.setNome("Desenvolvedor");
            desenvolvedor.setSalario(new BigDecimal("5000.00"));

            Cargo analista = new Cargo();
            analista.setNome("Analista");
            analista.setSalario(new BigDecimal("4000.00"));

            Cargo gerente = new Cargo();
            gerente.setNome("Gerente");
            gerente.setSalario(new BigDecimal("8000.00"));

            cargoRepository.saveAll(Arrays.asList(desenvolvedor, analista, gerente));
        }
    }
}