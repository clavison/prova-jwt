package br.senai.prova_jwt.utils;

import org.springframework.security.core.Authentication;

public class AuthUtil {

    public static boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
