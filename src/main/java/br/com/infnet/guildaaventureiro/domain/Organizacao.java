package br.com.infnet.guildaaventureiro.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "organizacoes",
        schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(name = "organizacoes_nome_key", columnNames = {"nome"})
        }
)
@Getter
@Setter
public class Organizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizacoes_id")
    @SequenceGenerator(
            name = "organizacoes_id",
            sequenceName = "organizacoes_id_seq",
            schema = "audit",
            allocationSize = 1
    )
    private Long id;

    @Column(
            length = 120,
            nullable = false,
            unique = true
    )
    private String nome;

    @Column(nullable = false)
    private boolean ativo = true;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "organizacao", fetch = FetchType.LAZY)
    private Set<Usuario> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "organizacao", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "organizacao", fetch = FetchType.LAZY)
    private Set<ApiKey> apiKeys = new HashSet<>();

    public void adicionarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.definirOrganizacao(this);
    }
}
