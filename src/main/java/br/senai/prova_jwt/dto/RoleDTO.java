package br.senai.prova_jwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleDTO {
    private Long id;

    @NotBlank(message = "A descrição da role não pode ser vazio.")
    @Size(min = 3, max = 50, message = "O nome da role deve ter entre 3 e 50 caracteres.")
    private String descricao;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

