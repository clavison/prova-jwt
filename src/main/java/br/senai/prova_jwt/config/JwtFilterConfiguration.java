package br.senai.prova_jwt.config;

import br.senai.prova_jwt.util.JwtAuthFilter;
import br.senai.prova_jwt.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class JwtFilterConfiguration {

    @Bean
    public JwtAuthFilter provideJwtFilter(UserDetailsService userService, JwtUtil jwtHelper) {
        return new JwtAuthFilter(userService, jwtHelper);
    }
}
