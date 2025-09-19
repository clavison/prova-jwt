package br.senai.prova_jwt.infrastructure.config;

import br.senai.prova_jwt.domain.repository.UsuarioRepository;
import br.senai.prova_jwt.infrastructure.util.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
    public class SecurityConfig {

        private final UsuarioRepository usuarioRepository;

        public SecurityConfig(UsuarioRepository usuarioRepository) {
            this.usuarioRepository = usuarioRepository;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/**").permitAll()
                            .requestMatchers("/cargos/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/funcionarios").hasRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/funcionarios").hasRole("USER")
                            .requestMatchers("/funcionarios/**").hasRole("ADMIN")
                            .requestMatchers("/usuarios").hasRole("ADMIN")
                            .requestMatchers("/roles/**").hasRole("ADMIN")
                            .anyRequest().authenticated()

                    )
                    .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        } // Padrao para todos

        // Registra AuthenticationManager como um bean para ser injetado onde for necessário
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            // Obtém o AuthenticationManager da configuração do Spring Security
            // Ele será usado para processar autenticações (login, validação de credenciais)
            return config.getAuthenticationManager();
        }

        // Registra um UserDetailsService como bean para o Spring Security
        @Bean
        public UserDetailsService userDetailsService() {
            return username ->
                    // Busca o usuário no repositório pelo username
                    usuarioRepository.findByUsername(username)
                            .map(usuario -> {
                                // Converte as roles do usuário para GrantedAuthority (necessário para o Spring Security)
                                List<GrantedAuthority> authorities = usuario.getRoles().stream()
                                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNome()))
                                        .collect(Collectors.toList());

                                // Retorna um objeto User do Spring Security contendo username, senha e permissões
                                return new org.springframework.security.core.userdetails.User(
                                        usuario.getUsername(),
                                        usuario.getPassword(),
                                        authorities
                                );
                            })
                            // Lança exceção se o usuário não for encontrado
                            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        }


        // Registra um PasswordEncoder como bean para o Spring Security
        @Bean
        public PasswordEncoder passwordEncoder() {
            // Usa o algoritmo BCrypt para criptografar e validar senhas de forma segura
            return new BCryptPasswordEncoder();
        }
    }
