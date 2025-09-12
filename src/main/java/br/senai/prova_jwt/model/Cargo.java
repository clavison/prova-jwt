package br.senai.prova_jwt.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal salario;

}
