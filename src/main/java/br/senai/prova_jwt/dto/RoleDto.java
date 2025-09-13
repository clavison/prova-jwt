package br.senai.prova_jwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String nome;
}
