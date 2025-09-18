package br.senai.prova_jwt.domain.cargo;

import br.senai.prova_jwt.domain.funcionario.Funcionario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private BigDecimal salario;

    @OneToMany(mappedBy = "cargo",
            cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;

}
