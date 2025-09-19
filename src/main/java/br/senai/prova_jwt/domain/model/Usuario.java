package br.senai.prova_jwt.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    // Define um relacionamento Many-to-Many (muitos para muitos) entre Usuario e Role
    @ManyToMany(fetch = FetchType.EAGER)
    // fetch = EAGER -> sempre que o usuário for carregado, suas roles também são carregadas imediatamente
    // Define a tabela de associação entre Usuario e Role
    @JoinTable(
            name = "usuario_roles", // nome da tabela que faz a ligação entre as duas entidades
            joinColumns = @JoinColumn(name = "usuario_id"), // coluna que referencia a chave primária de Usuario
            inverseJoinColumns = @JoinColumn(name = "role_id") // coluna que referencia a chave primária de Role
    )
    private Set<Role> roles = new HashSet<>(); // Conjunto de roles associadas a este usuário
}