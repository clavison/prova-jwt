package br.senai.prova_jwt.config;

import br.senai.prova_jwt.util.JwtAuthFilter;
import br.senai.prova_jwt.util.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class JwtFilterConfig {

    public JwtAuthFilter jwtAuthFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        return new JwtAuthFilter(userDetailsService, jwtUtil);
    }

}
