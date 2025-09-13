package com.erp.funcionariocargo.domains.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
}
