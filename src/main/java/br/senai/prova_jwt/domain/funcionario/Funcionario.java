package br.senai.prova_jwt.domain.funcionario;

import br.senai.prova_jwt.domain.usuario.Usuario;
import br.senai.prova_jwt.domain.cargo.Cargo;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @OneToOne
    private Usuario usuario;

}