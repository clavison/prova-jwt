package br.senai.prova_jwt.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;

    // Indica que esta entidade possui um relacionamento Many-to-One (muitos para um) com a entidade Cargo
    @ManyToOne
    // Define a coluna que será usada como chave estrangeira na tabela desta entidade
    // "cargo_id" será a coluna que referencia a PK da tabela Cargo
    @JoinColumn(name = "cargo_id")
    private Cargo cargo; // Objeto Cargo associado a esta entidade


}