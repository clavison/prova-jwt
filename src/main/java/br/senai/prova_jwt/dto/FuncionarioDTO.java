package br.senai.prova_jwt.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FuncionarioDTO {
    private Long id;

    @NotEmpty(message = "O nome do funcionário não pode ser vazio.")
    @Size(min = 3, max = 150, message = "O nome do funcionário deve ter entre 3 e 150 caracteres.")
    private String nome;

    @NotNull(message = "O ID do cargo não pode ser nulo.")
    private Long cargoId;

    // Construtores, Getters e Setters
    public FuncionarioDTO() {
    }

    public FuncionarioDTO(Long id, String nome, Long cargoId) {
        this.id = id;
        this.nome = nome;
        this.cargoId = cargoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }
}
