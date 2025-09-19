package com.prova.rayssa.config;

import com.prova.rayssa.model.Cargo;
import com.prova.rayssa.model.Role;
import com.prova.rayssa.model.Usuario;
import com.prova.rayssa.repository.CargoRepository;
import com.prova.rayssa.repository.RoleRepository;
import com.prova.rayssa.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        // Verificar se já existem dados para não duplicar
        if (usuarioRepository.count() > 0) {
            return;
        }

        System.out.println("🚀 Iniciando criação de dados iniciais...");

        // 1. Criar Roles
        Role roleAdmin = new Role();
        roleAdmin.setDescricao("ROLE_ADMIN");
        roleAdmin = roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setDescricao("ROLE_USER");
        roleUser = roleRepository.save(roleUser);

        // 2. Criar Cargos
        Cargo cargoJunior = new Cargo();
        cargoJunior.setNome("Desenvolvedor Junior");
        cargoJunior.setSalario(new BigDecimal("5000.00"));
        cargoJunior = cargoRepository.save(cargoJunior);

        Cargo cargoPleno = new Cargo();
        cargoPleno.setNome("Desenvolvedor Pleno");
        cargoPleno.setSalario(new BigDecimal("8000.00"));
        cargoPleno = cargoRepository.save(cargoPleno);

        Cargo cargoSenior = new Cargo();
        cargoSenior.setNome("Desenvolvedor Senior");
        cargoSenior.setSalario(new BigDecimal("12000.00"));
        cargoSenior = cargoRepository.save(cargoSenior);

        // 3. Criar Usuário Admin
        Usuario admin = new Usuario();
        admin.setLogin("admin");
        admin.setSenha(passwordEncoder.encode("123456"));
        admin.setRoles(Set.of(roleAdmin));
        usuarioRepository.save(admin);

        // 4. Criar Usuário comum
        Usuario user = new Usuario();
        user.setLogin("user");
        user.setSenha(passwordEncoder.encode("123456"));
        user.setRoles(Set.of(roleUser));
        usuarioRepository.save(user);

        System.out.println("✅ Dados iniciais criados com sucesso!");
        System.out.println("👤 Login Admin: admin / senha: 123456");
        System.out.println("👤 Login User: user / senha: 123456");
        System.out.println("🏢 Cargos criados: Junior (R$ 5.000), Pleno (R$ 8.000), Senior (R$ 12.000)");
        System.out.println("🔑 Roles criadas: ROLE_ADMIN, ROLE_USER");
        System.out.println("🌐 Aplicação rodando em: http://localhost:8081");
        System.out.println("🗄️ Console H2: http://localhost:8081/h2-console");
    }
}