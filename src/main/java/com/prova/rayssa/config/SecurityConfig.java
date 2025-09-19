
package com.prova.rayssa.config;

import com.prova.rayssa.repository.UsuarioRepository;
import com.prova.rayssa.util.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public SecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions().disable()) // Para H2 Console
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (sem autenticação)
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // H2 Console
                        .requestMatchers("/usuarios/cadastrar").permitAll()
                        .requestMatchers("/sec/publico/**").permitAll()
                        
                        // Endpoints protegidos por roles
                        .requestMatchers("/sec/admin/**").hasRole("ADMIN")
                        .requestMatchers("/sec/user/**").hasAnyRole("USER", "ADMIN")
                        
                        // Endpoints de funcionários e cargos (protegidos)
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/funcionarios/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/funcionarios/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/funcionarios/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/funcionarios/**").hasRole("ADMIN")
                        
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/cargos/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/cargos/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/cargos/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/cargos/**").hasRole("ADMIN")
                        
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/usuarios/**").hasAnyRole("ADMIN", "USER")
                        
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return login -> {
            var usuario = usuarioRepository.findByLogin(login);
            if (usuario == null) {
                throw new UsernameNotFoundException("Usuário não encontrado");
            }
            List<GrantedAuthority> authorities = usuario.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getDescricao()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(
                    usuario.getLogin(),
                    usuario.getSenha(),
                    authorities
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
