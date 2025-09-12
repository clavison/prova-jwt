package br.senai.prova_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDto {

    private Long id;
    private String username;
    private String password;
    private Set<String> roles;
    private CargoDto cargo;
}
