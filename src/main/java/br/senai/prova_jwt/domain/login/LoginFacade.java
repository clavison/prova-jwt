package br.senai.prova_jwt.domain.login;

import br.senai.prova_jwt.exceptions.CredenciaisInvalidasException;
import br.senai.prova_jwt.exceptions.SenhaInvalidaException;
import br.senai.prova_jwt.helpers.sec.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginFacade {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginFacade(UserDetailsService userDetailsService,
                       JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        try {
            if (!passwordEncoder.matches(password, username))
                throw new SenhaInvalidaException();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return jwtUtil.generateToken(userDetails);
        } catch (BadCredentialsException e) {
            throw new CredenciaisInvalidasException();
        }
    }

}
