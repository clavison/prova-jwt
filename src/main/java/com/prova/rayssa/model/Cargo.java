package com.prova.rayssa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do cargo é obrigatório")
    @Size(min = 2, max = 100, message = "Nome do cargo deve ter entre 2 e 100 caracteres")
    @Column(unique = true)
    private String nome;

    @NotNull(message = "Salário é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salário deve ser maior que zero")
    @Column(precision = 10, scale = 2)
    private BigDecimal salario;
    
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios = new ArrayList<>();
    
    // Métodos utilitários
    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
        funcionario.setCargo(this);
    }
    
    public void removeFuncionario(Funcionario funcionario) {
        funcionarios.remove(funcionario);
        funcionario.setCargo(null);
    }
}
