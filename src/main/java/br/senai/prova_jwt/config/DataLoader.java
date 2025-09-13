package br.senai.prova_jwt.config;

import br.senai.prova_jwt.model.Role;
import br.senai.prova_jwt.model.Usuario;
import br.senai.prova_jwt.repository.RoleRepository;
import br.senai.prova_jwt.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByNome("ADMIN").orElseGet(() -> {
                Role newRole = new Role();
                newRole.setNome("ADMIN");
                return roleRepository.save(newRole);
            });

            if (usuarioRepository.findByLogin("admin").isEmpty()) {
                Usuario adminUser = new Usuario();
                adminUser.setLogin("admin");
                adminUser.setSenha(passwordEncoder.encode("admin123"));
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                adminUser.setRoles(adminRoles);
                usuarioRepository.save(adminUser);
                System.out.println("Admin user created.");
            }
        };
    }
}
