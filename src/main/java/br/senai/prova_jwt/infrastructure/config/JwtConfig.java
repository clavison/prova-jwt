package br.senai.prova_jwt.infrastructure.config;

import br.senai.prova_jwt.infrastructure.util.JwtAuthFilter;
import br.senai.prova_jwt.infrastructure.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JwtConfig {

    // Classe de configuração do Spring para registrar beans relacionados ao JWT
    @Configuration
    public class JwtConfiguration {

        // Registra JwtAuthFilter como um bean gerenciado pelo Spring
        @Bean
        public JwtAuthFilter jwtAuthFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
            // Cria o filtro que valida o token JWT em cada requisição,
            // usando userDetailsService para buscar o usuário e jwtUtil para validar/extrair dados do token
            return new JwtAuthFilter(userDetailsService, jwtUtil);
        }
    }
}
