package br.senai.prova_jwt.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CargoDTO {
    private Long id;

    @NotEmpty(message = "O nome do cargo não pode ser vazio.")
    @Size(min = 3, max = 100, message = "O nome do cargo deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotNull(message = "O salário não pode ser nulo.")
    @Positive(message = "O salário deve ser um valor positivo.")
    private double salario;

    private List<FuncionarioDTO> funcionarios;

    public CargoDTO() {
    }

    public CargoDTO(Long id, String nome, double salario, List<FuncionarioDTO> funcionarios) {
        this.id = id;
        this.nome = nome;
        this.salario = salario;
        this.funcionarios = funcionarios;
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

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<FuncionarioDTO> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<FuncionarioDTO> funcionarios) {
        this.funcionarios = funcionarios;
    }
}
